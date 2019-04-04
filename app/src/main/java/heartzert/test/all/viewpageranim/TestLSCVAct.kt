package heartzert.test.all.viewpageranim

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import heartzert.test.all.R
import heartzert.test.all.viewpageranim.item.CardFragment
import heartzert.test.all.viewpageranim.transformers.ITransformer
import heartzert.test.all.viewpageranim.transformers.LeftSwipeCardViewTransformer

class TestLSCVAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lscv)

        val transformer : ITransformer = LeftSwipeCardViewTransformer()

        val fragments = arrayListOf<CardFragment>()
        for (x in 1..10) {
            fragments.add(CardFragment().apply { setText("$x") })
        }

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
            .apply {
                offscreenPageLimit = 4
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
