package com.kevsz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevsz.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer>{

}
