package io.monteirodev.trendingandroidrepositories.repositories

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.NO_POSITION
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import io.monteirodev.trendingandroidrepositories.R
import io.monteirodev.trendingandroidrepositories.commons.InfiniteScrollListener
import io.monteirodev.trendingandroidrepositories.details.DetailsActivity
import io.monteirodev.trendingandroidrepositories.details.DetailsFragment
import io.monteirodev.trendingandroidrepositories.models.Repository
import io.monteirodev.trendingandroidrepositories.network.ReposManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    protected var subscriptions = CompositeDisposable()

    private val REPO_LIST_KEY = "repo_list_key"
    private val repoAdapter by lazy { RepositoryAdapter(this::repoClicked) }
    private val reposManager by lazy { ReposManager() }

    private val isTablet by lazy { resources.getBoolean(R.bool.is_tablet) }
    private val REPO_INDEX_KEY = "repo_index"
    private var repoIndex = NO_POSITION
    private var detailsFragment = DetailsFragment()
    private lateinit var repository: Repository

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
        if (isTablet) {
            outState.putInt(REPO_INDEX_KEY, repoIndex)
        }
    }

    private fun initAdapter() {
        repository_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(this@MainActivity)
            layoutManager = linearLayout
            addItemDecoration(DividerItemDecoration(this@MainActivity,VERTICAL))
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener(
                    {requestRepositories()}, linearLayout))
            adapter = repoAdapter
        }
    }

    private fun requestRepositories() {
        val subscription = reposManager.getRepos(repoAdapter.itemCount / 30)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { retrievedRepos ->
                            repoAdapter.addRepos(retrievedRepos)
                            if (isTablet) {
                                if (repoIndex == NO_POSITION) {
                                    repoIndex = 0
                                }
                                replaceRepoDetailsFragment(retrievedRepos[repoIndex])
                            }
                        },
                        { e ->
                            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                        }
                )
        subscriptions.add(subscription)
    }

    fun repoClicked(repository: Repository) {
        if (isTablet) {
            repoIndex = repoAdapter.getRepos().indexOf(repository)
            setRepoDetailsFragment(repository)
        } else {
            val intent = DetailsActivity.newIntent(this, repository)
            startActivity(intent)
        }
    }


    private fun setRepoDetailsFragment(repository: Repository) {
        detailsFragment = DetailsFragment.newInstance(repository)
        replaceRepoDetailsFragment(repository)
    }


    private fun replaceRepoDetailsFragment(newRepository: Repository) {
        repository = newRepository
        detailsFragment = DetailsFragment.newInstance(repository)
        supportFragmentManager.beginTransaction()
                .replace(R.id.details_container, detailsFragment)
                .commit()
    }
}
