package com.example.turistapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.proto.TargetGlobal;

import java.util.HashMap;
import java.util.Map;

public class RegistActivity extends AppCompatActivity {
    EditText name, datadenascimento, localidade, codigopostal, telefone, contribuinte, email, password;
    String userId;
    Button registar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        name = findViewById(R.id.editTextNome);
        datadenascimento = findViewById(R.id.editTextData);
        localidade = findViewById(R.id.editTextLocalidade);
        codigopostal = findViewById(R.id.editTextCPostal);
        telefone = findViewById(R.id.editTextTelefone);
        contribuinte = findViewById(R.id.editTextContribuinte);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        registar = findViewById(R.id.buttonRegistar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        registar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();
                String mName = name.getText().toString().trim();
                String mLocalidade = localidade.getText().toString().trim();
                String mCPostal = codigopostal.getText().toString().trim();
                String mcontribuinte = contribuinte.getText().toString().trim();
                String mTelfone = telefone.getText().toString().trim();
                String mdata = datadenascimento.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            userId = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(userId);

                            HashMap<String, String> map = new HashMap<>();

                            Map<String, String> user  = new HashMap<>();
                            user.put("Name", mName);
                            user.put("Date", mdata);
                            user.put("Local", mLocalidade);
                            user.put("Cpostal", mCPostal);
                            user.put("Contribuinte", mcontribuinte);
                            user.put("Telefone", mTelfone);
                            user.put("email", mEmail);
                            user.put("password",mPassword);

                            databaseReference.setValue(user);
                            Intent intent = new Intent(RegistActivity.this, Menu.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(RegistActivity.this, "User not Created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}