package com.example.turistapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CriarAnuncio extends AppCompatActivity {

    EditText titulo, localidade, informacao, datadeentrada, preco, nrpessoas;
    CheckBox wifi, animais, ac, varanda, fumadores, vista;
    ImageView imageView;
    Button save, cancelar;
    Uri imageUri;
    private FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        titulo = findViewById(R.id.editTextTItulo);
        localidade = findViewById(R.id.editTextLocalidade);
        informacao = findViewById(R.id.editTextInformações);
        datadeentrada = findViewById(R.id.editTextDatadeEntrada);
        preco = findViewById(R.id.editTextPreco);
        nrpessoas = findViewById(R.id.editTextNr);

        wifi = findViewById(R.id.checkBoxWifi);
        animais = findViewById(R.id.checkBoxAnimais);
        ac = findViewById(R.id.checkBoxAC);
        varanda = findViewById(R.id.checkBoxVaranda);
        vista = findViewById(R.id.checkBoxVista);
        fumadores = findViewById(R.id.checkBoxFumadores);
        imageView = findViewById(R.id.imageView);

        save= findViewById(R.id.buttonCriar);
        cancelar = findViewById(R.id.buttonCancelar1);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePic();
            }
        });
        setContentView(R.layout.activity_criar_anuncio);
    }

    private void choosePic(){
        Intent intent = new Intent();
        intent.setType("images/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uploadPic();
        }

    }

    private void uploadPic() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference reference = storageReference.child("images/" + user);
        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(androidx.appcompat.R.id.content), "Image Uploaded", Snackbar.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentagem: " + (int) progressPercent+ "" );
            }
        });
    }
}