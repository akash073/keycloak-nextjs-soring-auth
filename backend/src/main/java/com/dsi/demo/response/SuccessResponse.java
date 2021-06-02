package com.dsi.demo.response;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
public class SuccessResponse<T extends Object> extends ApiResponse implements Serializable {

    private Map<String, T> data;

    protected void setCommonData(T value, String message)
    {
        super.status = HttpStatus.OK.value();
        super.message = message;
       /* if(value == null) {
            throw new ValidationException("Success response can not be null");
        }*/
        String key = value.getClass().getSimpleName().toLowerCase();
        Map<String, T> data = new HashMap<String,T>();
        data.put(key,value);
        this.data = data;
    }

    public SuccessResponse(T value){
        super(true);
        setCommonData(value, HttpStatus.OK.name());
    }

    public SuccessResponse(T value,String message){
        super(true);
        setCommonData(value,message);
    }


}
