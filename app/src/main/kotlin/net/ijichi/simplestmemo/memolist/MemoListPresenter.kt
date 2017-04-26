package net.ijichi.simplestmemo.memolist

/**
 * Created by ijichiyoshihito on 2017/02/03.
 */
class MemoListPresenter {

  var onClickCell: ((state: MemoState)->Unit)? = null
  var onLongClickCell: ((state: MemoState)->Unit)? = null

}