package com.etybeno.common.model;

/**
 * Created by thangpham on 21/02/2018.
 */
public class Pager {

    private int buttonsToShow = 5;
    private int startPage;
    private int endPage;
    private int currentPage;
    private int totalPages;
    private long totalCount;

    public Pager(long totalCount) {
        this.totalCount = totalCount;
    }

    public Pager(int totalPages, int currentPage, int buttonsToShow, long totalCount) {
        setButtonsToShow(buttonsToShow);
        int halfPagesToShow = getButtonsToShow() / 2;
        if (totalPages <= getButtonsToShow()) {
            setStartPage(1);
            setEndPage(totalPages);
        } else if (currentPage - halfPagesToShow <= 0) {
            setStartPage(1);
            setEndPage(getButtonsToShow());
        } else if (currentPage + halfPagesToShow == totalPages) {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(totalPages);
        } else if (currentPage + halfPagesToShow > totalPages) {
            setStartPage(totalPages - getButtonsToShow() + 1);
            setEndPage(totalPages);
        } else {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(currentPage + halfPagesToShow);
        }
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getButtonsToShow() {
        return buttonsToShow;
    }

    public void setButtonsToShow(int buttonsToShow) {
        if (buttonsToShow % 2 != 0) this.buttonsToShow = buttonsToShow;
        else throw new IllegalArgumentException("Must be an odd value!");
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "Pager [startPage=" + startPage + ", endPage=" + endPage + "]";
    }
}
