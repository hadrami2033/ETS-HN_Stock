package com.etshn.stock.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etshn.stock.entity.MagasinMouvement;

public interface MagasinMouvmentsRepository extends JpaRepository<MagasinMouvement, Long>  {
	Page<MagasinMouvement> findByMagasinIdAndProductIdOrderByDateCreationDesc(Long magasinId, Long productId, Pageable pageable);
	//Page<MagasinMouvement> findByMagasinIdAndDateCreationBetweenOrderByDateCreationDesc(Long magasinId, Date startDate, Date andDate, Pageable pageable);
	Page<MagasinMouvement> findByTypeIdAndMagasinIdAndProductNomContainingAndDateCreationBetweenOrderByDateCreationDesc(Long typeId, Long magasinId, String product, Date startDate, Date andDate, Pageable pageable);
	Page<MagasinMouvement> findByMagasinIdAndTypeIdOrderByDateCreationDesc(Long magasinId, Long typeId, Pageable pageable);
    @Query("select sum(m.quantity) from MagasinMouvement m where m.magasin.id = ?1 and m.product.id = ?2")
    List<Integer> getStockProductInMagasin(Long magasinId, Long productId);
}
