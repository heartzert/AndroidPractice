package heartzert.test.all.samples.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import heartzert.test.all.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.baidu.com")
            .build()

        retrofit.create(MyApi::class.java).testfun().enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                println(response.body().toString())
            }

        })
    }

    interface MyApi {
        @GET
        fun testfun(): Call<String>
    }
}
