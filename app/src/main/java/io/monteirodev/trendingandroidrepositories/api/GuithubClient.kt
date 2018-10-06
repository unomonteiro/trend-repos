package io.monteirodev.trendingandroidrepositories.api

import io.monteirodev.trendingandroidrepositories.models.GitHubResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GithubClient {

    private val githubApi: GithubApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        githubApi = retrofit.create(GithubApi::class.java)
    }

    fun getRepositories(page: Int): Call<GitHubResponse> {
        return githubApi.getRepositories(
                page,
                "android",
                "java,kotlin",
                "stars",
                "desc")
    }

    fun getReadmeHtml(owner: String, repo: String): String? {
        return githubApi.getReadmeHtml(owner, repo).execute().body()?.string()
    }
}