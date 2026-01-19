package com.revature.Util;

import java.util.List;
import java.util.Objects;

/**
 * The Page class represents a paginated collection of items, along with
 * metadata that facilitates a fluid paging experience for users. This
 * class encapsulates the items on the current page as well as
 * information about the total number of pages and elements. It supports
 * generics to allow flexibility in the type of items stored in the
 * page.
 *
 * @param <E> Type of elements stored in the page
 */
public class Page<E> {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private int totalElements;
    private List<E> items;

    public Page() {}

    public Page(int pageNumber, int pageSize, int totalPages, int totalElements, List<E> items) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.items = items;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, pageSize, totalPages, totalElements, items);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Page<?> page = (Page<?>) obj;
        return pageNumber == page.pageNumber && pageSize == page.pageSize && totalPages == page.totalPages && totalElements == page.totalElements && Objects.equals(items, page.items);
    }
}
