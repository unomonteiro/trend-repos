package io.monteirodev.trendingandroidrepositories.api

import io.monteirodev.trendingandroidrepositories.models.GitHubResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    // https://api.github.com/search/repositories?q=android&l=java,kotlin&sort=stars&order=desc
    @GET("search/repositories")
    fun getRepositories(
            @Query("page") int: Int = 0,
            @Query("q") query: String,
            @Query("l") language: String,
            @Query("sort") sort: String,
            @Query("order") order: String) : Call<GitHubResponse>

    // https://developer.github.com/v3/repos/contents/#get-the-readme
    @GET("/repos/{owner}/{repo}/readme")
    @Headers("Accept: application/vnd.github.v3.html")
    fun getReadmeHtml(@Path("owner") owner: String,
                      @Path("repo") repo: String): Call<ResponseBody>

}