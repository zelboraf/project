package com.example.project.offer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceInterface extends JpaRepository<Price, Long> {

	List<Price> getAllByOfferId(long id);
}
