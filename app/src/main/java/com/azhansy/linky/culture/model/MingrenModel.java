package com.azhansy.linky.culture.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHU on 2016/7/4.
 * {
 "total": 134,
 "result": [
 {
 "famous_name": "爱因斯坦",
 "famous_saying": "“学校的目标始终应当是：青年人在离开学校时，是作为一个和谐的人，而不是作为一个专家。”"
 },
 {
 "famous_name": "爱因斯坦",
 "famous_saying": "由百折不挠的信念所支持的人的意志，比那些似乎是无敌的物质力量具有更大的威力。"
 },

 "error_code": 0,
 "reason": "Succes"
 }
 */
public class MingrenModel {
    private int total;
    private int error_code;
    private String reason;
    private List<MingrenDetailModel> result;

    private boolean state;
//    public MingrenModel(){
//    }
//    public MingrenModel(JSONObject object){
//        this.total = object.optInt("total");
//        this.error_code = object.optInt("error_code");
//        this.reason = object.optString("reason");
//        JSONArray result = object.optJSONArray("result");
//        if (result != null) {
//            mingrenDetailModelList = new ArrayList<>();
//            for (int i=0;i<result.length();i++) {
//                mingrenDetailModelList.add(new MingrenDetailModel(result.optJSONObject(i)));
//            }
//        }
//    }

    public class MingrenDetailModel{
        private String famous_name;
        private String famous_saying;

        public MingrenDetailModel(JSONObject object){
            this.famous_name = object.optString("famous_name");
            this.famous_saying = object.optString("famous_saying");
        }
        public String getFamous_name() {
            return famous_name;
        }

        public void setFamous_name(String famous_name) {
            this.famous_name = famous_name;
        }

        public String getFamous_saying() {
            return famous_saying;
        }

        public void setFamous_saying(String famous_saying) {
            this.famous_saying = famous_saying;
        }
    }

    public List<MingrenDetailModel> getResult() {
        return result;
    }

    public void setResult(List<MingrenDetailModel> result) {
        this.result = result;
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
