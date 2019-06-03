package com.deyushuo.base;


import java.util.List;
import java.util.Map;

public class Nachrichtenleicht_Artikel_Item {
    public String sub_Titel;
    public String main_Context;
    public List<Map<String,String>> discrption;
    public String audio_Link;

    public Nachrichtenleicht_Artikel_Item(String sub_Titel,String main_Context,List<Map<String,String>> discrption,String audio_Link) {
        this.audio_Link = audio_Link;
        this.discrption = discrption;
        this.sub_Titel =sub_Titel;
        this.main_Context = main_Context;
    }

    public Nachrichtenleicht_Artikel_Item(){

    }

    public String getAudio_Link() {
        return audio_Link;
    }

    public String getMain_Context() {
        return main_Context;
    }

    public List<Map<String,String>> getDiscrption() {
        return discrption;
    }

    public String getSub_Titel() {
        return sub_Titel;
    }

    public void setAudio_Link(String audio_Link) {
        this.audio_Link = audio_Link;
    }

    public void setDiscrption(List<Map<String,String>> discrption) {
        this.discrption = discrption;
    }

    public void setMain_Context(String main_Context) {
        this.main_Context = main_Context;
    }

    public void setSub_Titel(String sub_Titel) {
        this.sub_Titel = sub_Titel;
    }
}
