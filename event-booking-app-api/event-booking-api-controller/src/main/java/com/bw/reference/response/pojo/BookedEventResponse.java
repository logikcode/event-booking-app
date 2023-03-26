package com.bw.reference.response.pojo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class BookedEventResponse {
    private String eventName;
    private  String description;
    private String venue;
    private Date startDate;
    private Date endDate;
    private String street;
    private String state;
    private String city;
    private String eventVenueNumber;

}
