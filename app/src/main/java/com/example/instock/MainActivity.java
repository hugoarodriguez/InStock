package com.example.instock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instock.models.Usuario;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAutH;
    FirebaseAuth.AuthStateListener mAuthListener;
    private Button logIn;
    private EditText edtMail, edtPassword;
    private TextInputLayout tilEmail, tilPassword;
    private String email = "";
    private String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAutH = FirebaseAuth.getInstance();

        edtMail = findViewById(R.id.etEmail);
        edtPassword = findViewById(R.id.etPassword);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        edtChangeListenerAll();
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
                    if(email.isEmpty()){
                        tilEmail.setError(getText(R.string.invalid_login_email));
                    }
                    if(password.isEmpty()){
                        tilPassword.setError(getText(R.string.invalid_login_password));
                    }
                }
            }
        });

    }// Fin onCreate

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        mAutH = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAutH.getCurrentUser();

        //Si currentUser es diferente de null indica que hay una sesión iniciada
        if(currentUser != null){

            Intent intent = new Intent(MainActivity.this, MainMenu.class);
            startActivity(intent);
            finish();
        }

        return super.onCreateView(name, context, attrs);
    }

    private void logInUser()
    {
        mAutH.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser currentUser = mAutH.getCurrentUser();
                    String userEmail = currentUser.getEmail();

                    Usuario usuario = Usuario.getInstance();
                    usuario.setCorreoUsuario(userEmail);

                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No se pudo iniciar sesión, compruebe sus datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Método para enlazar los editText con el ChangedListener
    private void edtChangedListener(EditText editText, TextInputLayout til){

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    til.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //Método que activa la escucha del onChange de los EditText
    private void edtChangeListenerAll(){
        edtChangedListener(edtMail, tilEmail);
        edtChangedListener(edtPassword, tilPassword);
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