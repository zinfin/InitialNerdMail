package com.bignerdranch.android.nerdmail.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bignerdranch.android.nerdmailservice.Email;
import com.bignerdranch.android.nerdmailservice.EmailBroadcaster;

public class EmailBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "EmailBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Email email = (Email) intent
                .getSerializableExtra(EmailBroadcaster.EMAIL_EXTRA);
        // Don't notify about spam
        if (!email.isSpam()){
            Intent emailIntent = EmailService.getNotifyIntent(context, email);
            context.startService((emailIntent));
        }
    }
}