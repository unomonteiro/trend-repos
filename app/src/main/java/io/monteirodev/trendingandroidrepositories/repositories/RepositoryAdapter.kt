package io.monteirodev.trendingandroidrepositories.repositories

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.monteirodev.trendingandroidrepositories.R
import io.monteirodev.trendingandroidrepositories.commons.inflate
import io.monteirodev.trendingandroidrepositories.models.Repository
import kotlinx.android.synthetic.main.repository_item.view.*

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    private var repoList = arrayListOf<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
            RepositoryViewHolder(parent.inflate(R.layout.repository_item))

    override fun getItemCount(): Int = repoList.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) =
            holder.bind(repoList[position])

    fun addRepos(newRepos: ArrayList<Repository>) {
        repoList.addAll(newRepos)
        notifyDataSetChanged()
    }

    fun getRepos() = repoList

    class RepositoryViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(repository: Repository) = with(itemView) {
            name_text_view.text = repository.name
            description_text_view.text = repository.description
            stargazers_text_view.text = repository.stargazersCount
        }
    }
}