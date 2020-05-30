package com.xcodeassociated.commons.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public class CustomPageRequest {

    private SortDirection sortDirection;
    private String sortBy;
    private Integer page;
    private Integer size;

    public CustomPageRequest(Integer page, Integer size, SortDirection sortDirection, String sortBy) {
        this.sortDirection = Optional.ofNullable(sortDirection).orElse(SortDirection.asc);
        this.sortBy = Optional.ofNullable(sortBy).orElse("id");
        this.page = Optional.ofNullable(page).map(p -> Math.max(0, p - 1)).orElse(0);
        this.size = Math.max(1, Optional.ofNullable(size).orElse(1));
    }

    public Pageable toPageable() {
        return PageRequest.of(
                page,
                size,
                sortDirection.getDirection(),
                sortBy
        );
    }

}
