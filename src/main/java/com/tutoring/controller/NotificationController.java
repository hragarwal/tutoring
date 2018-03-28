package com.tutoring.controller;

import com.tutoring.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by chirag.agrawal on 3/27/2018.
 */

@RestController
public class NotificationController {

    private final String TOPIC = "JavaSampleApproach";

    @Autowired
    PushNotificationService pushNotificationsService;

    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send()  {

       /* JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "JSA Notification");
        notification.put("body", "Happy Message!");

        JSONObject data = new JSONObject();
        data.put("Key-1", "JSA Data 1");
        data.put("Key-2", "JSA Data 2");

        body.put("notification", notification);
        body.put("data", data);
    */
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
