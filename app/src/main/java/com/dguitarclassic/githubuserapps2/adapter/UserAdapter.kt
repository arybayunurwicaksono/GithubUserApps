package com.dguitarclassic.githubuserapps2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dguitarclassic.githubuserapps2.databinding.ItemRowUserBinding
import com.dguitarclassic.githubuserapps2.view_model.UserModel

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val listUser = ArrayList<UserModel>()
    private var onItemClickCallback : OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(userModel: ArrayList<UserModel>){
        listUser.clear()
        listUser.addAll(userModel)
        notifyDataSetChanged()
    }

    inner class UserViewHolder( val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(userModel: UserModel) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(userModel)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(userModel.avatar_url)
                    .circleCrop()
                    .into(imgItemPhoto)
                tvItemScore.text = userModel.id.toString()
                tvItemUsername.text = userModel.login
            }
        }
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): UserViewHolder {
        val viewHolder = ItemRowUserBinding.inflate(LayoutInflater.from(view.context), view, false)
        return UserViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(userModel: UserModel)
    }
}