package com.example.lenovo.myapplication;

public class GonulluKullanicilar {
    String ad,email,profilResmi,soyad;

    public GonulluKullanicilar() {
    }

    public GonulluKullanicilar(String ad, String email, String profilResmi, String soyad) {
        this.ad = ad;
        this.email = email;
        this.profilResmi = profilResmi;
        this.soyad = soyad;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilResmi() {
        return profilResmi;
    }

    public void setProfilResmi(String profilResmi) {
        this.profilResmi = profilResmi;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }
}
