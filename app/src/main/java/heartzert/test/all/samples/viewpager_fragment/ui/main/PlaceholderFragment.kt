package heartzert.test.all.samples.viewpager_fragment.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import heartzert.test.all.R
import java.util.zip.Inflater

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : BaseFragment() {

    var inflater: Inflater? = null
    var container: ViewGroup? = null
    var savedInstanceState: Bundle? = null
    var isInit = false
    var isLazyLoad = true

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (isLazyLoad) {

        } else {
            onCreateViewLazy(savedInstanceState)
        }

        for (x in 0..100000) {
            Log.d("=======", "123")
        }
        val root = inflater.inflate(R.layout.fragment_viewpager, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        pageViewModel.text.observe(this, Observer<String> {
            textView.text = it
        })
        return root
    }

    fun onCreateViewLazy(savedInstanceState: Bundle?) {
        val mSaved = savedInstanceState ?: this.savedInstanceState
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    fun tttt(view: View?) {

    }


}

//class CategoryFragment : BaseFragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.fragment_category, container, false)
//        initView(view)
//        return view
//    }
//
//    override fun onFragmentVisibleChange(isVisible: Boolean) {
//        if (isVisible) {
//            //更新界面数据，如果数据还在下载中，就显示加载框
//            notifyDataSetChanged()
//            if (mRefreshState === STATE_REFRESHING) {
//                mRefreshListener.onRefreshing()
//            }
//        } else {
//            //关闭加载框
//            mRefreshListener.onRefreshFinish()
//        }
//    }
//
//    override fun onFragmentFirstVisible() {
//        //去服务器下载数据
//        mRefreshState = STATE_REFRESHING
//        mCategoryController.loadBaseData()
//    }
//}