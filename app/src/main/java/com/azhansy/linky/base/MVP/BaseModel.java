package com.azhansy.linky.base.MVP;


import com.azhansy.linky.R;
import com.azhansy.linky.base.LinkApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jd on 2015/10/12.
 */
public abstract class BaseModel {
    protected boolean state;
    protected int errorCode;
    protected String message;

    public BaseModel() {
    }

    public BaseModel(boolean state, int errorCode, String message) {
        this.state = state;
        this.errorCode = errorCode;
        this.message = message;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 解析请求返回的json
     *
     * @param json
     * @param <M>
     * @return
     */
    @SuppressWarnings(value = "unchecked")
    public <M extends BaseModel> M parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            this.state = jsonObject.optBoolean("state");
            this.errorCode = jsonObject.optInt("code");
            this.message = jsonObject.optString("message");
            // 解析data部分
            JSONObject dataObj = jsonObject.optJSONObject("data");
            if (dataObj != null) {
                parseData(dataObj);
            }
        } catch (JSONException e) {
            this.state = false;
            this.errorCode = 0;
            this.message = LinkApplication.getInstance().getString(R.string.parse_exception_message);
        }
        return (M) this;
    }

    /**
     * 解析json的data部分，不需要则空实现即可
     *
     * @param dataObj
     */
    protected abstract void parseData(JSONObject dataObj) throws JSONException;

    /**
     * 可解析单层次的json数组，要求数组元素都是JSONObject
     *
     * @param jsonArray
     * @param parser
     */
//    protected void parseJsonArray(JSONArray jsonArray, BaseJSONArrayParser parser) {
//        parser.parseJsonArray(jsonArray);
//    }
}
