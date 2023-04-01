package com.example.anukoolfinal.utility

import android.content.Context
import android.content.SharedPreferences
import com.example.anukoolfinal.model.AppData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class Preference(val context: Context) {

    private val PREFS_NAME = "AnuKool"
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val selectedApp = "selectedApp"
        const val profileData = "profileData"

    }


    fun removeSelectedApp(uniqueId: String?) {
        val map = loadMap()
        map.remove(uniqueId)
        val gson = Gson()
        val hashMapString = gson.toJson(map)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(selectedApp, hashMapString)
        editor.apply()
    }

    fun addSelectedApp(
        uniqueId: String,
        selectedProductItems: AppData
    ) {
        val outputMap = loadMap()
        outputMap.put(uniqueId, selectedProductItems)
        val gson = Gson()
        val hashMapString = gson.toJson(outputMap)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString("selectedApp", hashMapString)
        editor.apply()
    }

    fun appAdded(uniqueId: String?): Boolean {
        val myStrings: HashMap<String?, AppData?> =
            loadMap() as HashMap<String?, AppData?>
        return myStrings.containsKey(uniqueId)
    }

    fun getSelectedApp(uniqueId: String?): AppData? {
        return loadMap()?.get(uniqueId)
    }

    fun getSelectedAppList(): MutableCollection<AppData?>? {
        return loadMap()?.values?.toMutableList()
    }

    fun loadMap(): MutableMap<String?, AppData?> {
        /*val outputMap: MutableMap<String?, SelectedProductItems?> = HashMap()
         try {
             if (sharedPref != null) {
                 val jsonString = sharedPref.getString(selectedProductItem, JSONObject().toString())
                 if (jsonString != null) {
                     val jsonObject = JSONObject(jsonString)
                     val keysItr = jsonObject.keys()
                     while (keysItr.hasNext()) {
                         val key = keysItr.next()
                         val value = jsonObject[key] as SelectedProductItems
                         outputMap[key] = value
                     }
                 }
             }
         } catch (e: JSONException) {
             e.printStackTrace()
         }
         return outputMap*/

        val gson = Gson()
//         if(){
//
//         }
        val storedHashMapString: String? = sharedPref.getString(selectedApp, "")
        val type = object : TypeToken<HashMap<String?, AppData?>?>() {}.type
        var testHashMap2:  HashMap<String?, AppData?>?  =
            gson.fromJson(storedHashMapString, type)
        if(testHashMap2.isNullOrEmpty()){
            testHashMap2 = HashMap()
        }
        return testHashMap2

    }

    fun logoutUser() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(selectedApp)
        editor.remove(profileData)
        editor.apply()
    }
}