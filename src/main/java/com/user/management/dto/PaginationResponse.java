package com.user.management.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaginationResponse<T> {

    private Integer totalRow;
    private Integer totalPage;
    private T rows;

    public PaginationResponse(Integer totalRow, T rows, Integer limit) {
        this.totalRow = totalRow;
        this.rows = rows;
        this.totalPage = getTotalPage(limit);
    }

    private Integer getTotalPage(Integer limit) {
        if (limit == null) {
            return 1;
        }
        return limit >= totalRow ? 1 : totalRow / limit;
    }
}