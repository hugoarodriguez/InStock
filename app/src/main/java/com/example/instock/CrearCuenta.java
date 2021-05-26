package com.example.instock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.instock.firebasemanager.FirebaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class CrearCuenta extends AppCompatActivity {

    EditText etNombres;
    EditText etApellidos;
    EditText etEmpresa;
    EditText etCorreo;
    EditText etPassword;
    EditText etConfirmarPass;
    TextInputLayout tilNombres, tilApellidos, tilNombreEmpresa, tilEmail, tilPassword, tilCPassword;
    Button btnRegistrar;
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_crear_cuenta);

        //Instancia del DrawerLayout
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.dl_main_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //Enlazamos el ActionBar con el DrawerLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Habilitamos el ActionBar y establecemos el ícono de "Atrás"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        firebaseAuth = FirebaseAuth.getInstance();

        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etEmpresa = findViewById(R.id.etNombreEmpresa);
        etCorreo = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmarPass = findViewById(R.id.etCPassword);
        btnRegistrar = findViewById(R.id.btnLogIn);
        tilNombres = findViewById(R.id.tilNombres);
        tilApellidos = findViewById(R.id.tilApellidos);
        tilNombreEmpresa = findViewById(R.id.tilNombreEmpresa);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilCPassword = findViewById(R.id.tilCPassword);
        edtChangeListenerAll();

        awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        awesomeValidation.addValidation(this, R.id.tilNombres, ".{1,}", R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.tilApellidos, ".{1,}", R.string.invalid_lastname);
        awesomeValidation.addValidation(this, R.id.tilNombreEmpresa, ".{1,}", R.string.invalid_company_name);
        awesomeValidation.addValidation(this, R.id.tilEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).{6,}";
        awesomeValidation.addValidation(this, R.id.tilPassword, regexPassword, R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.tilCPassword, R.id.tilPassword, R.string.invalid_confirmation_password);

        crearCuenta();

    }

    private void crearCuenta(){
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = etCorreo.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                String confirmPass = etConfirmarPass.getText().toString().trim();
                String nombres = etNombres.getText().toString().trim();
                String apellidos = etApellidos.getText().toString().trim();
                String empresa = etEmpresa.getText().toString().trim();

                if (awesomeValidation.validate()) {
                    firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String userID = task.getResult().getUser().getUid();

                                FirebaseManager firebaseManager = new FirebaseManager();
                                //Registramos los datos del usuario en Firebase
                                firebaseManager.writeNewUser(userID, mail, nombres, apellidos, empresa);

                                Toast.makeText(CrearCuenta.this, "Usuario creado con exito", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                dameToastdeError(errorCode);
                            }
                        }
                    });
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
        edtChangedListener(etNombres, tilNombres);
        edtChangedListener(etApellidos, tilApellidos);
        edtChangedListener(etEmpresa, tilNombreEmpresa);
        edtChangedListener(etCorreo, tilEmail);
        edtChangedListener(etPassword, tilPassword);
        edtChangedListener(etConfirmarPass, tilCPassword);
    }

    private void dameToastdeError(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(CrearCuenta.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(CrearCuenta.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(CrearCuenta.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(CrearCuenta.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                tilEmail.setError("La dirección de correo electrónico está mal formateada.");
                etCorreo.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(CrearCuenta.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                tilPassword.setError("la contraseña es incorrecta ");
                etPassword.requestFocus();
                etPassword.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(CrearCuenta.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(CrearCuenta.this, "Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(CrearCuenta.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(CrearCuenta.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta.", Toast.LENGTH_LONG).show();
                tilEmail.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                etCorreo.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(CrearCuenta.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(CrearCuenta.this, "La cuenta de usuario ha sido inhabilitada por un administrador.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(CrearCuenta.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(CrearCuenta.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(CrearCuenta.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(CrearCuenta.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(CrearCuenta.this, "La contraseña proporcionada no es válida.", Toast.LENGTH_LONG).show();
                tilPassword.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                etPassword.requestFocus();
                break;

        }
    }
}