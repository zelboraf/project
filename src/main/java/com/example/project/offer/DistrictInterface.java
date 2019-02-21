package com.example.project.offer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictInterface extends JpaRepository<District, Long> {

	District getOneByName(String name);

}
