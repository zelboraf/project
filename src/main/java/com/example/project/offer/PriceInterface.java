package com.example.project.offer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceInterface extends JpaRepository<Price, Long> {

	Price getCurrentPrice();
}
