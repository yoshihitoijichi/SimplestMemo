package net.ijichi.simplestmemo.memolist

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import net.ijichi.simplestmemo.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ijichiyoshihito on 2017/02/03.
 */
class MemoListItemView: FrameLayout {

  lateinit var title: TextView
  lateinit var updateDate: TextView

  val listPresenter: MemoListPresenter

  constructor(context: Context, listPresenter: MemoListPresenter): super(context){
    init(context)
    this.listPresenter = listPresenter
  }

  private fun init(context: Context){
    val v = View.inflate(context, getLayout(), this)
    title = v?.findViewById(R.id.memo_list_item_title) as TextView
    updateDate = v?.findViewById(R.id.memo_list_item_update_date_text) as TextView
  }

  private fun getLayout(): Int{
    return R.layout.memo_list_item_view
  }

  fun update(state: MemoState){
    title.text = state.title
    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPAN)
    updateDate.text = df.format(state.updateDate)

    setOnClickListener {
      listPresenter.onClickCell?.invoke(state)
    }

    setOnLongClickListener {
      listPresenter.onLongClickCell?.invoke(state)
      true
    }
  }
}