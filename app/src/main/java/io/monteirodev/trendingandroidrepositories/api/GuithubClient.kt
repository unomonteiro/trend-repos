package io.monteirodev.trendingandroidrepositories.api

import io.monteirodev.trendingandroidrepositories.models.GitHubResponse
import okhttp3.ResponseBody
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

    fun getRepositories(): Call<GitHubResponse> {
        return githubApi.getRepositories(
                "android",
                "java,kotlin",
                "stars",
                "desc")
    }

    fun getReadmeHtml(owner: String, repo: String): Call<ResponseBody> {
        return githubApi.getReadmeHtml(owner, repo)
    }
}