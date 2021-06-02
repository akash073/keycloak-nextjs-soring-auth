package com.dsi.demo.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ListResponse<T extends List> extends ApiResponse implements Serializable {

    private Map<String, T> data;

    protected void setCommonData(T value , Class<?> classType,String message)
    {
        super.status = HttpStatus.OK.value();
        super.message = message;

        Map<String, T> data = new HashMap<String,T>();

       /* if(value == null){
            throw new ValidationException("List response can be null");
        }*/

        String key = classType.getSimpleName().toLowerCase() + "List";
        data.put(key,value);
        this.data = data;
    }

    public ListResponse(T value , Class<?> classType) {
        super(true);
        setCommonData(value,classType, HttpStatus.OK.name());
    }

    public ListResponse(T value , Class<?> classType,String message) {
        super(true);
        setCommonData(value,classType,message);
    }
}
