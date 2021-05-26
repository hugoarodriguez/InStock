package com.example.instock;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instock.models.ModalDialogValues;
import com.example.instock.utils.CreateDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ActualizarPasswordFragment extends Fragment {

    Button btnConfirmar;
    EditText etPasswordA, etPasswordN, etPasswordC;
    TextInputLayout tilAnterior, tilNueva, tilNuevaC;

    CreateDialog createDialog = new CreateDialog();
    ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();
    String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";

    public ActualizarPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_actualizar_password, container, false);

        instanciarVistas(v);
        edtChangeListenerAll();
        confirmar();

        return v;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        limpiarCampos();
        super.onViewStateRestored(savedInstanceState);
    }

    private void instanciarVistas(View v){
        tilAnterior = (TextInputLayout)v.findViewById(R.id.tilAnterior);
        tilNueva = (TextInputLayout)v.findViewById(R.id.tilNueva);
        tilNuevaC = (TextInputLayout)v.findViewById(R.id.tilNuevaC);
        etPasswordA = (EditText) v.findViewById(R.id.etPasswordA);
        etPasswordN = (EditText) v.findViewById(R.id.etPasswordN);
        etPasswordC = (EditText) v.findViewById(R.id.etPasswordC);
        btnConfirmar = (Button)v.findViewById(R.id.btnConfirmar);
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
        //Método para quitar alerta en onChanged
        edtChangedListener(etPasswordA, tilAnterior);
        edtChangedListener(etPasswordN, tilNueva);
        edtChangedListener(etPasswordC, tilNuevaC);
    }

    private void confirmar(){
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (etPasswordA.getText().toString().equals("")
                        || etPasswordN.getText().toString().equals("") || !etPasswordN.getText().toString().matches(regexPassword)
                        || etPasswordC.getText().toString().equals("")) {

                    if (etPasswordA.getText().toString().equals("")) {
                        tilAnterior.setError(getText(R.string.campo_requerido));
                    }
                    if (etPasswordN.getText().toString().equals("")
                            || !etPasswordN.getText().toString().matches(regexPassword)) {
                        tilNueva.setError(getText(R.string.invalid_password));
                    }
                    if (etPasswordC.getText().toString().equals("")) {
                        tilNuevaC.setError(getText(R.string.campo_requerido));
                    }
                } else {

                    //Verificamos si las contraseñas ingresadas coinciden
                    if(!etPasswordN.getText().toString().equals(etPasswordC.getText().toString())){
                        tilNuevaC.setError(getText(R.string.invalid_confirmation_password));
                        return;
                    }

                    tilNuevaC.setError(null);

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                    AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(),
                            etPasswordA.getText().toString().trim());

                    currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //Si las credenciales son las correctas
                            if(task.isSuccessful()){
                                //Confirmamos si el usuario desea cambiar su password
                                modalDialogValues.modalDialogValues(getResources().getString(R.string.actualizar_password_title),
                                        getResources().getString(R.string.actualizar_password_message));

                                createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        currentUser.updatePassword(etPasswordN.getText().toString()).
                                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        //Si se pudo actualizar la contraseña
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(getContext(),
                                                                    "Se actualizó la contraseña",
                                                                    Toast.LENGTH_SHORT).show();
                                                            limpiarCampos();

                                                        } else {
                                                            Toast.makeText(getContext(),
                                                                    "No se pudo actualizar la contraseña",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }).show();

                            } else{
                                etPasswordA.requestFocus();
                                tilAnterior.setError("No coincide con la contraseña registrada");
                            }
                        }
                    });
                }
            }
        });
    }

    private void limpiarCampos(){
        etPasswordA.setText(null);
        etPasswordN.setText(null);
        etPasswordC.setText(null);
        tilAnterior.setError(null);
        tilNueva.setError(null);
        tilNuevaC.setError(null);
        etPasswordA.requestFocus();
    }
}