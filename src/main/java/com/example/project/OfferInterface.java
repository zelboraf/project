package com.example.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OfferInterface extends JpaRepository<Offer, Long> {

	Offer findOneByOfferId(String offerId);

	@Query(value = "SELECT FORMAT(AVG(area), 2) FROM offers", nativeQuery = true)
	double getAverageArea();

	@Query(value = "SELECT FORMAT(AVG(price), 2) FROM offers", nativeQuery = true)
	double getAveragePrice();

	@Query(value = "SELECT FORMAT(AVG(price_per_m2), 2) FROM offers", nativeQuery = true)
	double getAveragePricePerM2();

	@Query(value = "SELECT COUNT(*) FROM offers", nativeQuery = true)
	long countAll();

}
