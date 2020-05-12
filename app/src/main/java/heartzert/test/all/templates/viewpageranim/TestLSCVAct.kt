package heartzert.test.all.templates.viewpageranim

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import heartzert.test.all.R
import heartzert.test.all.templates.viewpageranim.item.CardFragment
import heartzert.test.all.templates.viewpageranim.transformers.ITransformer
import heartzert.test.all.templates.viewpageranim.transformers.TransTet

class TestLSCVAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lscv)

//        val transformer : ITransformer = LeftSwipeCardViewTransformer()
        val transformer: ITransformer = TransTet()

        val fragments = arrayListOf<CardFragment>()
        for (x in 1..10) {
            fragments.add(CardFragment().apply { setText("$x") })
        }

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
            .apply {
                offscreenPageLimit = 2
                setPageTransformer(true, transformer)
            }
        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(pisition: Int): Fragment {
                return fragments[pisition]
            }

            override fun getCount(): Int {
                return fragments.size
            }

        }
    }
}
