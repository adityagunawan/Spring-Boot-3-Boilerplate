package com.user.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest<T> {
    private T filter;
    private Integer limit = 10;
    private Integer page = 1;

    private String sort;
    private String distinct;
    private Boolean ascending;
    private Boolean all;
}
