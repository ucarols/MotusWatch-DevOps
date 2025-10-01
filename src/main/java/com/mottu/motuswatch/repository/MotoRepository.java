package com.mottu.motuswatch.repository;

import com.mottu.motuswatch.model.Moto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Page<Moto> findByArea(com.mottu.motuswatch.model.AreaPatio area, Pageable pageable);
    Page<Moto> findByPlacaContainingIgnoreCase(String placa, Pageable pageable);
    boolean existsByPlaca(String placa);
}
