package com.cip.yerayplasenciaramos.agenda2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SmsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        String phoneNo = (String)getIntent().getExtras().getSerializable("phoneNo");

        Button sendMessageBtn = (Button) findViewById(R.id.btn_send_message);
        final EditText messagetEt = (EditText) findViewById(R.id.et_message);

        TextView tlfn = (TextView) findViewById(R.id.tlfn);
        tlfn.setText(phoneNo);

        //onClick sendMessage
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messagetEt.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    if (!TextUtils.isEmpty(phoneNo)) {
                        Intent smsIntent = new Intent(Intent.ACTION_SENDTO,
                                Uri.parse("smsto:" + phoneNo));
                        smsIntent.putExtra("sms_body", message);
                        startActivity(smsIntent);
                    } else {
                        Toast.makeText(SmsActivity.this, "No has introducido un numero de telefono", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SmsActivity.this, "No has introducido un mensaje" , Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
