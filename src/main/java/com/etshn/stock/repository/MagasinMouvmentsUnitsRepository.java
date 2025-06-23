package com.etshn.stock.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etshn.stock.entity.MagasinUnitsMouvments;

public interface MagasinMouvmentsUnitsRepository extends JpaRepository<MagasinUnitsMouvments, Long> {
	Page<MagasinUnitsMouvments> findByMagasinIdAndUnitIdOrderByDateCreationDesc(Long magasinId, Long unitId, Pageable pageable);
	//Page<MagasinMouvement> findByMagasinIdAndDateCreationBetweenOrderByDateCreationDesc(Long magasinId, Date startDate, Date andDate, Pageable pageable);
	Page<MagasinUnitsMouvments> findByTypeIdAndMagasinIdAndUnitNomContainingAndDateCreationBetweenOrderByDateCreationDesc(Long typeId, Long magasinId, String unit, Date startDate, Date endDate, Pageable pageable);
	Page<MagasinUnitsMouvments> findByMagasinIdAndTypeIdOrderByDateCreationDesc(Long magasinId, Long typeId, Pageable pageable);
    @Query("select sum(m.quantity) from MagasinUnitsMouvments m where m.magasin.id = ?1 and m.unit.id = ?2")
    List<Integer> getStockUnitInMagasin(Long magasinId, Long unitId);
}
