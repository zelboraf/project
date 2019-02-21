package com.example.project.offer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentialTypeInterface extends JpaRepository<ResidentialType, Long> {

	ResidentialType getOneByName(String name);

}
