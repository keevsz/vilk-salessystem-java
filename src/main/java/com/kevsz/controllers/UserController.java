package com.kevsz.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kevsz.model.Rol;
import com.kevsz.model.User;
import com.kevsz.repository.RolRepository;
import com.kevsz.repository.UserRepository;

@Controller
@RequestMapping("/")

public class UserController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	RolRepository rolRepository;

	@Autowired
	PasswordEncoder encoder;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping({ "/users" })
	public String index(Model model) {
		List<User> list = userRepository.findAll();
		model.addAttribute("users", list);
		return "/users/index";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping({ "/users/create" })
	public String register() {
		return "/users/create";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/users/store")
	public String store(String name, String lastname, String email, Integer dni, String birthday, String gender,
			String location, Integer phonenumber, String[] rols) {

		User user = new User(email, encoder.encode("password"), name, lastname, birthday, gender, phonenumber, dni,
				location);

		Set<Rol> roles = new HashSet<>();

		for (String rolIterator : rols) {
			Rol rol = rolRepository.findByDescription(rolIterator).get();
			roles.add(rol);
		}
		user.setRoles(roles);
		userRepository.save(user);
		return "redirect:/users?success";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/users/delete")
	public String delete(@RequestParam int id) {
		System.out.println(id);
		userRepository.deleteById(id);
		return "redirect:/users";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/users/edit")
	public String edit(@RequestParam int id, Model model) {
		User user = userRepository.findById(id).get();

		model.addAttribute("user", user);
		model.addAttribute("rolRepository", rolRepository);
		return "/users/edit";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/users/update")
	public String update(@RequestParam int id, String name, String lastname, String email, Integer dni, String birthday,
			String gender, String location, Integer phonenumber, String[] rols, String password) {

		User user = userRepository.findById(id).get();

		user.setName(name);
		user.setLastname(lastname);
		user.setEmail(email);
		user.setDni(dni);
		user.setBirthday(birthday);
		user.setLocation(location);
		user.setPhonenumber(phonenumber);
		user.setGender(gender);

		if (password != null)
			user.setPassword(encoder.encode(password));

		Set<Rol> roles = new HashSet<>();

		for (String rolIterator : rols) {
			Rol rol = rolRepository.findByDescription(rolIterator).get();
			roles.add(rol);
		}

		user.setRoles(roles);
		userRepository.save(user);
		return "redirect:/users?updated";
	}

	@GetMapping("/profile")
	public String profile(Model model) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(email).get();

		model.addAttribute("user", user);
		model.addAttribute("rolRepository", rolRepository);
		return "/users/edit";
	}
}
