package com.greenmile.desafio.repository;

import com.greenmile.desafio.domain.RegisterTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterTimeRepository extends JpaRepository<RegisterTime, Long> {
    Page<RegisterTime> findByUserId(Pageable pageable, Long idUser );
}
