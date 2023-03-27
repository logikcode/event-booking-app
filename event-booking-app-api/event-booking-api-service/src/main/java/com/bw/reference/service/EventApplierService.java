package com.bw.reference.service;

import com.bw.reference.domain.EventApplierDto;
import com.bw.reference.entity.EventApplier;
import org.springframework.stereotype.Service;

@Service
public interface EventApplierService {
    EventApplier bookEvent(EventApplierDto applierInfo);

}
