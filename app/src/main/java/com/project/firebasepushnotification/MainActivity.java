package com.project.firebasepushnotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.project.firebasepushnotification.firebaseFCM.FcmNotificationsSender;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
EditText editText,title;
String fcmToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.notificationTitleid);
        editText = findViewById(R.id.notificationBodyid);

        // for sending notification to all
        FirebaseMessaging.getInstance().subscribeToTopic("all");


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            fcmToken = Objects.requireNonNull(task.getResult()).getToken();

                        }

                    }
                });


    }

    public void sendNotificationToAll(View view) {
        String titles = title.getText().toString();
        String bodys = editText.getText().toString();
       // send_To_all(titles,bodys);
        send_specific(titles,bodys);
    }

    public void send_To_all(String title,String body) {

               FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",
                title, body,
                getApplicationContext(),
                MainActivity.this);

        notificationsSender.SendNotifications();


    }

    public void send_specific(String title,String body) {
       /* int i;
        for(i=0;i<fcmlist.size();i++){
            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(fcmlist.get(i),
                    "Specific Notification", "This is the Specific Notification body.",
                    getApplicationContext(),
                    MainActivity.this);

            notificationsSender.SendNotifications();
        }*/

        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(fcmToken,
                title, body,
                getApplicationContext(),
                MainActivity.this);

        notificationsSender.SendNotifications();

    }

}