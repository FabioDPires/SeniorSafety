package com.example.seniorsafety.managers;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import com.example.seniorsafety.RemindActivity;
import com.example.seniorsafety.database.Medication;
import com.example.seniorsafety.receivers.MedicationReceiver;
import java.sql.SQLOutput;
import java.util.Calendar;
public class MedicationReminder {
    private Context mContext;
    private AlarmManager mAlarmManager;
    public MedicationReminder(Context context) {
        mContext = context;
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
    public void setReminder(Medication medication, Calendar when, int requestCode) {
        Intent i = new Intent(mContext, MedicationReceiver.class);
        i.putExtra("medName", medication.getName());
        i.putExtra("medQuant", medication.getQuantity());
        i.putExtra("medTime",medication.getTime());
        i.putExtra("requestCode",requestCode);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, requestCode, i, 0);
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pi);
    }
    public void updateReminder(Medication medication, Calendar when, int requestCode) {
        Intent i = new Intent(mContext, MedicationReceiver.class);
        i.putExtra("medName", medication.getName());
        i.putExtra("medQuant", medication.getQuantity());
        i.putExtra("medTime",medication.getTime());
        i.putExtra("requestCode",requestCode);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pi);
    }
    public void cancelReminder(Medication medication, int requestCode) {
        Intent i = new Intent(mContext, MedicationReceiver.class);
        i.putExtra("medName", medication.getName());
        i.putExtra("medQuant", medication.getQuantity());
        i.putExtra("medTime",medication.getTime());
        i.putExtra("requestCode",requestCode);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.cancel(pi);
    }
}