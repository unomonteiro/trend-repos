package io.monteirodev.trendingandroidrepositories.repositories

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import io.monteirodev.trendingandroidrepositories.R
import io.monteirodev.trendingandroidrepositories.details.DetailsActivity
import io.monteirodev.trendingandroidrepositories.models.Repository
import io.monteirodev.trendingandroidrepositories.network.ReposManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    protected var subscriptions = CompositeDisposable()

    private val REPO_LIST_KEY = "REPO_LIST_KEY"
    private val repoAdapter by lazy { RepositoryAdapter(this::repoClicked) }
    private val reposManager by lazy { ReposManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapter()
        if (savedInstanceState != null && savedInstanceState.containsKey(REPO_LIST_KEY)) {
            repoAdapter.addRepos(savedInstanceState.getParcelableArrayList(REPO_LIST_KEY))
        } else {
            requestRepositories()
        }
    }

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        if (!subscriptions.isDisposed) {
            subscriptions.dispose()
        }
        subscriptions.clear()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(REPO_LIST_KEY, repoAdapter.getRepos())
    }

    private fun initAdapter() {
        repository_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity,VERTICAL))
            adapter = repoAdapter
        }
    }

    private fun requestRepositories() {
        val subscription = reposManager.getRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { retrievedRepos ->
                            repoAdapter.addRepos(retrievedRepos)
                        },
                        { e ->
                            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                        }
                )
        subscriptions.add(subscription)
    }

    fun repoClicked(repository: Repository) {
        val intent = DetailsActivity.newIntent(this, repository)
        startActivity(intent)
    }
}
