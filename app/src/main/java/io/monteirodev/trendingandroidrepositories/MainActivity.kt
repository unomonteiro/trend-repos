package io.monteirodev.trendingandroidrepositories

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import io.monteirodev.trendingandroidrepositories.network.ReposManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val reposManager by lazy { ReposManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestRepositories()

    }

    private fun requestRepositories() {
        val subscription = reposManager.getRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { retrievedRepos ->
                            val names = arrayListOf<String>()
                            for (repo in retrievedRepos) {
                                names.add(repo.name)
                            }
                            results.text = TextUtils.join("\n",names)
                        },
                        { e ->
                            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                        }
                )
    }
}
