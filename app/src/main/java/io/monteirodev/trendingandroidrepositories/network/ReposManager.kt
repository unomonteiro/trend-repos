package io.monteirodev.trendingandroidrepositories.network

import io.monteirodev.trendingandroidrepositories.api.GithubClient
import io.monteirodev.trendingandroidrepositories.models.Repository
import io.reactivex.Observable

class ReposManager(private val api: GithubClient = GithubClient()) {
    fun getRepos(): Observable<ArrayList<Repository>> {
        return Observable.create {
            subscriber ->

            val callResponse = api.getRepositories()
            val response = callResponse.execute()

            if (response.isSuccessful && response.body()?.items != null) {
                val repositoryList = response.body()?.items
                subscriber.onNext(repositoryList as ArrayList<Repository>)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun getReadmeHtml(owner: String, repo: String): Observable<String> {
        return Observable.create {
            subscriber ->

            val call = api.getReadmeHtml(owner, repo)
            val response = call.execute()

            if (response.isSuccessful) {
                val string = response.body()?.string()
                string?.let { subscriber.onNext(it) }
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}