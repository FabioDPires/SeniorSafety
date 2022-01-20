package com.example.seniorsafety;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
public class RemindActivity extends AppCompatActivity {
    private Button confirmButton;
    private TextView nameTV, quantTV;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences sp;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);
        mediaPlayer = MediaPlayer.create(this, R.raw.nucler_siren);
        mediaPlayer.start();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.sp = PreferenceManager.getDefaultSharedPreferences(this);
        String medName = getIntent().getStringExtra("medName");
        int medQuant = getIntent().getIntExtra("medQuant", 0);
        String quantString = Integer.toString(medQuant);
        this.nameTV = (TextView) findViewById(R.id.medicationNameDetailsRemind);
        this.quantTV = (TextView) findViewById(R.id.medicationQuantityRemind);
        this.nameTV.setText(medName);
        this.quantTV.setText("Quantity: "+quantString);
        this.confirmButton = (Button) findViewById(R.id.confirm_button);
        this.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS(medName);
            }
        });
    }

    private void sendSMS(String medName) {
        String username = this.firebaseAuth.getCurrentUser().getDisplayName();
        String sms = username + " took " + medName;
        String number = sp.getString("emergencyNumber", "");
        System.out.println("Number " + number);
        if (number.equals("")) {
            Toast.makeText(getApplicationContext(), "You don't have a emergency contact. Please add one", Toast.LENGTH_LONG).show();
            mediaPlayer.stop();
            finish();
            return;
        }

        SmsManager man = SmsManager.getDefault();
        man.sendTextMessage(number, null, sms, null, null);
        Toast.makeText(getApplicationContext(), "We informed your emergency contact that you took your medicine.", Toast.LENGTH_LONG).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        mediaPlayer.stop();
        finish();
    }
}