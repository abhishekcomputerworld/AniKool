package com.example.anukoolfinal.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.anukoolfinal.R
import com.example.anukoolfinal.databinding.FragmentHomeBinding
import com.example.anukoolfinal.model.AppData
import com.example.anukoolfinal.utility.Permission
import com.example.anukoolfinal.utility.Preference


class HomeFragment : Fragment() ,AppSelectedListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var navController: NavController? = null
    private lateinit var sharedPreference: Preference
    private lateinit var appListAdapter : AppListAdapter


    private var addedApp: ArrayList<AppData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

      //  val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
         //   textView.text = "kya haal hai"
        }





        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!Permission.isAccessGranted(requireActivity())) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }



        sharedPreference = Preference(requireContext())
        navController = Navigation.findNavController(view)
        setUpListener()

        addedApp= ((sharedPreference.getSelectedAppList()?: arrayListOf()) as ArrayList<AppData>)
        appListAdapter = AppListAdapter(
            requireContext(),
            addedApp,
            sharedPreference,
            this,
            false
        )
        binding!!.rlApps.adapter = appListAdapter
    }

    private fun setUpListener() {
        binding.button2.setOnClickListener {
            navController!!.navigate(R.id.action_navigation_home_to_selectAppFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAppClicked(appInfo: AppData, clicked: Boolean) {

    }
}