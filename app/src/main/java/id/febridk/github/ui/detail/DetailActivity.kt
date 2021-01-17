package id.febridk.github.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import id.febridk.github.R
import id.febridk.github.databinding.ActivityDetailBinding
import java.text.NumberFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    companion object {
        const val USERNAME = "username"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val app_detail_name: String = getString(R.string.app_detail_name)
        val text_followers: String = getString(R.string.followers)
        val text_following: String = getString(R.string.following)

        supportActionBar?.apply {
            elevation = 0f
            title = app_detail_name
            setDisplayHomeAsUpEnabled(true)
        }

        val username = intent.getStringExtra(USERNAME)
        val bundle = Bundle()
        bundle.putString(USERNAME, username)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        username?.let { viewModel.setUserDetail(it) }
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    detailLogin.text = it.name
                    detailUsername.text = it.login
                    userFollowers.text = "${NumberFormat.getInstance(Locale.GERMAN)
                        .format(it.followers)} ${text_followers}"
                    userFollowing.text = "${NumberFormat.getInstance(Locale.GERMAN)
                        .format(it.following)} ${text_following}"

                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .into(detailAvatar)
                }
            }
        })

        val sectionPagerAdapter = SectionPagerAdaptor(this, supportFragmentManager, bundle)
        binding.apply {
            detailPager.adapter = sectionPagerAdapter
            detailTab.setupWithViewPager(detailPager)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

}