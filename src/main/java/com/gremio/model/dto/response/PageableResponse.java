package com.gremio.model.dto.response;

import java.util.List;

public record PageableResponse<T>(
    int currentPage,
    int totalPages,
    long totalItems,
    int size,
    List<T> items
) {
}