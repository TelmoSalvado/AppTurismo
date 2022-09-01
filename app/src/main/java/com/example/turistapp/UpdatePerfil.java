package com.example.turistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class UpdatePerfil extends AppCompatActivity {
    EditText name, datadenascimento, localidade, codigopostal, telefone, contribuinte, email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_perfil);
    }
}