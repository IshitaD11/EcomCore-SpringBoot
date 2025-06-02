package org.example.productcatalogservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchProductRequestDto {
    private String query;
    private Integer pageNo;
    private Integer pageSize;
    List<SortParam> sortParamList;
}
