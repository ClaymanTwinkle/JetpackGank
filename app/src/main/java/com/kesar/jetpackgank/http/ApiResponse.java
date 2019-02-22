package com.kesar.jetpackgank.http;

import java.util.List;

/**
 * ApiResponse
 *
 * @author andy <br/>
 * create time: 2019/2/20 14:12
 */
public class ApiResponse<T> {
    private List<String> category = null;
    private boolean isError = false;
    private T results = null;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "category=" + category +
                ", isError=" + isError +
                ", results=" + results +
                '}';
    }
}
