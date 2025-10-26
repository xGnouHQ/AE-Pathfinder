package com.pathfinder.repository;

import com.pathfinder.model.NachwuchskraftAnhang;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NachwuchskraftAnhangRepository extends JpaRepository<NachwuchskraftAnhang, Long> {
    List<NachwuchskraftAnhang> findByNachwuchskraftId(Long nwkId);
}
