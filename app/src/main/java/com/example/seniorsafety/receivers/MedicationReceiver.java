package com.example.seniorsafety.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.seniorsafety.RemindActivity;

public class MedicationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("RECEIVED");
        String name=intent.getStringExtra("medName");
        int quant=intent.getIntExtra("medQuant",0);
        Intent i=new Intent(context, RemindActivity.class);
        i.putExtra("medName",name);
        i.putExtra("medQuant",quant);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
