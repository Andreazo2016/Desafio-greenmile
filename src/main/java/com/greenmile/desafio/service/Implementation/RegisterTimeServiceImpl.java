package com.greenmile.desafio.service.Implementation;

import com.greenmile.desafio.domain.RegisterTime;
import com.greenmile.desafio.repository.RegisterTimeRepository;
import com.greenmile.desafio.service.RegisterTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RegisterTimeServiceImpl implements RegisterTimeService {

    private RegisterTimeRepository registerTimeRepository;

    @Autowired
    public RegisterTimeServiceImpl( RegisterTimeRepository registerTimeRepository ){
        this.registerTimeRepository = registerTimeRepository;
    }

    @Override
    public Page<RegisterTime> getAllRegisterByUser( Pageable pageable, Long id) {
        return registerTimeRepository.findByUserId( pageable, id );
    }

    @Override
    public RegisterTime save(RegisterTime registerTime) {
        return registerTimeRepository.save( registerTime );
    }
}
