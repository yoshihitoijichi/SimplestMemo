package net.ijichi.simplestmemo.memolist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import java.util.*

/**
 * Created by ijichiyoshihito on 2017/02/03.
 */
class MemoListAdapter(context: Context, states: ArrayList<MemoState>, val listPresenter: MemoListPresenter) : ArrayAdapter<MemoState>
(context, 0, states) {

  var states: ArrayList<MemoState>? = states
  var positionViewList: HashMap<Int, MemoListItemView> = HashMap()

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val view = if(convertView == null) MemoListItemView(context, listPresenter) else convertView as MemoListItemView
    view.update(getItem(position))
    positionViewList.put(position, view)
    return view
  }

  fun getPositionView(targetPosition: Int): MemoListItemView? {
    val targetView = positionViewList[targetPosition]
    return targetView
  }

  override fun getCount(): Int {
    return states?.size ?: 0
  }


}