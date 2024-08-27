package com.user.management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListUserFilterRequest {
    private String name;
    private String username;
    private String email;
}
