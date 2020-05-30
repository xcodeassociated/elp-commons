package com.xcodeassociated.commons.paging;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.xcodeassociated.commons.paging.view.BaseView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class JsonViewAwarePage<T> {

    @JsonView(BaseView.class)
    @JsonProperty("pageable")
    private NumberHolder number;
    @JsonView(BaseView.class)
    private int numberOfElements;
    @JsonView(BaseView.class)
    private List<T> content;
    @JsonView(BaseView.class)
    private int size;
    @JsonView(BaseView.class)
    private long totalElements;
    @JsonView(BaseView.class)
    private int totalPages;

    public JsonViewAwarePage(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.number = new NumberHolder(page.getNumber() + 1);
        this.numberOfElements = page.getNumberOfElements();
        this.size = page.getSize();
    }

    public JsonViewAwarePage(List<T> all, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        List<T> pageData = all.stream()
                .skip(pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        this.number = new NumberHolder(pageNumber + 1);
        this.size = pageable.getPageSize();
        this.content = pageData;
        this.numberOfElements = pageData.size();
        this.totalElements = all.size();
        this.totalPages = all.size() % pageSize == 0 ? all.size() / pageSize : all.size() / pageSize + 1;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NumberHolder {
        @JsonView(BaseView.class)
        private int pageNumber;
    }

}
