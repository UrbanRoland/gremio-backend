package com.gremio.controller;

import com.gremio.model.dto.response.PageableResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;

/**
 * An abstract controller class providing common utility methods for handling paginated responses.
 */
@RequiredArgsConstructor
public abstract class AbstractController {
    
    @Getter
    private final ConversionService conversionService;

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

    /**
     * Creates a {@link PageableResponse} of a different type (T) by converting the content of the provided {@link Page} (S).
     *
     * @param page        The Page object containing the original paginated data.
     * @param targetClass The target class (T) to which each item in the Page content will be converted.
     * @param <S>         The type of the original paginated data.
     * @param <T>         The type to which the original paginated data will be converted.
     * @return A PageableResponse containing the converted paginated data and relevant pagination information.
     */
    protected <S, T> PageableResponse<T> getPageableResponse(final Page<S> page, final Class<T> targetClass) {
        final List<T> collection = page
            .stream()
            .map(item -> conversionService.convert(item, targetClass))
            .collect(Collectors.toList());
        
        return new PageableResponse<>(page.getNumber(), page.getTotalPages(), page.getTotalElements(), page.getSize(), collection);
    }
}