package com.example.project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CityInterface extends JpaRepository<City, Long> {

	City getOneByName(String name);

}
