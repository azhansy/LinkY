package com.azhansy.linky.culture.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHU on 2016/7/4.
 */
public class ChengyuModel {
    private int total;
    private int error_code;
    private String reason;
    private List< ChengyuDetailModel> modelList;

    private boolean state;
    public ChengyuModel(){

    }
    public ChengyuModel(JSONObject object){
        this.total = object.optInt("total");
        this.error_code = object.optInt("error_code");
        this.reason = object.optString("reason");
        JSONArray result = object.optJSONArray("result");
        if (result != null) {
            modelList = new ArrayList<>();
            for (int i=0;i<result.length();i++) {
                modelList.add(new  ChengyuDetailModel(result.optJSONObject(i)));
            }
        }
    }

    public class  ChengyuDetailModel{
        private String id;
        private String name;

        public  ChengyuDetailModel(JSONObject object){
            this.id = object.optString("id");
            this.name = object.optString("name");
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public List<ChengyuDetailModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<ChengyuDetailModel> modelList) {
        this.modelList = modelList;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
