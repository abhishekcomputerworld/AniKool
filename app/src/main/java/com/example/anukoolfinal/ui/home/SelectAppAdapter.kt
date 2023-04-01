package com.example.anukoolfinal.ui.home

import android.content.Context
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.anukoolfinal.R
import com.example.anukoolfinal.databinding.AppSelectItemBinding
import com.example.anukoolfinal.model.AppData
import com.example.anukoolfinal.utility.ImageUtil
import com.example.anukoolfinal.utility.Preference


class SelectAppAdapter(
    private val context: Context,
    private var packageList: ArrayList<AppData>,
    private var sharedPreference: Preference,
    var appSelectedListener: AppSelectedListener,
    var switchClicked: Boolean
) : RecyclerView.Adapter<SelectAppAdapter.AppSelectItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppSelectItemViewHolder {
        val binding =
            AppSelectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppSelectItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppSelectItemViewHolder, position: Int) {
        with(holder) {
            binding.appNameText.text = packageList[position].appName
            binding.appDescText.text = packageList[position].appPackageName
            Glide.with(context).load(ImageUtil.convert(packageList[position]?.appIconName)).apply(
                RequestOptions().placeholder(R.mipmap.ic_launcher)
            ).into(binding!!.appIconImageview)

            binding.swithBar.switchInclude.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
              /*  if (isChecked) {
                    appSelectedListener.onAppClicked(packageList[position], isChecked)
                    Toast.makeText(context, "isChecked", 1)
                } else {
                    appSelectedListener.onAppClicked(packageList[position], isChecked)
                    Toast.makeText(context, "unChecked", 1)

                }*/
            })
            binding.swithBar.switchInclude.isClickable=false
            if (switchClicked || binding.swithBar.switchInclude.getTag()==true) {
                binding.swithBar.switchInclude.setChecked(true)
            } else {
                binding.swithBar.switchInclude.setChecked(false)
            }
            binding.llApp.setOnClickListener {
                if (binding.swithBar.switchInclude.isChecked) {
                    binding.swithBar.switchInclude.setTag(false)
                    binding.swithBar.switchInclude.setChecked(false)

                } else {
                    binding.swithBar.switchInclude.setTag(true)
                    binding.swithBar.switchInclude.setChecked(true)

                }
                var isChecked= binding.swithBar.switchInclude.isChecked
                if (isChecked) {
                    appSelectedListener.onAppClicked(packageList[position], isChecked)
                    Toast.makeText(context, "isChecked", 1)
                } else {
                    appSelectedListener.onAppClicked(packageList[position], isChecked)
                    Toast.makeText(context, "unChecked", 1)

                }
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

    fun setSwitchChange(changed: Boolean) {
        switchClicked = changed
     //   var a :ArrayList<ResolveInfo> = arrayListOf()
     ////    a.addAll(packageList)
        packageList.removeAt(0);
     //   packageList.addAll(a);
        notifyDataSetChanged()

    }

    inner class AppSelectItemViewHolder(val binding: AppSelectItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}