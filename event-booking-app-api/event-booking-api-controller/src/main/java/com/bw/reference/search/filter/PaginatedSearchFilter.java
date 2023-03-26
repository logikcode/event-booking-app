package com.bw.reference.search.filter;

import lombok.Setter;

import java.util.Optional;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/

@Setter
public class PaginatedSearchFilter {

    private Integer offset;
    private Integer limit;

    public Optional<Integer> getOffset() {
        return Optional.ofNullable(offset);
    }

    public Optional<Integer> getLimit() {
        return Optional.ofNullable(limit);
    }
}
