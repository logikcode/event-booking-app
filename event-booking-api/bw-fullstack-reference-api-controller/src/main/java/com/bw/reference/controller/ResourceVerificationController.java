package com.bw.reference.controller;

import com.bw.commons.security.constraint.Public;
import com.bw.reference.Enumeration.ResourceConstant;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.dao.eventbooking.EventApplierRepository;
import com.bw.reference.service.PhoneNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resource/verify")
public class ResourceVerificationController {

    private final PhoneNumberService phoneNumberService;
    private final EventApplierRepository applierRepository;

    @Public
    @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<String> verifyResource(@RequestParam("identifier")
                                                 String identifier, @RequestParam("type")
                                                 ResourceConstant type) {
        if (type == ResourceConstant.email) {
            if (applierRepository.findActiveByEmail(identifier).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } else if (type == ResourceConstant.mobile_number) {
            if (applierRepository.findByPhoneNumber(phoneNumberService.formatPhoneNumber(identifier)).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}