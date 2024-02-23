package com.project.aiyue.responor;

import com.project.aiyue.constant.ResponCodeConstant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonRespon<T> {

    private Integer code;
    private T data;
    private String message;

    public static <T> CommonRespon<T> success(T data){
        return CommonRespon.<T>builder()
                .code(ResponCodeConstant.SUCCESS_CODE)
                .message(ResponCodeConstant.SUCCESS_DESC)
                .data(data)
                .build();
    }

    public static <T> CommonRespon<T> success(T data,String msg){
        return CommonRespon.<T>builder()
                .code(ResponCodeConstant.SUCCESS_CODE)
                .message(msg)
                .data(data)
                .build();
    }

    public static <T> CommonRespon<T> error(int code,String msg){
        return CommonRespon.<T>builder()
                .code(code)
                .message(msg)
                .data(null)
                .build();
    }
}
