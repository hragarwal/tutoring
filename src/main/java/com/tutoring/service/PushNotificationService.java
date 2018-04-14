package com.tutoring.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.tutoring.config.HeaderRequestInterceptor;
import com.tutoring.model.Lesson;
import com.tutoring.model.dto.NewLessonNotification;
import com.tutoring.model.dto.NotificationMessage;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.LessonStates;
import com.tutoring.util.NotificationType;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Created by chirag.agrawal on 3/27/2018.
 */

@Service
public class PushNotificationService {


    private static final String FIREBASE_SERVER_KEY = "AAAAoW5fOo8:APA91bFnmUTQXF_xMfrtBnymL6St5vl1IA4Wy8PAwzMbCDciWik8SwBcEzsgEWKqtItiAhYTwdZ56R5vyX2Wq5sluBp6qQlVqArRx-NM_n7JFZpJnl44vOrp7aq4Ovt9n-JqN0CGExDK";

    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    @Async
    public void notifyUsers(Lesson lesson) {
        try {
            NotificationMessage notificationMessage = null;
            if (lesson.getStatus().getId() == LessonStates.AVAILABLE) {
                notificationMessage = new
                        NotificationMessage(NotificationType.NEW_LESSON.getNumVal(), "New Lesson",
                        lesson.getStudentProfile().getName() + " requested for help in " + lesson.getSubjectName().getName() + AppConstants.DOT, null);
                NewLessonNotification lessonNotification = new NewLessonNotification();
                lessonNotification.setLessonUniqueId(lesson.getLessonUniqueId());
                notificationMessage.setData(lessonNotification);
            }

            String topic = "tutorAll";

            // See documentation on defining a message payload.
            Message message = Message.builder()
                    .putData("payload", AppUtils.getJSONValue(notificationMessage))
                    .setTopic(topic)
                    .build();

            // Send a message to the devices subscribed to the provided topic.
            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            // Response is a message ID string.
            System.out.println("Successfully sent message: " + response);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }


}
