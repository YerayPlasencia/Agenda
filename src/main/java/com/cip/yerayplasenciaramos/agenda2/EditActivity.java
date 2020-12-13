package com.cip.yerayplasenciaramos.agenda2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    Button back,upd_el,del_btn, btn_call, btn_sms, btn_maps;
    EditText name,lastname,address,phone,email;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        back = (Button) findViewById(R.id.back2);
        upd_el = (Button) findViewById(R.id.upd_element);
        del_btn = (Button) findViewById(R.id.del_btn);
        name= (EditText) findViewById(R.id.name);
        lastname= (EditText) findViewById(R.id.lastname);
        address= (EditText) findViewById(R.id.address);
        email= (EditText) findViewById(R.id.email);
        phone= (EditText) findViewById(R.id.phone);
        btn_call = (Button) findViewById(R.id.call);
        btn_sms = (Button) findViewById(R.id.sms);
        btn_maps = (Button) findViewById(R.id.maps);
        
        upd_el.setBackgroundColor(Color.BLUE);
        del_btn.setBackgroundColor(Color.RED);
        btn_call.setBackgroundColor(Color.GREEN);
        btn_sms.setBackgroundColor(Color.LTGRAY);
        btn_maps.setBackgroundColor(Color.MAGENTA);

        Intent i = getIntent();
        id = i.getLongExtra("id",0);
        name.setText(i.getStringExtra("name"));
        lastname.setText(i.getStringExtra("lastname"));
        phone.setText(i.getStringExtra("phone"));
        address.setText(i.getStringExtra("address"));
        email.setText(i.getStringExtra("email"));

        upd_el.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() > 0 && lastname.getText().toString().length() > 0) {
                    Contact c = new Contact(getBaseContext());
                    c.open();
                    c.updateContact(id, name.getText().toString(), lastname.getText().toString(), address.getText().toString(), email.getText().toString(), phone.getText().toString());
                    Toast.makeText(getBaseContext(), "Elemento Actualizado", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);

                builder.setTitle(" - Confirmar - ");
                builder.setMessage("Estas seguro que deseas eliminar ?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Contact c = new Contact(getBaseContext());
                        c.open();
                        c.deleteContact(id);
                        finish();
                        dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Elemento eliminado", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = phone.getText().toString();
                if(!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(EditActivity.this, "No has introducido un numero de telefono",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorTlfn = phone.getText().toString();
                Intent i = new Intent(EditActivity.this, SmsActivity.class);
                i.putExtra("phoneNo", valorTlfn);
                startActivity(i);
            }
        });

        btn_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorDireccion = address.getText().toString();
                Intent i = new Intent(EditActivity.this, MapsActivity.class);
                i.putExtra("st", valorDireccion);
                startActivity(i);
            }
        });
    }
}
