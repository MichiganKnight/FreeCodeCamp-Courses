package com.revature.Util;

/**
 * The PageOptions class is a Plain Old Java Object (POJO) that encapsulates the information necessary for creating paginated results. This class packages various options for pagination, such as the page number, page size, sorting criteria, and sorting direction. By encapsulating these options, the class allows for cleaner and more maintainable code by avoiding the need to pass multiple individual parameters to methods that support paging.
 */
public class PageOptions {
    private int pageNumber;
    private int pageSize;
    private String sortBy;
    private String sortDirection;

    public PageOptions() {}

    public PageOptions(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
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

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
