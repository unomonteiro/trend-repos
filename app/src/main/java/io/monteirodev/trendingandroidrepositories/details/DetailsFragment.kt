package io.monteirodev.trendingandroidrepositories.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.monteirodev.trendingandroidrepositories.R
import io.monteirodev.trendingandroidrepositories.commons.loadImg
import io.monteirodev.trendingandroidrepositories.models.Repository
import io.monteirodev.trendingandroidrepositories.network.ReposManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

private const val ARG_REPOSITORY = "repository_arg"

class DetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    private val reposManager by lazy { ReposManager() }

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

            val subscription = reposManager.getReadmeHtml(
                    repository.owner.login, repository.name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { readmeHtml ->
                                view.readme_webview.loadDataWithBaseURL(
                                        "http://www.github.com",
                                        readmeHtml, "text/html",
                                        "UTF-8",
                                        null)
                            },
                            { e ->
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            }
                    )
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
