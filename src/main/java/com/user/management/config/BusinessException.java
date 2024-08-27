package com.user.management.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BusinessException extends Exception{
    private HttpStatusCode httpStatus;
    private String code;
    private String message;

    public BusinessException(HttpStatusCode httpStatus,String code, String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}

