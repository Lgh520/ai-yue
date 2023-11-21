package com.project.aiyue.dao.po;

import java.io.Serializable;

public class SysParam implements Serializable {
    private static final long serialVersionUID = 7806093017207835998L;
    private String paramCode;

    private String paramKey;

    private String paramValue;

    private String paramName;

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode == null ? null : paramCode.trim();
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey == null ? null : paramKey.trim();
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    @Override
    public String toString() {
        return "SysParam{" +
                "paramCode='" + paramCode + '\'' +
                ", paramKey='" + paramKey + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", paramName='" + paramName + '\'' +
                '}';
    }
}