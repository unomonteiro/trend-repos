package io.monteirodev.trendingandroidrepositories.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.monteirodev.trendingandroidrepositories.R
import io.monteirodev.trendingandroidrepositories.commons.loadImg
import io.monteirodev.trendingandroidrepositories.models.Repository
import kotlinx.android.synthetic.main.activity_details_.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val repository = intent.getParcelableExtra<Repository>(INTENT_REPOSITORY)
        title = repository.name

        avatar_image_view.loadImg(repository.owner.avatarUrl)
        name_text_view.text = repository.name
        description_text_view.text = repository.description
        watchers_text_view.text = repository.watchersCount
        stars_text_view.text = repository.stargazersCount
        fork_text_view.text = repository.forksCount
        language_value_text_view.text = repository.language
        license_value.text = repository.license?.name


    }

    companion object {
        private val INTENT_REPOSITORY = "repository"

        fun newIntent(context: Context, repo: Repository): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(INTENT_REPOSITORY, repo)
            return intent
        }
    }
}
