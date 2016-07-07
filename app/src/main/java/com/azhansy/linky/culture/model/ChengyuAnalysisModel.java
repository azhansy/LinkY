package com.azhansy.linky.culture.model;

/**
 * Created by SHU on 2016/7/7.
 *
 {
 "result": {//返回结果集
 "id": "d420b457-4b86-4ab1-b824-cb84440131fc",//成语ID
 "name": "戛釜撞瓮",//成语
 "spell": "jiá  fǔ  zhuàng  wèng",//拼音
 "content": "刮磨锅子，碰撞陶器。比喻粗俗之音。",//解释
 "derivation": null,//典故
 "samples": null//示例
 },
 "error_code": 0,//返回状态码
 "reason": "Succes"//返回状态说明
 }
 */
public class ChengyuAnalysisModel {
    private String reason;
    private int error_code;
    private ChengyuAnalysisModelDetail result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ChengyuAnalysisModelDetail getResult() {
        return result;
    }

    public void setResult(ChengyuAnalysisModelDetail result) {
        this.result = result;
    }

    public class ChengyuAnalysisModelDetail{
        private String id;
        private String name;
        private String spell;
        private String content;
        private String samples;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSpell() {
            return spell;
        }

        public void setSpell(String spell) {
            this.spell = spell;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSamples() {
            return samples;
        }

        public void setSamples(String samples) {
            this.samples = samples;
        }

        @Override
        public String toString() {
            return "ChengyuAnalysisModelDetail{" +
                    ", name='" + name + '\'' + "\n"+
                    ", spell='" + spell + '\'' + "\n"+
                    ", content='" + content + '\'' + "\n"+
                    ", samples='" + samples + '\'' + "\n"+
                    '}';
        }
    }

}
