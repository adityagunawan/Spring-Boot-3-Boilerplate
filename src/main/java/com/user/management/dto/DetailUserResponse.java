package com.user.management.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DetailUserResponse {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private Date createdDate;
    private Date modifiedDate;
}
