package com.example.turistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
        holder.localidade.setText(perfil.getLocalidade());
        holder.telefone.setText(perfil.getTelefone());
        holder.codigopostal.setText(perfil.getCPostal());
        holder.contribuinte.setText(perfil.getContribuinte());
        holder.email.setText(perfil.getEmail());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, datadenascimento, localidade, codigopostal, telefone, contribuinte, email, password;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name =itemView.findViewById(R.id.textView3);
            datadenascimento =itemView.findViewById(R.id.item_data);
            localidade =itemView.findViewById(R.id.item_localidade);
            codigopostal =itemView.findViewById(R.id.item_CPostal);
            telefone =itemView.findViewById(R.id.item_telefone);
            contribuinte =itemView.findViewById(R.id.item_contribuinte);
            email =itemView.findViewById(R.id.item_email);
            password = itemView.findViewById(R.id.item_password);



        }
    }

}
