package com.example.bruno.cookcalc.Controller;

public class ConfigController {

    private Integer idConfig;
    private String name;
    private String value;

    public ConfigController() {
    }

    public Integer getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(Integer idConfig) {
        this.idConfig = idConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConfigController{" +
                "idConfig=" + idConfig +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
