package com.tutoring.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;
import com.tutoring.service.PushNotificationService;
import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by chirag.agrawal on 3/27/2018.
 */

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    PushNotificationService pushNotificationsService;


    /**
     *
     * @param token - registered device token
     * @param topic - topic you want to subscribe
     * @return
     */
    @RequestMapping(value = "/{token}/topics/{topic}", method = RequestMethod.POST)
    public void subscribeTokenToTopic(@PathVariable("token") String token, @PathVariable("topic") String topic)  {
        String topics[] = AppUtils.getStringArrayFromString(topic, AppConstants.SEMI_COLON);
        List<String> registrationTokens = Arrays.asList(token);
        try {
            // Subscribe the devices corresponding to the registration tokens to the
            // topic.
            TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopicAsync(registrationTokens, topic).get();
            // See the TopicManagementResponse reference documentation
            // for the contents of response.
            System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send()  {
        String body = "{\n" +
                "\t\t   \"notification\": {\n" +
                "\t\t      \"title\": \"JSA Notification\",\n" +
                "\t\t      \"body\": \"Happy Message!\"\n" +
                "\t\t   },\n" +
                "\t\t   \"data\": {\n" +
                "\t\t      \"Key-1\": \"JSA Data 1\",\n" +
                "\t\t      \"Key-2\": \"JSA Data 2\"\n" +
                "\t\t   },\n" +
                "\t\t   \"to\": \"/topics/testing\",\n" +
                "\t\t   \"priority\": \"high\"\n" +
                "\t\t}";

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = pushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
