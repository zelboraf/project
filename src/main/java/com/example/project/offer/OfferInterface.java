package com.example.project.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OfferInterface extends JpaRepository<Offer, Long> {

	Offer findOneByOfferId(String offerId);

	@Query(value = "SELECT FORMAT(AVG(area), 2) FROM offers", nativeQuery = true)
	double getAvgArea();

	@Query(value = "SELECT FORMAT(AVG(price), 2) FROM offers", nativeQuery = true)
	double getAvgPrice();

	@Query(value = "SELECT FORMAT(AVG(price_per_m2), 2) FROM offers", nativeQuery = true)
	double getAvgPricePerM2();

	@Query(value = "SELECT COUNT(*) FROM offers", nativeQuery = true)
	long countAll();

}
