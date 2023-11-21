package com.project.aiyue.responor;

import lombok.Data;

@Data
public class CommonRespon<T> {

    private Integer code;
    private T data;
    private String message;
}
