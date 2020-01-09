package heartzert.test.all.nestedscrollview.custom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import heartzert.test.all.R
import heartzert.test.all.common.ParentRecyclerviewAdapter
import kotlinx.android.synthetic.main.activity_my_nested_scroll.recyclerView

class MyNestedScrollAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_nested_scroll)
        recyclerView.adapter = ParentRecyclerviewAdapter()
    }

}
