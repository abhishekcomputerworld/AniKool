package com.example.anukoolfinal.ui.home

import android.R.attr.bitmap
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.anukoolfinal.R
import com.example.anukoolfinal.databinding.AppListItemBinding
import com.example.anukoolfinal.model.AppData
import com.example.anukoolfinal.utility.ImageUtil
import com.example.anukoolfinal.utility.Preference


class AppListAdapter(
    private val context: Context,
    private var packageList: ArrayList<AppData>,
    private var sharedPreference: Preference,
    var appSelectedListener: AppSelectedListener,
    var switchClicked: Boolean
) : RecyclerView.Adapter<AppListAdapter.AppSelectItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppSelectItemViewHolder {
        val binding =
            AppListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppSelectItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppSelectItemViewHolder, position: Int) {
        with(holder) {
            binding.appNameText.text = packageList[position].appName
            Glide.with(context).load( ImageUtil.convert(packageList[position]?.appIconName)).apply(
                RequestOptions().placeholder(R.mipmap.ic_launcher)
            ).into(binding!!.appImageview)
            binding.deleteBtn.setOnClickListener {
                sharedPreference.removeSelectedApp(packageList[position].appPackageName)
                packageList.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return packageList.size
    }


    fun filterList(filterlist: ArrayList<AppData>) {
        // below line is to add our filtered
        // list in our course array list.
        packageList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }


    inner class AppSelectItemViewHolder(val binding: AppListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}