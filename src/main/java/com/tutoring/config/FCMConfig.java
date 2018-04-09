package com.tutoring.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by NEX3JCE on 4/1/2018.
 */

@Configuration
public class FCMConfig {

    public FCMConfig( )  {
        try {
           // FileInputStream serviceAccount =
            //        new FileInputStream("tutoring-firebase.json");

            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("tutoring-firebase.json").getFile());

            FileInputStream serviceAccount =
                    new FileInputStream(file);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
