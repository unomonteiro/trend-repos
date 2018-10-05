package io.monteirodev.trendingandroidrepositories.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

class GitHubResponse(val items: List<Repository>)

@Parcelize
class Repository(val id: Int,
                 val name: String,
                 val description: String,
                 @Json(name = "stargazers_count")
                 val stargazersCount: String) : Parcelable


