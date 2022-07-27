package com.dguitarclassic.githubuserapps2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dguitarclassic.githubuserapps2.theme.PrefHelper
import com.dguitarclassic.githubuserapps2.R
import com.dguitarclassic.githubuserapps2.adapter.UserAdapter
import com.dguitarclassic.githubuserapps2.databinding.ActivityMainBinding
import com.dguitarclassic.githubuserapps2.view_model.MainModel
import com.dguitarclassic.githubuserapps2.view_model.UserModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var Model : MainModel
    private lateinit var adapterUser : UserAdapter

    private val theme by lazy { PrefHelper(this) }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.language -> {
                startActivity(Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.theme -> {
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mark: View = findViewById(R.id.mark)
        mark.setOnClickListener { view ->
            val favorite = Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(favorite)

        }

        adapterUser = UserAdapter()
        adapterUser.notifyDataSetChanged()
        adapterUser.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(userModel: UserModel) {
                Intent(this@MainActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USERNAME, userModel.login)
                    it.putExtra(UserDetailActivity.EXTRA_ID, userModel.id)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR, userModel.avatar_url)
                    startActivity(it)
                }
            }
        })

        Model = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get((MainModel::class.java))

        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapterUser

            svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(newText: String?): Boolean = false

                override fun onQueryTextChange(query: String?): Boolean {
                    if (query != null && query.isNotEmpty()) {
                        search()
                    }
                    return true
                }
            })
        }

        when(theme.getBoolean("pref_is_dark_mode")) {
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        Model.getSearch().observe(this) {
            if (it != null) {
                adapterUser.setList(it)
                Loading(false)
                binding.rvUsers.visibility = View.VISIBLE
            }
        }
    }

    private fun search(){
        binding.apply {
            val query = svUsers.query.toString()
            if (query.isEmpty()) return
            binding.rvUsers.visibility = View.GONE
            Loading(true)
            Model.setSearch(query)
        }
    }

    private fun Loading(load: Boolean) {
        if (load) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}