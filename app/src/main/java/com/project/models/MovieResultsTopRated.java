package com.project.models;

import java.util.List;

/**
 * Created by anandmishra on 12/11/16.
 */

public class MovieResultsTopRated {

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    Integer page;
    List<Result> results;
}
