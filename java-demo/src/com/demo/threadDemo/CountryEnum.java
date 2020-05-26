package com.demo.threadDemo;


public enum CountryEnum {

    ZERO(0,"齐"),ONE(1,"楚"),TOW(2,"燕"),THREE(3,"赵"),FOUR(4,"魏"),FIVE(5,"韩");

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    CountryEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CountryEnum forEach(int i){
        CountryEnum[] array = CountryEnum.values();
        for (CountryEnum element : array) {
            if(element.getCode() == i){
                return element;
            }
        }
        return null;
    }
}
