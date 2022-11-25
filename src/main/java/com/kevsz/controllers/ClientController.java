package com.kevsz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kevsz.model.Client;
import com.kevsz.repository.ClientRepository;

@Controller
@RequestMapping("/")
public class ClientController {

  @Autowired
  ClientRepository clientRepository;

  @GetMapping("/clients/create")
	public String create() {
	
		return "clients/create";
	}

  @PostMapping("/clients/store")
	public String store(String name, Integer dni) {
		Client client = new Client(name, dni);
		clientRepository.save(client);
		return "redirect:/sales/create";
	}
}
