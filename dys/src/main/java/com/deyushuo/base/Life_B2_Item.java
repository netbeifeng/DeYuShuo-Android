package com.deyushuo.base;

public class Life_B2_Item {
    public String titel;
    public String context;
    public Life_B2_Item(String titel,String context) {
        this.context= context;
        this.titel= titel;
    }
    public String getContext() {
        return context;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
