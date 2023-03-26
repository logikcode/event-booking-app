package com.bw.reference.controller;


import com.bw.commons.security.constraint.Public;
import com.bw.reference.entity.City;
import com.bw.reference.entity.Country;
import com.bw.reference.entity.State;
import com.bw.reference.search.filter.CitySearchFilter;
import com.bw.reference.search.filter.CountrySearchFilter;
import com.bw.reference.search.filter.StateSearchFilter;
import com.bw.reference.search.handler.CitySearchHandler;
import com.bw.reference.search.handler.CountrySearchHandler;
import com.bw.reference.search.handler.StateSearchHandler;
import com.bw.reference.util.PredicateExtractor;
import com.querydsl.core.QueryResults;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Public
@RequiredArgsConstructor
@RestController
@RequestMapping("/location")
@Tag(name = "location", description = "Location API")
public class LocationController {

    private final CountrySearchHandler countrySearchHandler;
    private final StateSearchHandler stateSearchHandler;
    private final PredicateExtractor predicateExtractor;
    private final CitySearchHandler citySearchHandler;


    @GetMapping("/countries")
    @Operation(summary = "search countries", description = "filter countries")
    @ApiResponses(value = @ApiResponse(responseCode = ("" + HttpStatus.SC_OK), description = "Ok"))
    public QueryResults<Country> searchCountries(CountrySearchFilter filter) {
        return countrySearchHandler.search(filter, predicateExtractor.getPredicate(filter));
    }

    @GetMapping("/states")
    @Operation(summary = "search states", description = "filter states using name, code parameters")
    @ApiResponses(value = @ApiResponse(responseCode = ("" + HttpStatus.SC_OK), description = "Ok"))
    public QueryResults<State> searchStates(StateSearchFilter filter) {
        return stateSearchHandler.search(filter, predicateExtractor.getPredicate(filter));
    }

    @GetMapping("/cities")
    @Operation(summary = "search cities", description = "find cities")
    @ApiResponses(value = @ApiResponse(responseCode = ("" + HttpStatus.SC_OK), description = "Ok"))
    public QueryResults<City> searchCities(CitySearchFilter filter) {
        return citySearchHandler.search(filter, predicateExtractor.getPredicate(filter));
    }
}
