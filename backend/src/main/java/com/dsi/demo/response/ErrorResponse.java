package com.dsi.demo.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/*
{
    "timestamp": 1563684770034,
    "status": 500,
    "error": "Internal Server ApiError",
    "exception": "javax.servlet.ServletException",
    "message": "Missing or invalid Authorization header.",
    "path": "/api/v1/hello"
}
 */

public class ErrorResponse extends ApiResponse implements Serializable {
   // private Object data;
    public ErrorResponse(HttpStatus httpStatus, String message){
        super(false);
        super.status = httpStatus.value();
        super.message = message;
        //this.data = null;
    }

}
