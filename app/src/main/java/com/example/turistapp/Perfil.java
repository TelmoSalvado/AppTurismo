package com.example.turistapp;

public class Perfil {

    private String Email;
    private String Name;
    private String Localidade;
    private String CPostal;
    private String contribuinte;
    private String mTelfone;
    private String data;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocalidade() {
        return Localidade;
    }

    public void setLocalidade(String localidade) {
        Localidade = localidade;
    }

    public String getCPostal() {
        return CPostal;
    }

    public void setCPostal(String CPostal) {
        this.CPostal = CPostal;
    }

    public String getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(String contribuinte) {
        this.contribuinte = contribuinte;
    }

    public String getmTelfone() {
        return mTelfone;
    }

    public void setmTelfone(String mTelfone) {
        this.mTelfone = mTelfone;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }



}
