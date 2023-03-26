package com.bw.reference.response.pojo;

import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/

@Builder
@AllArgsConstructor
public class QueryResultsPojo<T> {

    private final long limit, offset, total;

    private final List<T> results;

    public QueryResultsPojo(List<T> results, QueryResults<?> queryResults) {
        this.results = results;
        this.limit = queryResults.getLimit();
        this.offset = queryResults.getOffset();
        this.total = queryResults.getTotal();
    }

    public long getLimit() {
        return limit;
    }

    public long getOffset() {
        return offset;
    }

    public long getTotal() {
        return total;
    }

    public List<T> getResults() {
        return results;
    }
}