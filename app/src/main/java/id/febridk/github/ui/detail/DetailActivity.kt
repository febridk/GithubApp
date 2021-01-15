package id.febridk.github.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.febridk.github.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object{
        const val USERNAME = "username"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0f
            title = "Github User"
            setDisplayHomeAsUpEnabled(true)
        }

        val username = intent.getStringExtra(USERNAME)
        val bundle = Bundle()
        bundle.putString(USERNAME, username)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        username?.let { viewModel.setUserDetail(it) }
        viewModel.getUserDetail().observe(this,{
            if (it != null){
                binding.apply {
                    detailLogin.text = it.name
                    detailUsername.text = it.login
                    userFollowers.text = "${it.followers} Followers"
                    userFollowing.text = "${it.following} Following"

                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
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
}