package heartzert.test.all.custom_views.recyclertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import heartzert.test.all.BR
import heartzert.test.all.R
import heartzert.test.all.databinding.ActivityRecyclerViewTestBinding
import kotlinx.android.synthetic.main.activity_recycler_view_test.parentRecyclerView
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

class RecyclerViewTestActivity : AppCompatActivity() {

    val itemList = ObservableArrayList<String>()
    val items = MergeObservableList<Any>().insertList(itemList)
    val itemBinding = OnItemBindClass<Any>()
        .map(String::class.java) { itemBinding: ItemBinding<Any>, i: Int, s: String ->
            itemBinding.set(BR.string, R.layout.item_recyclerview)
        }

    private val mDataList = ArrayList<Any>()
    private val strArray = arrayOf("关注", "推荐", "视频", "直播", "图片", "段子", "精华", "热门")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityRecyclerViewTestBinding>(this, R.layout.activity_recycler_view_test)
        binding.viewModel = this

        for (x in 1..100) {
            itemList.add("1")
        }

        parentRecyclerView.initLayoutManager()
        initData()
    }

    private fun initData() {
        val multiTypeAdapter = MultiTypeAdapter(mDataList)
        for (i in 0..8) {
            mDataList.add("parent item text $i")
        }
        val categoryBean = CategoryBean()
        categoryBean.tabTitleList.clear()
        categoryBean.tabTitleList.addAll(strArray.asList())
        mDataList.add(categoryBean)
        parentRecyclerView.adapter = multiTypeAdapter
        multiTypeAdapter.notifyDataSetChanged()
    }

}
