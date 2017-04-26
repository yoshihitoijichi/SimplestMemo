package net.ijichi.simplestmemo

import net.ijichi.simplestmemo.memolist.MemoState

/**
 * Created by ijichiyoshihito on 2017/02/08.
 */
interface MainActivityContract{

  fun addState(state: MemoState)
  fun removeState(state: MemoState)


}