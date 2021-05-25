package com.example.instock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarCuenta extends AppCompatActivity {
    private EditText mail;
    private Button recuperar;
    private ProgressDialog progress;
    private String correo;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_recuperar_cuenta);

        //Instancia del DrawerLayout
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.dl_main_menu);

        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //Enlazamos el ActionBar con el DrawerLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Habilitamos el ActionBar y establecemos el ícono de "Atrás"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);

        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        mail = findViewById(R.id.etEmail);
        recuperar = findViewById(R.id.btnConfirmar);
        progress = new ProgressDialog(this);

        getRecuperar();

    }

    private void getRecuperar() {
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = mail.getText().toString().trim();
                if (!correo.isEmpty())
                {
                    progress.setMessage("Espere un momento...");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                    getEnviarCorreo();
                } else
                {
                    Toast.makeText(getApplicationContext(), "El correo no se pudo envirar",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   private void getEnviarCorreo()
    {
        auth.setLanguageCode("es");
        auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>(){

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Por favor revisar su correo para recuperar cuenta",
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RecuperarCuenta.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    /*public void comprobar(View view)
    {
        Intent i = new Intent(RecuperarCuenta.this, CambiarPassword.class);
        startActivity(i);
    }*/
}