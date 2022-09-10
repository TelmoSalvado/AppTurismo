package com.example.turistapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turistapp.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.MyViewHolder> {

    Context context;
    ArrayList<Perfil> list;
    public PerfilAdapter(Context context,ArrayList<Perfil> list ){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public PerfilAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.perfilitem,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PerfilAdapter.MyViewHolder holder, int position) {

        MyViewHolder myViewHolder= (MyViewHolder)holder;

        Perfil perfil = list.get(position);
        holder.name.setText(perfil.getName());
        holder.datadenascimento.setText(perfil.getDate());
        holder.localidade.setText(perfil.getLocal());
        holder.telefone.setText(perfil.getTelefone());
        holder.codigopostal.setText(perfil.getCpostal());
        holder.contribuinte.setText(perfil.getContribuinte());


        String mName = holder.name.getText().toString();
        String mDatadenascimento = holder.datadenascimento.getText().toString();
        String mLocalidade = holder.localidade.getText().toString();
        String mTelefone = holder.telefone.getText().toString();
        String mCodigopostal = holder.codigopostal.getText().toString();
        String mContribuinte = holder.contribuinte.getText().toString();


        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                ShowDialog(user,mName,mDatadenascimento,mLocalidade,mCodigopostal, mTelefone,mContribuinte);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, datadenascimento, localidade, codigopostal, telefone, contribuinte;
        Button update;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name =itemView.findViewById(R.id.item_nome);
            datadenascimento =itemView.findViewById(R.id.item_data_nas);
            localidade =itemView.findViewById(R.id.item_loca);
            codigopostal =itemView.findViewById(R.id.item_cp);
            telefone =itemView.findViewById(R.id.item_tlf);
            contribuinte =itemView.findViewById(R.id.item_Contribuinte);


            update = itemView.findViewById(R.id.btn_update_carro);

        }
    }
    private void ShowDialog(final String id, String Name, String data, String loca, String Codigo, String tel, String cont){
        AlertDialog.Builder mDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mDialogView = inflater.inflate(R.layout.activity_update_perfil,null);
        mDialog.setView(mDialogView);

        EditText name, datadenascimento, localidade, codigopostal, telefone, contribuinte;

        name = mDialogView.findViewById(R.id.upd_editTextNome);
        datadenascimento = mDialogView.findViewById(R.id.upd_editTextData);
        localidade = mDialogView.findViewById(R.id.upd_editTextLocalidade);
        codigopostal = mDialogView.findViewById(R.id.upd_editTextCPostal);
        telefone = mDialogView.findViewById(R.id.upd_editTextTelefone);
        contribuinte = mDialogView.findViewById(R.id.upd_editTextContribuinte);

       name.setText(Name);
       datadenascimento.setText(data);
       localidade.setText(loca);
       codigopostal.setText(Codigo);
       telefone.setText(tel);
       contribuinte.setText(cont);

        Button button = mDialogView.findViewById(R.id.buttonGuardar);
        Button btnCan = mDialogView.findViewById(R.id.buttonCancelar);
        mDialog.show();
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
                Intent intent = new Intent(context, HomeFragment.class);
                context.startActivity(intent);
            }
        });
        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HomeFragment.class);
                context.startActivity(intent);
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
