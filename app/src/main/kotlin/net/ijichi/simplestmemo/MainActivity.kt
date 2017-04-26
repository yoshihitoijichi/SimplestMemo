package net.ijichi.simplestmemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.ListView
import net.ijichi.simplestmemo.memolist.MemoListAdapter
import net.ijichi.simplestmemo.memolist.MemoListPresenter
import net.ijichi.simplestmemo.memolist.MemoState
import net.ijichi.simplestmemo.util.PrefsUtil
import java.util.*


class MainActivity: AppCompatActivity(), MainActivityContract {

  // view
  private val mainList: ListView
    get() = findViewById(R.id.main_list) as ListView
  private val newMemoButton: Button
    get() = findViewById(R.id.new_memo_button) as Button

  var presenter = MainActivityPresenter(this, this)
  private var listAdapter: MemoListAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val toolbar = findViewById(R.id.toolbar) as Toolbar
    toolbar.title = "SimplestMemo"

    newMemoButton.text = "New"

    PrefsUtil.setPreference(this)
    setupListener()
  }

  override fun onResume() {
    super.onResume()
    setupList()
  }

  private fun setupListener(){
    newMemoButton.setOnClickListener {
      presenter.onCLickNewMemo((listAdapter?.count ?: 0) + 1)
    }
  }

  private fun setupList(){
    val states = ArrayList<MemoState>()

    val prefsTitles = PrefsUtil.getAllTitles()
    for(i in 1..prefsTitles.size){
      val state = MemoState()
      state.index = i
      state.title = PrefsUtil.getTitle(i)
      state.updateDate = PrefsUtil.getUpdateDate(i)
      states.add(state)
    }

    val memoListPresenter = MemoListPresenter()
    memoListPresenter.onClickCell = { state ->
      presenter.onCLickNewMemo(state.index)
    }
    memoListPresenter.onLongClickCell = { state -> presenter.onLongClickCell(state) }
    listAdapter = MemoListAdapter(this, states, memoListPresenter)
    mainList.adapter = listAdapter

    scrollLastPosition()
  }

  private fun scrollLastPosition(){
    val lastCount = (listAdapter?.count ?: 0) - 1
    mainList.setSelection(lastCount)  // 最終行へゆっくりスクロール
  }

  override fun addState(state: MemoState) {
    listAdapter?.add(state)
    mainList.smoothScrollToPosition((listAdapter?.count ?: 0) - 1)  // 最終行へゆっくりスクロール
  }

  override fun removeState(state: MemoState) {
    listAdapter?.remove(state)
  }

  fun updateState(index: Int){
    listAdapter?.states?.forEach {
      if(it.index == index){
        it.updateDate = Date()
        val itemView = listAdapter?.getPositionView(listAdapter?.getPosition(it) ?: 0)
        itemView?.update(it)
      }
    }
  }

}

