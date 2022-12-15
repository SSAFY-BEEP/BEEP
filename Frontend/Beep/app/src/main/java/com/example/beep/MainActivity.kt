package com.example.beep

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.beep.di.MainApplication
import com.example.beep.ui.navigation.RootNavGraph
import com.example.beep.ui.theme.BLUE500
import com.example.beep.ui.theme.BeepTheme
import com.example.beep.util.CHANNEL_ID
import com.example.beep.util.SoundEffectPlayer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private var pressedTime: Long = 0

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        try {
            getFirebaseToken()
            createFirebaseChannel()
        } catch (e: Exception) {
            Log.d("Firebase", e.toString())
        }

        setContent {
            BeepTheme {
                SoundEffectPlayer.readyEffects(LocalContext.current)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RootNavGraph()
                }
            }
        }
    }

    // 백버튼 메소드
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBackPressed() {
        // 이전터치 +0.5sec 이하면 앱종료
        if (pressedTime + 500 > System.currentTimeMillis()) {
            // 따닥이면 앱 종료
            super.onBackPressed()
            finish()
        } else {
            // 아님 이전페이지로
            super.onBackPressed()
        }
        // on below line initializing our press time variable
        pressedTime = System.currentTimeMillis();
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundEffectPlayer.releaseEffects()
    }

    private fun getFirebaseToken() {
        // Firebase 토큰 가져오기
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Firebase", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            MainApplication.sharedPreferencesUtil.saveFcmToken(token)
            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d("Firebase", msg)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    private fun createFirebaseChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun runtimeEnableAutoInit() {
        Firebase.messaging.isAutoInitEnabled = true
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // Inform user that that your app will not show notifications.
        }
    }
}







@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BeepTheme {
    }
}