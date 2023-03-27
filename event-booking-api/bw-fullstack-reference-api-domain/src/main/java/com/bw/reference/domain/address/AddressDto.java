package com.bw.reference.domain.address;

import com.bw.reference.constraint.ExistsColumnValue;
import com.bw.reference.domain.address.GpsCoordinateDto;
import com.bw.reference.entity.City;
import com.bw.reference.entity.State;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@Getter
@Setter
public class AddressDto {

    private String zipCode;

    @NotBlank
    @ExistsColumnValue(columnName = "code", value = State.class)
    private String state;

    @ExistsColumnValue(columnName = "code", value = City.class)
    private String city;

    @NotBlank
    private String streetAddress;
    private String houseNumber;

    @Valid
    private GpsCoordinateDto gpsCoordinate;
}