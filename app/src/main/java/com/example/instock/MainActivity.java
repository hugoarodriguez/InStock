package com.example.instock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAutH;
    FirebaseAuth.AuthStateListener mAuthListener;
    private Button logIn;
    private EditText edtMail;
    private EditText edtPassword;

    private String email = "";
    private String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAutH = FirebaseAuth.getInstance();

        edtMail = findViewById(R.id.etEmail);
        edtPassword = findViewById(R.id.etPassword);
        logIn = findViewById(R.id.btnLogIn);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtMail.getText().toString();
                password = edtPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty())
                {
                    logInUser();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }// Fin onCreate

    private void logInUser()
    {
        mAutH.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No se pudo iniciar sesión, compruebe los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void crearCuenta(View v) {
        Intent intent = new Intent(this, CrearCuenta.class);
        startActivity(intent);
    }



    public void recuperarCuenta(View v){
        Intent intent = new Intent(this, RecuperarCuenta.class);
        startActivity(intent);
    }
}