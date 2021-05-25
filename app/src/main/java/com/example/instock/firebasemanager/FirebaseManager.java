package com.example.instock.firebasemanager;

import com.example.instock.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;;

public class FirebaseManager {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public FirebaseManager(FirebaseAuth mAuth){
        this.mAuth = mAuth;
    }

    public void writeNewUser(String userId, String name, String email) {
        Usuario user = new Usuario(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }

}
