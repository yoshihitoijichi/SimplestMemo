package net.ijichi.simplestmemo.util

import android.content.Context
import android.content.SharedPreferences
import java.util.*

/**
 * Created by ijichiyoshihito on 2017/02/15.
 */
object PrefsUtil{

  private var prefs: SharedPreferences? = null
  private var editor: SharedPreferences.Editor? = null

  enum class Key(val value: String){
    TITLE("title"),
    MEMO("memo"),
    UPDATE_DATE("update_time"),;
  }

  private val dataSave = "DataSave"

  fun setPreference(context: Context){
    prefs = context.getSharedPreferences(dataSave, Context.MODE_PRIVATE)
    editor = prefs?.edit()
  }

  fun saveData(index: Int, title: String, memo: String, date: Date){
    editor?.putString(Key.TITLE.value + index, title)
    editor?.putString(Key.MEMO.value + index, memo)
    editor?.putLong(Key.UPDATE_DATE.value + index, date.time)
    editor?.apply()
  }

  fun getAllTitles(): HashMap<String, *>{
    val titleMap = HashMap<String, String>()
    prefs?.all?.forEach {
      if(it.key.indexOf(Key.TITLE.value) >= 0){
        titleMap.put(it.key, it.value.toString())
      }
    }
    return titleMap
  }

  fun getTitle(index: Int): String{
    return prefs?.getString(Key.TITLE.value + index, "") ?: ""
  }

  fun getContents(index: Int): String{
    return prefs?.getString(Key.MEMO.value + index, "") ?: ""
  }

  fun getUpdateDate(index: Int): Date{
    return Date(prefs?.getLong(Key.UPDATE_DATE.value + index, 0) ?: 0)
  }

}