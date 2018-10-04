package io.monteirodev.trendingandroidrepositories.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class GitHubResponse(val items: List<Repository>)

@Parcelize
class Repository(val id: Int,
                 val name: String,
                 val description: String) : Parcelable


