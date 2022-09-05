package com.example.turistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button iniciarSessao;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        iniciarSessao = findViewById(R.id.buttonIniciarSessao);
        firebaseAuth = FirebaseAuth.getInstance();
        iniciarSessao.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User Auth sucess", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, FragmentInitial.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "User Auth fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
    public void efetuarregisto(View view){
        Intent intent = new Intent(this, RegistActivity.class);
        startActivity(intent);
    }


}