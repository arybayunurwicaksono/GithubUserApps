package com.dguitarclassic.githubuserapps2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dguitarclassic.githubuserapps2.db.Favorite
import com.dguitarclassic.githubuserapps2.db.FavoriteModel
import com.dguitarclassic.githubuserapps2.adapter.UserAdapter
import com.dguitarclassic.githubuserapps2.databinding.ActivityFavoriteBinding
import com.dguitarclassic.githubuserapps2.view_model.UserModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel : FavoriteModel
    private lateinit var adapter : UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(FavoriteModel::class.java)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserModel) {
                Intent(this@FavoriteActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUsers.adapter = adapter
        }

        viewModel.getFavorite()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }

    private fun mapList(users: List<Favorite>): ArrayList<UserModel> {
        val listUsers = ArrayList<UserModel>()
        for (user in users) {
            val userMapped = UserModel (
                user.id,
                user.login,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}