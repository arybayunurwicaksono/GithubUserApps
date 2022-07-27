package com.dguitarclassic.githubuserapps2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dguitarclassic.githubuserapps2.adapter.PagerAdapter
import com.dguitarclassic.githubuserapps2.databinding.ActivityDetailUserBinding
import com.dguitarclassic.githubuserapps2.view_model.UserDetailModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel : UserDetailModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = intent.getStringExtra(EXTRA_USERNAME)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        loading(true)
        viewModel= ViewModelProvider(this).get(UserDetailModel::class.java)

        if (username != null){
            viewModel.setDetail(username)
        }
        viewModel.getDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvItemUsername.text = it.login
                    tvItemName.text = it.name
                    tvItemCompany.text = it.company
                    tvItemLocation.text = it.location
                    repository.text = "${it.public_repos}"
                    followers.text = "${it.followers}"
                    following.text = "${it.following}"
                    Glide.with(this@UserDetailActivity)
                        .load(it.avatar_url)
                        .into(imgAvatar)
                    loading(false)
                }
            }
        }

        val pager = PagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = pager
            tabs.setupWithViewPager(viewPager)
        }
        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null) {
                    if (count > 0 ){
                        binding.markToggle.isChecked = true
                        _isChecked = true
                    } else {
                        binding.markToggle.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.markToggle.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked){
                if (username != null) {
                    if (avatar != null) {
                        viewModel.addtoFavorite(username, id, avatar)
                    }
                }
            }else {
                viewModel.removeFromFavorite(id)
            }
            binding.markToggle.isChecked = _isChecked
        }


    }

    private fun loading(statement : Boolean){
        if(statement){
            binding.progressBar.visibility = View.VISIBLE
        }else{

            binding.progressBar.visibility = View.GONE
        }
    }
    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_ID"
        const val EXTRA_AVATAR = "extra_avatar"
    }
}