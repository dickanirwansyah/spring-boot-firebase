package com.dicka.springfirebase.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseInitializer {

    @PostConstruct
    private void initDBFirebase() throws IOException {

        InputStream fileInputStream = this.getClass().getClassLoader()
                .getResourceAsStream("./notification-c1205-firebase-adminsdk-9mjw7-eeb38231e9.json");

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(fileInputStream))
                .setDatabaseUrl("https://notification-c1205-default-rtdb.firebaseio.com")
                .build();

        if (FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(firebaseOptions);
        }

    }

    public Firestore getFirebase(){
        return FirestoreClient.getFirestore();
    }
}
