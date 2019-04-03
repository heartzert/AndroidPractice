package heartzert.test.all.leftswipecard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import heartzert.test.all.R

class TestLSCVAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lscv)

        val fragments = arrayListOf<CardFragment>()
        for (x in 1..10) {
            fragments.add(CardFragment().apply { setText("$x") })
        }

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
            .apply {
                offscreenPageLimit = 4
                setPageTransformer(true, LeftSwipeCardViewTransformer())
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
