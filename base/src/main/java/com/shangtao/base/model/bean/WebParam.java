package com.shangtao.base.model.bean;

import java.io.Serializable;

public class WebParam implements Serializable {

    private String url;

    public WebParam(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
