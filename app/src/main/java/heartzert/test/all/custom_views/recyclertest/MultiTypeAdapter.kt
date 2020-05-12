package heartzert.test.all.custom_views.recyclertest

/**
 * Created by heartzert on 2019/10/22.
 * Email: heartzert@163.com
 */
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import heartzert.test.all.R

class MultiTypeAdapter(private val dataSet:ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val  TYPE_TEXT = 0
        private const val TYPE_CATEGORY = 1
    }

    private var mCategoryViewHolder:SimpleCategoryViewHolder? = null

    override fun getItemViewType(position: Int): Int {
        return if(dataSet[position] is String) {
            TYPE_TEXT
        } else {
            TYPE_CATEGORY
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("========", "onCreateViewHolder")
        return if(viewType == TYPE_TEXT) {
            SimpleTextViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_item_text,viewGroup,false))
        } else {
            val categoryViewHolder = SimpleCategoryViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_item_category,viewGroup,false))
            mCategoryViewHolder =  categoryViewHolder
            return categoryViewHolder
        }
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        Log.d("========", "onBindViewHolder")
        if(holder is SimpleTextViewHolder) {
            holder.mTv.text = dataSet[pos] as String
        } else if(holder is SimpleCategoryViewHolder){
            holder.bindData(dataSet[pos])
        }
    }

    fun getCurrentChildRecyclerView(): ChildRecyclerView? {
        mCategoryViewHolder?.apply {
            return this.getCurrentChildRecyclerView()
        }
        return null
    }


}

class SimpleCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mTabLayout: TabLayout = itemView.findViewById(R.id.tabs) as TabLayout
    private val mViewPager: ViewPager = itemView.findViewById(R.id.viewPager) as ViewPager

    val viewList = ArrayList<ChildRecyclerView>()

    private var mCurrentRecyclerView :ChildRecyclerView? = null

    init {

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                if(viewList.isEmpty().not()) {
                    mCurrentRecyclerView = viewList[position]
                    Log.d("gaohui","onPageSelected: $position $mCurrentRecyclerView")
                }
            }
            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    fun bindData(obj: Any) {
        //TODO 需要优化，这里每次item被回收时都会重新setupWithViewPager
        (obj as? CategoryBean)?.apply {
            viewList.clear()
            tabTitleList.forEach{ _ ->
                val categoryView = CategoryView(itemView.context)
                viewList.add(categoryView)
            }
            mCurrentRecyclerView = viewList[mViewPager.currentItem]
            val lastItem = mViewPager.currentItem
            Log.d("gaohui","bindData: ${mViewPager.currentItem} $mCurrentRecyclerView")

            mViewPager.adapter = CategoryPagerAdapter(viewList,tabTitleList)
            mTabLayout.setupWithViewPager(mViewPager)
            mViewPager.currentItem = lastItem
        }
    }

    fun getCurrentChildRecyclerView(): ChildRecyclerView? {
        return mCurrentRecyclerView
    }
}

class CategoryPagerAdapter(
    private val viewList: ArrayList<ChildRecyclerView>,
    private val tabTitleList: ArrayList<String>
) : PagerAdapter() {
    override fun getCount(): Int {
        return viewList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = viewList[position]
        if (container == view.parent) {
            container.removeView(view)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //container.removeView((View) object);
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitleList[position]
    }
}

class SimpleTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mTv: TextView = itemView.findViewById(R.id.textView) as TextView
}

class CategoryBean {
    var tabTitleList = ArrayList<String>()
}