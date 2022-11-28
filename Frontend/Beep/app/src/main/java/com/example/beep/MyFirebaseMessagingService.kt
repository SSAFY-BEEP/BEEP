package com.example.beep

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("Firebase", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d("Firebase", "sendRegistrationTokenToServer($token)")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("Firebase", "From: ${remoteMessage.from}")
        // Data가 담겨있는지 확인하고 담겨있으면 데이터를 읽어 지정한 알림을 보냄
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("Firebase", "Message data payload: ${remoteMessage.data}")
            sendNotification(remoteMessage)
        } else {
            Log.e("Firebase", "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
        }

        // 알림 정보가 들어있으면 알림을 보냄
        remoteMessage.notification?.let {
            Log.d("Firebase", "Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    // 받은 알림을 기기에 표시하는 메서드
    private fun sendNotification(remoteMessage: RemoteMessage)
    {
        // RequestCode, Id를 고유값으로 지정하여 알림이 개별 표시
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()

        // 일회용 PendingIntent : Intent 의 실행 권한을 외부의 어플리케이션에게 위임
        val intent = Intent(this, MainActivity::class.java)
        //각 key, value 추가
        for(key in remoteMessage.data.keys){
            intent.putExtra(key, remoteMessage.data.getValue(key))
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Activity Stack 을 경로만 남김(A-B-C-D-B => A-B)
        val pendingIntent = PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_IMMUTABLE)

        // 알림 채널 이름
        val channelId = "test_channel"
        // 알림 소리
//        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val alarmUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/" + R.raw.pager2);
//        val player: MediaPlayer = MediaPlayer.create(this, R.raw.pager)
//        player.start()

        // 알림에 대한 UI 정보, 작업
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.beepalerticon) // 아이콘 설정
            .setContentTitle(remoteMessage.data["header"].toString()) // 제목
            .setContentText(remoteMessage.data["content"].toString()) // 메시지 내용
            .setAutoCancel(true) // 알람클릭시 삭제여부
            .setSound(alarmUri)  // 알림 소리
            .setContentIntent(pendingIntent) // 알림 실행 시 Intent

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//         오레오 버전 이후에는 채널이 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val notificationChannel = NotificationChannel(channelId, "Notice", importance)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            notificationChannel.setSound(alarmUri, audioAttributes)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // 알림 생성
        notificationManager.notify(uniId, notificationBuilder.build())
    }
}