package com.bignerdranch.android.nerdmail.model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.bignerdranch.android.nerdmail.R;
import com.bignerdranch.android.nerdmail.controller.DrawerActivity;
import com.bignerdranch.android.nerdmailservice.Email;

import java.util.List;

/**
 * Created by sand8529 on 8/18/16.
 */
public class EmailNotifier {
  private static final int EMAIL_NOTIFICATION_ID = 0;

  private static EmailNotifier sEmailNotifier;

  private Context mContext;

  private EmailNotifier(Context context){
    mContext = context.getApplicationContext();
  }
  public static EmailNotifier get(Context context){
    if (sEmailNotifier == null){
      sEmailNotifier = new EmailNotifier(context);
    }
    return sEmailNotifier;
  }
  public void notifyOfEmails(List<Email> emailList){
    if (emailList.isEmpty()){
      return;
    }
    Notification notification;
    if (emailList.size() ==1){
      notification = createSingleEmailNotification(emailList.get(0));
    }else{
      notification = createMultipleEmailNotifications(emailList);
    }
    NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    manager.notify(EMAIL_NOTIFICATION_ID, notification);
  }
  public void clearNotifications(){
    NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    manager.cancel(EMAIL_NOTIFICATION_ID);
  }

  private Notification createSingleEmailNotification(Email email) {
    String sender = email.getSenderAddress();
    String subject = email.getSubject();
    String body = email.getBody();

    Intent intnent = new Intent(mContext, DrawerActivity.class);
    PendingIntent pi = PendingIntent.getActivity(mContext, 0 , intnent, 0);

    return new NotificationCompat.Builder(mContext)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(sender)
        .setContentText(subject)
        .setSubText(body)
        .setContentIntent(pi)
        .setAutoCancel(true)
        .build();
  }
  private Notification createMultipleEmailNotifications(List<Email> emails){
    NotificationCompat.InboxStyle style = createInboxStyle(emails);

    Intent intent = new Intent(mContext, DrawerActivity.class);
    PendingIntent pi =  PendingIntent.getActivity(mContext, 0,  intent, 0);
    String contentTitle = mContext.getString(R.string.multiple_emails_title, emails.size());
    NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(contentTitle)
        .setContentIntent(pi)
        .setAutoCancel(true)
        .setStyle(style);
    return builder.build();
  }

  private NotificationCompat.InboxStyle createInboxStyle(List<Email> emails) {
    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
    String bigTitle = mContext.getString(R.string.multiple_emails_title, emails.size());
    inboxStyle.setBigContentTitle( bigTitle);

    int notificationCount = emails.size() >5 ? 5 : emails.size();
    for (int i =0 ; i < notificationCount; i++){
      Email email = emails.get(i);
      String text = email.getSenderAddress() + " " + email.getSubject();
      inboxStyle.addLine(text);
    }
    return inboxStyle;
  }

}
