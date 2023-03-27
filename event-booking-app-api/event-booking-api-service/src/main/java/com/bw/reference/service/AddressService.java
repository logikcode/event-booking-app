package com.bw.reference.service;


import com.bw.reference.domain.address.AddressDto;
import com.bw.reference.entity.Address;
import com.bw.reference.entity.Country;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

public interface AddressService {
    Address createAddress(AddressDto dto);
}