package com.example.anukoolfinal.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.anukoolfinal.R
import com.example.anukoolfinal.databinding.FragmentSelectAppBinding
import com.example.anukoolfinal.model.AppData
import com.example.anukoolfinal.utility.AppUtility
import com.example.anukoolfinal.utility.ImageUtil
import com.example.anukoolfinal.utility.Preference
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectAppFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectAppFragment : Fragment(), AppSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSelectAppBinding? = null
    private val binding get() = _binding!!
    private var navController: NavController? = null
    private lateinit var sharedPreference: Preference
    private lateinit var selectAppAdapter: SelectAppAdapter
    private var packList: ArrayList<AppData> = arrayListOf()
    private var addedApp: ArrayList<AppData> = arrayListOf()

    private var f11937b: Animation? = null

    /* renamed from: c */
    private var f11938c: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = Preference(requireContext())
        getAllApps()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSelectAppBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setUpAdapter()
        binding.appSelectSearchCancelBtn.setTag(Integer.valueOf(R.mipmap.ic_search))
        setUpListener()
        m15462e(0)
    }

    private fun setUpAdapter() {
        selectAppAdapter = SelectAppAdapter(
            requireContext(),
            packList,
            sharedPreference,
            this,
            false
        )
        binding!!.appSelectListview.adapter = selectAppAdapter    }

    private  fun getAllApps() {
        synchronized(SelectAppFragment::class.java) {
            var data = //(packageManager.queryIntentActivities(intent, 0) as ArrayList<ResolveInfo>)
                AppUtility.getApps(requireActivity())
            /* .sortedBy {
                it.loadLabel(requireActivity().packageManager).toString()
            }*/
            for (item in data) {
                if (!sharedPreference.appAdded(item.activityInfo.packageName)) {
                    packList.add(
                        AppData(
                            item.loadLabel(context?.packageManager).toString(),
                            item.activityInfo.packageName,
                            ImageUtil.convert(item.loadIcon(context?.packageManager).toBitmap())
                        )
                    )
                }
            }
        }

    }



    private fun setUpListener() {
        binding.appSelectSearchCancelBtn.setOnClickListener {
            if ((binding.appSelectSearchCancelBtn.getTag() as Int).toInt() == R.mipmap.ic_search) {
                binding.appSelectSearchCancelBtn.setImageResource(R.mipmap.ic_close)
                binding.appSelectSearchCancelBtn.setTag(Integer.valueOf(R.mipmap.ic_close))
                binding.appSelectTitleText.visibility = View.GONE
                binding.appSelectSearchEdit.visibility = View.VISIBLE
                binding.appSelectSearchEdit.requestFocus()
                (requireContext().getSystemService("input_method") as InputMethodManager).showSoftInput(
                    binding.appSelectSearchEdit,
                    1
                )

            } else {
                binding.appSelectSearchCancelBtn.setImageResource(R.mipmap.ic_search)
                binding.appSelectSearchCancelBtn.setTag(Integer.valueOf(R.mipmap.ic_search))
                binding.appSelectSearchEdit.visibility = View.GONE
                this.binding.appSelectTitleText.visibility = View.VISIBLE
                (requireContext().getSystemService("input_method") as InputMethodManager).hideSoftInputFromWindow(
                    binding.appSelectSearchEdit.getWindowToken(),
                    0
                )
                binding.appSelectSearchEdit.setText("")
            }
        }
        binding.appSelectSearchEdit.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Log.e("afterTextChanged_", "beforeTextChanged")

                }

                override fun onTextChanged(
                    s: CharSequence, start: Int, before: Int,
                    count: Int
                ) {
                    Log.e("afterTextChanged_", "onTextChanged")
                    // filter(s.toString());

                }

                override fun afterTextChanged(s: Editable) {
                    Log.e("afterTextChanged_", "afterTextChanged")
                    filter(s.toString());
                    //return false;


                }
            }
        )
        binding.appSelectBackBtn.setOnClickListener {
            navController?.popBackStack()
        }
        binding.switchView.switchInclude.setOnClickListener {
            if (binding.switchView.switchInclude.isChecked) {
                addedApp.addAll(packList)
                m15462e(1)
            } else {
                addedApp.clear()
                m15462e(0)
            }
            selectAppAdapter = SelectAppAdapter(
                requireContext(),
                packList,
                sharedPreference,
                this,
                binding.switchView.switchInclude.isChecked
            )

            binding!!.appSelectListview.adapter = selectAppAdapter

        }
        binding.switchView.switchInclude.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            /* if (isChecked) {
                 addedApp.addAll(packList)
                 m15462e(1)
             } else {
                 addedApp.clear()
                 m15462e(0)
             }

             selectAppAdapter = SelectAppAdapter(
                 requireContext(),
                 packList,
                 sharedPreference,
                 this,
                 isChecked
             )
             binding!!.appSelectListview.adapter = selectAppAdapter*/
            // selectAppAdapter.setSwitchChange(isChecked)

        })
        binding.appSelectAddBtn.setOnClickListener {
            for (item in addedApp) {
                sharedPreference.addSelectedApp(item.appPackageName, item)
            }
            navController?.popBackStack()
        }
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<AppData> = ArrayList<AppData>()

        // running a for loop to compare elements.
        for (item in packList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.appName.toString()
                    .toLowerCase()
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            // Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            selectAppAdapter.filterList(filteredlist)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectAppFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun m15462e(i: Int) {
        if (this.f11937b == null) {
            this.f11937b = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
            this.f11938c = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
        }
        val findViewById: View = binding.appSelectAddBtn
        var i2 = 8
        try {
            val animation = findViewById.animation
            if (animation != null) {
                if (animation === this.f11937b && i == 0) {
                    findViewById.animation.setAnimationListener(null as Animation.AnimationListener?)
                    findViewById.clearAnimation()
                    findViewById.startAnimation(this.f11938c)
                    findViewById.visibility = 8
                } else if (animation === this.f11938c && i > 0) {
                    findViewById.animation.setAnimationListener(null as Animation.AnimationListener?)
                    findViewById.clearAnimation()
                    this.f11937b?.setAnimationListener(
                        C3637b(
                            findViewById
                        )
                    )
                    findViewById.startAnimation(this.f11937b)
                }
            } else if (i > 0) {
                if (findViewById.visibility != 0) {
                    this.f11937b?.setAnimationListener(
                        C3638c(

                            findViewById
                        )
                    )
                    findViewById.startAnimation(this.f11937b)
                }
            } else if (findViewById.visibility == 0) {
                findViewById.startAnimation(this.f11938c)
                findViewById.visibility = 8
            }
        } catch (unused: Exception) {
            if (i > 0) {
                i2 = 0
            }
            findViewById.visibility = i2
        }
    }


    internal class C3637b(
        /* synthetic */val f11940a: View
    ) :
        Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            f11940a.visibility = 0
        }

        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationStart(animation: Animation) {}
    }

    /* renamed from: com.sp.protector.free.AppSelectActivity$c */
    internal class C3638c(
        /* synthetic */val f11941a: View
    ) :
        Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            f11941a.visibility = 0
        }

        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationStart(animation: Animation) {}
    }

    override fun onAppClicked(appInfo: AppData, clicked: Boolean) {
        if (clicked) {
            addedApp.add(appInfo)
        } else {
            addedApp.remove(appInfo)
        }
        if (clicked || !addedApp.isNullOrEmpty()) {
            m15462e(1)
        } else {
            m15462e(0)
        }
        if (addedApp.size == packList.size) {
            binding.switchView.switchInclude.isChecked = true
        } else {
            binding.switchView.switchInclude.isChecked = false
        }

    }

}