package heartzert.example.composetranslateapp.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Created by heartzert on 2023/9/6.
 * Email: heartzert@163.com
 */
class SplashActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashPage()
        }
    }

    @Composable
    private fun SplashPage() {
        Surface(modifier = Modifier.fillMaxSize()) {
            Image(painter =, contentDescription =)
        }
    }
}
