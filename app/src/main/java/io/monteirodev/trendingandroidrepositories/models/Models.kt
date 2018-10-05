package io.monteirodev.trendingandroidrepositories.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

class GitHubResponse(val items: List<Repository>)

@Parcelize
class Repository(val id: Int,
                      val name: String,
                      val description: String,
                      val owner: Owner,
                      val license: License?,
                      @Json(name = "stargazers_count") val stargazersCount: String,
                      @Json(name = "watchers_count") val watchersCount: String,
                      @Json(name = "forks_count") val forksCount: String,
                      val language: String?) : Parcelable

@Parcelize
class Owner(@Json(name = "avatar_url") val avatarUrl: String) : Parcelable

@Parcelize
class License(val name: String) : Parcelable