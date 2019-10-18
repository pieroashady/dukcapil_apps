package com.dika.dukcapil.Models;

public class Upload {
    Boolean defaultLang;
    String base64Id;

    public Upload(Boolean defaultLang, String base64Id){
        this.defaultLang = defaultLang;
        this.base64Id = base64Id;
    }
}