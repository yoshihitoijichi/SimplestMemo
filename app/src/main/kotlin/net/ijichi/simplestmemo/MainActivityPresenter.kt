package net.ijichi.simplestmemo

import android.content.Context
import android.content.Intent
import android.widget.EditText
import net.ijichi.simplestmemo.memolist.MemoState
import net.ijichi.simplestmemo.util.DialogUtil
import java.util.*

/**
 * Created by ijichiyoshihito on 2017/02/03.
 */
class MainActivityPresenter(val context: Context, val contract: MainActivityContract) {

  fun onCLickNewMemo(index: Int) {
    val intent = Intent(context, NewMemoActivity::class.java)
    intent.putExtra(NewMemoActivity.key_index, index)
    context.startActivity(intent)
  }

  fun onLongClickCell(state: MemoState) {
    // リスト表示用のアラートダイアログ
    val items = arrayOf<CharSequence>("Remove", "Edit", "Cancel")
    DialogUtil(context).showListDialog(items, { which ->
      when (which) {
        0 -> DialogUtil(context).showButtonDialog (
          "Confirmation",
          "Remove this file?",
          true,
          true,
          { removeListItem(state) }
        )
        1 ->
          showEditDialog { editText -> editListItemTitle(state, editText) }
        2 -> { }
      }
    })
  }

  private fun showEditDialog(editCallback: (String) -> Unit){
    val editView = EditText(context)
    editView.setSingleLine()
    DialogUtil(context).showEditViewDoubleButtonDialog(editView, { editText ->
      editCallback.invoke(editText)
    })
  }

  private fun removeListItem(state: MemoState){
    contract.removeState(state)
  }

  private fun editListItemTitle(state: MemoState, editText: String){
//    if(newFile.exists()){// 既に存在する場合エラー
//      showEdiTextOverlapDialog()
//    }else{
      val newState = MemoState()
      newState.index = state.index
      newState.title = editText
      newState.updateDate = Date()
      contract.removeState(state)
      contract.addState(newState)
//    }
  }

  fun showEdiTextOverlapDialog(){
    val title = "overlap error"
    val message = "Please Change other edit text"
    DialogUtil(context).showButtonDialog(title, message, true)
  }

}