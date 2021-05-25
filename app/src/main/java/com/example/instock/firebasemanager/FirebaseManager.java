package com.example.instock.firebasemanager;

import com.example.instock.models.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {

    private DatabaseReference mDatabase;

    public FirebaseManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void writeNewUser(String userId, String correoUsuario, String nombresUsuario,
                             String apellidosUsuario, String nombreEmpresa) {
        Usuario user = new Usuario(correoUsuario, nombresUsuario, apellidosUsuario, nombreEmpresa);

        mDatabase.child("users").child(userId).setValue(user);
    }
}
