package com.greenmile.desafio.service;

import com.greenmile.desafio.domain.RegisterTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegisterTimeService {

    Page<RegisterTime> getAllRegisterByUser(Pageable pageable, Long id );
    RegisterTime save( RegisterTime registerTime );
}
