package com.dguitarclassic.githubuserapps2.activities.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dguitarclassic.githubuserapps2.R
import com.dguitarclassic.githubuserapps2.activities.UserDetailActivity
import com.dguitarclassic.githubuserapps2.adapter.UserAdapter
import com.dguitarclassic.githubuserapps2.databinding.FragmentPagerBinding
import com.dguitarclassic.githubuserapps2.view_model.PagerModel

class FollowingFragment : Fragment(R.layout.fragment_pager) {

    private var _binding : FragmentPagerBinding?=null
    private val binding get() = _binding
    private lateinit var viewModel : PagerModel
    private lateinit var pagerAdapter: UserAdapter
    private lateinit var username : String

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPagerBinding.bind(view)

        username = arguments?.getString(UserDetailActivity.EXTRA_USERNAME).toString()

        pagerAdapter = UserAdapter()
        binding?.apply {
            rvFollow.layoutManager = LinearLayoutManager(requireContext())
            rvFollow.setHasFixedSize(true)
            rvFollow.adapter = pagerAdapter

        }
        loading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).
        get(PagerModel::class.java)

        viewModel.setFollowing(username)
        viewModel.getFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                pagerAdapter.setList(it)
                loading(false)
            }
        }
    }
    private fun loading(statement : Boolean){
        if(statement){
            binding?.progress?.visibility = View.VISIBLE
        }else{

            binding?.progress?.visibility = View.GONE
        }
    }
}