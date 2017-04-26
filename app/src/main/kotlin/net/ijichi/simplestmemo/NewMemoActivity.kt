package net.ijichi.simplestmemo

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import net.ijichi.simplestmemo.util.DialogUtil
import net.ijichi.simplestmemo.util.PrefsUtil
import java.util.*

class NewMemoActivity: Activity() {

  // view
  private val titleEdit: EditText
    get() = findViewById(R.id.title_edit_text) as EditText
  private val contentsEdit: EditText
    get() = findViewById(R.id.contents_edit_text) as EditText
  private val saveButton: Button
    get() = findViewById(R.id.new_memo_save_button) as Button
  private val cancelButton: Button
    get() = findViewById(R.id.new_memo_cancel_button) as Button

  // data
  private var index = 0
  private var oldTitle = ""
  private var oldContents = ""

  companion object {
    val key_index = "KEY_INDEX"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_memo)

    index = intent?.getIntExtra(key_index, 0) ?: 0

    setupData()
    setupClickListener()
  }

  private fun setupData(){
    oldTitle = PrefsUtil.getTitle(index)
    oldContents = PrefsUtil.getContents(index)
    titleEdit.setText(oldTitle)
    contentsEdit.setText(oldContents)
  }

  private fun setupClickListener(){
    saveButton.setOnClickListener {
      if(titleEdit.text.isEmpty())return@setOnClickListener
      PrefsUtil.saveData(index, titleEdit.text.toString(), contentsEdit.text.toString(), Date())
      oldTitle = titleEdit.text.toString()
      oldContents = contentsEdit.text.toString()
      Toast.makeText(this, "saved" , Toast.LENGTH_SHORT).show()

      (this.callingActivity as? MainActivity)?.updateState(index)
    }

    cancelButton.setOnClickListener {
      if(oldTitle == titleEdit.text.toString() && oldContents == contentsEdit.text.toString())
        finish()
      else  DialogUtil(this).showButtonDialog("Confirmation", "change exist", true, true, { finish() })
    }
  }

}
