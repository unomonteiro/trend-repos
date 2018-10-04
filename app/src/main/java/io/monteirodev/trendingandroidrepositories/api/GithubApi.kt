package io.monteirodev.trendingandroidrepositories.api

import io.monteirodev.trendingandroidrepositories.models.GitHubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    // https://api.github.com/search/repositories?q=android&l=java,kotlin&sort=stars&order=desc
    @GET("search/repositories")
    fun getRepositories(
            @Query("q") query: String,
            @Query("l") language: String,
            @Query("sort") sort: String,
            @Query("order") order: String) : Call<GitHubResponse>
}