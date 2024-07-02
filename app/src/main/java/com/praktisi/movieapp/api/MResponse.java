package com.praktisi.movieapp.api;

import java.util.List;
import com.praktisi.movieapp.models.Status;

public class MResponse<T> {
    public int page;
    public List<T> results;
    public int total_pages;
    public int total_results;
    public Status status;

    public MResponse(int page, List<T> results, int total_pages, int total_results, Status status) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.status = status;
    }

    public static <T> MResponse<T> success(int page, List<T> results, int total_pages, int total_results) {
        return new MResponse<>(page, results, total_pages, total_results, Status.SUCCESS);
    }

    public static <T> MResponse<T> done() {
        return new MResponse<>(0, null, 0, 0, Status.DONE);
    }

    public static <T> MResponse<T> failure() {
        return new MResponse<>(0, null, 0, 0, Status.FAILURE);
    }

    public static <T> MResponse<T> timeout() {
        return new MResponse<>(0, null, 0, 0, Status.TIMEOUT);
    }

}