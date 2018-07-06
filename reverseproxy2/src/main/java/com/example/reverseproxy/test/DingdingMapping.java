package com.example.reverseproxy.test;

public class DingdingMapping {
    private int id;
    private String userName;
    private String userid;//用户id
    private String coAppName;
    private String corpid;//企业id
    private String mobile;
    private boolean register;
    private long bindingTime;
    private boolean binding;
    private String nodeKey;
    private String properties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCoAppName() {
        return coAppName;
    }

    public void setCoAppName(String coAppName) {
        this.coAppName = coAppName;
    }

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public long getBindingTime() {
        return bindingTime;
    }

    public void setBindingTime(long bindingTime) {
        this.bindingTime = bindingTime;
    }

    public boolean isBinding() {
        return binding;
    }

    public void setBinding(boolean binding) {
        this.binding = binding;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
