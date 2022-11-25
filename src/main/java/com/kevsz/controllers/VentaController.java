package com.kevsz.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kevsz.model.Client;
import com.kevsz.model.Product;
import com.kevsz.model.Sale;
import com.kevsz.model.SaleDetail;
import com.kevsz.model.User;
import com.kevsz.repository.ClientRepository;
import com.kevsz.repository.ProductRepository;
import com.kevsz.repository.SaleDetailRepository;
import com.kevsz.repository.SaleRepository;
import com.kevsz.repository.UserRepository;

@Controller
@RequestMapping("/")
public class VentaController {
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	SaleRepository saleRepository;

	@Autowired
	SaleDetailRepository saleDetailRepository;
	
	@GetMapping({ "/sales" })
	public String index(Model model) {
		
		List<Sale> sales = saleRepository.findAll();
		model.addAttribute("sales",sales);
		return "/sales/index";
	}

	@GetMapping({ "/sales/show" })
	public String show(@RequestParam int id,Model model) {
		
		Sale sale = saleRepository.findById(id).get();
		List<SaleDetail> details = saleDetailRepository.findAll();
		model.addAttribute("sale",sale);
		model.addAttribute("details",details);
		return "/sales/show";
	}
	
	@GetMapping({ "/sales/create" })
	public String register(Model model) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    String date = dtf.format(LocalDateTime.now());

		List<Client> clients = clientRepository.findAll();
		List<Product> products = productRepository.findAll();
		model.addAttribute("clients",clients);
		model.addAttribute("products",products);
		model.addAttribute("date",date);
		return "/sales/create";
	}
	
	@PostMapping("/sales/store")
	public String store(Integer[] products,Integer[] quantities, Integer client) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    String fecha = dtf.format(LocalDateTime.now());

		Client clientFounded= clientRepository.findById(client).get();
		Sale sale = new Sale();
		sale.setDate(fecha);
		sale.setIgv(0.18);
		sale.setStatus("A");
		sale.setClient(clientFounded);

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User userFounded = userRepository.findByEmail(email).get();

		sale.setUser(userFounded);
		sale.setTotal(0.00);
		saleRepository.save(sale);

		Double total = 0.00;

		for (int i = 0; i < quantities.length; i++) {
			Product productFounded = productRepository.findById(products[i]).get();
			productFounded.setQuantity(productFounded.getQuantity()-quantities[i]);
			productRepository.save(productFounded);
			total += productFounded.getPrice() * quantities[i];
			SaleDetail saleDetail = new SaleDetail(quantities[i],productFounded.getPrice(),sale,productFounded);
			saleDetailRepository.save(saleDetail);
		}

		sale.setTotal(total);
		saleRepository.save(sale);
	
		return "redirect:/sales?success";
	}
}
