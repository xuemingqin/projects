package com.example.welcomemyapplication.data;

public class ResponseObject<T>{
    private String state;
    private  T datas;
    public ResponseObject(){
        super();
    }

    public ResponseObject(String state, T datas){
        this.state=state;
        this.datas=datas;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;

    }
}
