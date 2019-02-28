package com.example.project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeInterface extends JpaRepository<Type, Long> {

	Type getOneByName(String name);

}
