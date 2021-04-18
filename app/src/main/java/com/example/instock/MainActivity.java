package com.example.instock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void crearCuenta(View v) {
        Intent intent = new Intent(this, CrearCuenta.class);
        startActivity(intent);
    }

    public void iniciarSesion(View v){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void recuperarCuenta(View v){
        Intent intent = new Intent(this, RecuperarCuenta.class);
        startActivity(intent);
    }
}