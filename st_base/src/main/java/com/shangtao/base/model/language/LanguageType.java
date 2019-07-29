package com.shangtao.base.model.language;

public enum LanguageType {
    CHINESE("zh"),ENGLISH("en");

    private final String type;

    LanguageType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public static LanguageType getLanguageType(String language) {
        switch (language){
            case "zh":
                return CHINESE;
            case "en":
                return ENGLISH;
            default:
                return ENGLISH;
        }
    }
}
