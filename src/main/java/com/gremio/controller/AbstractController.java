package com.gremio.controller;

import com.gremio.model.dto.response.PageableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * An abstract controller class providing common utility methods for handling paginated responses.
 */
@RequiredArgsConstructor
public abstract class AbstractController {

    /**
     * Creates a {@link PageableResponse} containing the information from the provided {@link Page} object.
     *
     * @param page The Page object containing the paginated data.
     * @param <T>  The type of the paginated data.
     * @return A PageableResponse containing the paginated data and relevant pagination information.
     */
    protected <T> PageableResponse<T> getPageableResponse(final Page<T> page) {
        return new PageableResponse<>(page.getNumber(), page.getTotalPages(), page.getTotalElements(), page.getSize(),
                page.getContent());
    }
}