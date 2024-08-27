package com.user.management.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GeneralResponse<T> {
    private String code;
    private String message;
    private T data;

    public GeneralResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> GeneralResponse<T> Ok() {
        return new GeneralResponse<>("00", "Success", null);
    }

    public static <T> GeneralResponse<T> Ok(T data) {
        return new GeneralResponse<>("00", "Success", data);
    }
}

