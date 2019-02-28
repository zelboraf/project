package com.example.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriceInterface extends JpaRepository<Price, Long> {

	@Query(value = "SELECT * FROM prices p " +
			"JOIN offers_prices op ON p.id = op.price_id " +
			"JOIN offers o ON op.offer_id = o.id " +
			"WHERE o.id = ?1 " +
			"ORDER BY p.local_date_time ",
			nativeQuery = true)
	List<Price> getAllByOfferId(long id);

}
