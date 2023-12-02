package com.gremio.model.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class PageableResponse<T> {
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int size;
    private List<T> items;
}