package org.example.productcatalogservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortParam {
    private String attribute;
    private SortType sortType;
}
