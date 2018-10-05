package io.monteirodev.trendingandroidrepositories.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.monteirodev.trendingandroidrepositories.R
import io.monteirodev.trendingandroidrepositories.commons.loadImg
import io.monteirodev.trendingandroidrepositories.models.Repository
import kotlinx.android.synthetic.main.fragment_details.*

private const val ARG_REPOSITORY = "repository_arg"

class DetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository = arguments?.get(ARG_REPOSITORY) as Repository?
        if (repository != null) {
            avatar_image_view.loadImg(repository.owner.avatarUrl)
            name_text_view.text = repository.name
            description_text_view.text = repository.description
            watchers_text_view.text = repository.watchersCount
            stars_text_view.text = repository.stargazersCount
            fork_text_view.text = repository.forksCount
            language_value_text_view.text = repository.language
            license_value.text = repository.license?.name
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(repository: Repository) =
                DetailsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_REPOSITORY, repository)
                    }
                }
    }
}
