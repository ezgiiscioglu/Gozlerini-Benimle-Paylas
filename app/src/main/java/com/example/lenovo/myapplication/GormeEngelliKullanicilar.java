package com.example.lenovo.myapplication;

public class GormeEngelliKullanicilar {
    String ad,email,sifre,soyad,video,ses;

    public GormeEngelliKullanicilar() {
    }

    public GormeEngelliKullanicilar(String ad, String email, String sifre, String soyad, String video) {
        this.ad = ad;
        this.email = email;
        this.sifre = sifre;
        this.soyad = soyad;
        this.video = video;
        this.ses = ses;
    }

    public String getSes() {
        return ses;
    }

    public void setSes(String ses) {
        this.ses = ses;
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

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
