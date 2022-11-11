package com.example.Beep.api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Configuration
public class FirebaseConfig {

    private FirebaseApp firebaseApp;

    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())      //환경 변수 설정으로 변경
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
