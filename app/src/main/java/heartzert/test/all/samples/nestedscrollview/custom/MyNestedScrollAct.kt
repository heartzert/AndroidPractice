package heartzert.test.all.samples.nestedscrollview.custom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import heartzert.test.all.R
import heartzert.test.all.common.ParentRecyclerviewAdapter

class MyNestedScrollAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_nested_scroll)
        findViewById<RecyclerView>(R.id.recyclerView).adapter = ParentRecyclerviewAdapter()
    }

}
