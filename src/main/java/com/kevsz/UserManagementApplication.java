package com.kevsz;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kevsz.model.Rol;
import com.kevsz.model.User;
import com.kevsz.repository.RolRepository;
import com.kevsz.repository.UserRepository;

@SpringBootApplication
public class UserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@Bean
	CommandLineRunner commandLineRunner(RolRepository rolRepository, UserRepository userRepository,
			PasswordEncoder encoder) {
		return args -> {
			try {
				userRepository.findByEmail("admin@gmail.com").get();
				System.out.println("Encontrado: pausando creacion de datos");
			} catch (Exception e) {
				System.out.println("No Encontrado: creando...");		
				// Población tabla roles		
				Rol role_admin = new Rol("ROLE_ADMIN");
				rolRepository.save(role_admin);
				
				Rol role_user = new Rol("ROLE_USER");
				rolRepository.save(role_user);
				
				// Poblacion usuarios
				Rol rolAdmin = rolRepository.findByDescription("ROLE_ADMIN").get();
				Rol rolUser = rolRepository.findByDescription("ROLE_USER").get();

				User userAdmin = new User("admin@gmail.com", encoder.encode("password"),"Kevin Yair","Vilca Quispe", "22-09-2001","H",949525116,74207469,"Chepén, La Libertad, Perú");
				Set<Rol> roles = new HashSet<>();
				roles.add(rolAdmin);			
				userAdmin.setRoles(roles);
				userRepository.save(userAdmin);

				User userUser = new User("user@gmail.com", encoder.encode("password"),"Jose Wladimir","Esquén Quiróz", "12-02-2001","H",938488291,78278398,"Pueblo nuevo, Chepén, La Libertad, Perú");
				Set<Rol> roles1 = new HashSet<>();
				roles1.add(rolUser);			
				userUser.setRoles(roles1);
				userRepository.save(userUser);

				User userUser2 = new User("user2@gmail.com", encoder.encode("password"),"Alex Marcelino","Silva Eneque", "21-09-2001","H",989827879,72877387,"Pueblo nuevo, Chepén, La Libertad, Perú");
				Set<Rol> roles2 = new HashSet<>();
				roles2.add(rolUser);			
				userUser2.setRoles(roles2);
				userRepository.save(userUser2);

				User userUser3 = new User("user3@gmail.com", encoder.encode("password"),"Juan David","Morales Paredes", "14-06-2001","H",918918372,86745801,"Guadalupe, Pacasmayo, Perú");
				Set<Rol> roles3 = new HashSet<>();
				roles3.add(rolUser);			
				userUser3.setRoles(roles3);
				userRepository.save(userUser3);
				
				System.out.println(e);
			}
		};
	}

}
