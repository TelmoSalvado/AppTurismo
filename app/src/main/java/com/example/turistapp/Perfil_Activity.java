package com.example.turistapp;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Perfil_Activity extends AppCompatActivity {

   RecyclerView recyclerView;
   DatabaseReference databaseReference;
   ListView listView;
    ArrayList<Perfil> list;
    PerfilAdapter perfilAdapter;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    Button atualizar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil2);
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = findViewById(R.id.viewProfile);
        atualizar = findViewById(R.id.btn_update_carro);
        databaseReference = db.getReference("users");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        perfilAdapter = new PerfilAdapter(this, list);
        recyclerView.setAdapter(perfilAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.d("Child Event,Verse", dataSnapshot.getKey());
                    Log.d("Child Event,Verse", user);
                    if( dataSnapshot.getKey().equals(user) ){
                        Perfil perfil = dataSnapshot.getValue(Perfil.class);
                        list.add(perfil);
                    }

                }
                perfilAdapter.notifyDataSetChanged();
            
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Perfil_Activity.this,"ERROR GETTING DATA", Toast.LENGTH_LONG).show();
            }

        });


        /*db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });*/

    }

    private void ShowDialog(final String id){
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.activity_update_perfil,null);
        mDialog.setView(mDialogView);

        EditText name, datadenascimento, localidade, codigopostal, telefone, contribuinte;

        name = mDialogView.findViewById(R.id.upd_editTextNome);
        datadenascimento = mDialogView.findViewById(R.id.upd_editTextData);
        localidade = mDialogView.findViewById(R.id.upd_editTextLocalidade);
        codigopostal = mDialogView.findViewById(R.id.upd_editTextCPostal);
        telefone = mDialogView.findViewById(R.id.upd_editTextTelefone);
        contribuinte = mDialogView.findViewById(R.id.upd_editTextContribuinte);

        mDialog.show();

        Button button = mDialogView.findViewById(R.id.buttonGuardar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mName = name.getText().toString();
                String mdatadenascimento = datadenascimento.getText().toString();
                String mlocalidade = localidade.getText().toString();
                String mcodigopostal = codigopostal.getText().toString();
                String mctelefone = telefone.getText().toString();
                String mcontribuinte = contribuinte.getText().toString();

                updateData(id, mName, mdatadenascimento, mlocalidade, mcodigopostal, mctelefone, mcontribuinte);
            }
        });
    }
    private void updateData(String id, String name, String datadenascimento,  String localidade,String codigopostal,String telefone,String contribuinte){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(id);
        Map<String, String> user  = new HashMap<>();
        user.put("Name", name);
        user.put("Date", datadenascimento);
        user.put("Local", localidade);
        user.put("Cpostal", codigopostal);
        user.put("Contribuinte", contribuinte);
        user.put("Telefone", telefone);

        reference.setValue(user);

    }
}