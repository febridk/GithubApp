package id.febridk.github.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.febridk.github.data.model.User
import id.febridk.github.databinding.ActivityMainBinding
import id.febridk.github.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.USERNAME, data.login)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)

        binding.apply {
            recyclerUser.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayout.VERTICAL
                )
            )
            recyclerUser.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerUser.setHasFixedSize(true)
            recyclerUser.adapter = adapter

            btnSearch.setOnClickListener {
                searchUser()
            }

            searchQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })

    }

    private fun searchUser() {
        binding.apply {
            val query = searchQuery.text.toString()
            if (query.isEmpty()) {
                Toast.makeText(this@MainActivity, "Field cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                showLoading(true)
                viewModel.setSearchUsers(query)
            }

        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}