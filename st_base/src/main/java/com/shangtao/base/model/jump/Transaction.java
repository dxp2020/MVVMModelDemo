package com.shangtao.base.model.jump;

public class Transaction {

    private Class targetClass;

    private IFragmentParams params;

    private int requestCode;

    public Transaction(Class targetClass) {
        this.targetClass = targetClass;
    }

    public Transaction(IFragmentParams arg){
        this.params = arg;
    }

    public Transaction(Class targetClass, IFragmentParams params) {
        this.targetClass = targetClass;
        this.params = params;
    }

    public Transaction(Class targetClass, IFragmentParams params, int requestCode) {
        this.targetClass = targetClass;
        this.params = params;
        this.requestCode = requestCode;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public IFragmentParams getParams() {
        return params;
    }

    public void setParams(IFragmentParams params) {
        this.params = params;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public boolean isForResult(){
        return requestCode!=0;
    }
}
