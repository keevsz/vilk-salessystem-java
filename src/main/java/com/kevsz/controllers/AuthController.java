package com.kevsz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kevsz.model.User;
import com.kevsz.repository.RolRepository;
import com.kevsz.repository.UserRepository;

@Controller
@RequestMapping("/")
public class AuthController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RolRepository rolRepository;

	@GetMapping({ "/", "/login" })
	public String login() {

		return "login";
	}

	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@GetMapping("/organization")
	public String organization(Model model) {
		List<User> list = userRepository.findAll();

		model.addAttribute("count", list.size());
		model.addAttribute("rolRepository", rolRepository);
		model.addAttribute("users", list);
		return "others/organization";
	}

}