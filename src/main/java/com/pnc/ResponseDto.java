package com.pnc;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ResponseDto<T> implements Serializable {
    private ResponseStatus status;
    private T data;

    private ResponseDto(T data, ResponseStatus responseStatus){
        this.data=data;
        this.status=responseStatus;

    }
    public static <T> ResponseDto<T> forSuccess(T data){
        return new ResponseDto(data, ResponseStatus.SUCCESS);
    }

    public static <T> ResponseDto<T> forFailure(T data){
        return new ResponseDto(data, ResponseStatus.ERROR);
    }
}
