package com.bignerdranch.android.nerdmail.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.bignerdranch.android.nerdmail.model.DataManager;
import com.bignerdranch.android.nerdmail.model.EmailNotifier;
import com.bignerdranch.android.nerdmailservice.Email;

/**
 * Created by sand8529 on 8/18/16.
 */
public class EmailService extends IntentService {
  private  static final String TAG = "EmailService";
  public final static String EXTRA_EMAIL = "com.bignerdranch.android.nerdMail.EMAIL_EXTRA";
  public final static String EXTRA_CLEAR = "com.bignerdranch.android.nerdMail.CLEAR_EXTRA";
  private DataManager mDataManager;

  public EmailService(){
    super(TAG);
    mDataManager = DataManager.get(this);
  }
  @Override protected void onHandleIntent(Intent intent) {
    boolean shouldClear = intent.getBooleanExtra(EXTRA_CLEAR, false);
    if (shouldClear){
      clearEmails();
    }else{
      Email email = (Email)intent.getSerializableExtra(EXTRA_EMAIL);
      mDataManager.insertEmail(email);

      mDataManager.getNotificationEmails().doOnNext(emails -> {
        //notify ser about emails
        EmailNotifier emailNotifier = EmailNotifier.get(EmailService.this);
        emailNotifier.notifyOfEmails(emails);
      }).subscribe();
    }


  }

  private void clearEmails() {
    mDataManager.markEmailsAsNotified();
  }

  public static Intent getNotifyIntent(Context context, Email email){
    Intent intent = new Intent(context, EmailService.class);
    intent.putExtra(EXTRA_EMAIL, email);
    return intent;
  }
  public static Intent getClearIntent(Context context){
    Intent intent = new Intent(context, EmailService.class);
    intent.putExtra(EXTRA_CLEAR, true);
    return intent;
  }
}
