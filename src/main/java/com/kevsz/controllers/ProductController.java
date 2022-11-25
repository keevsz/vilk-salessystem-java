package com.kevsz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kevsz.model.Category;
import com.kevsz.model.Product;
import com.kevsz.repository.CategoryRepository;
import com.kevsz.repository.ProductRepository;

@Controller
@RequestMapping("/")
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping({ "/products" })
	public String index(Model model) {
		List<Product> list = productRepository.findAll();
		model.addAttribute("products", list);
		return "/products/index";
	}

	@GetMapping({ "/products/create" })
	public String create(Model model) {
		List<Category> list = categoryRepository.findAll();
		model.addAttribute("categories", list);
		return "/products/create";
	}

	@PostMapping("/products/store")
	public String store(String description, int quantity, double price, String image, int idCategory) {

		Category c = categoryRepository.findById(idCategory).get();

		Product product = new Product(description, quantity, price, image, c);
		productRepository.save(product);
		return "redirect:/products?success";
	}

	@GetMapping("/products/delete")
	public String delete(@RequestParam int id) {
		productRepository.deleteById(id);
		return "redirect:/products";
	}

	@GetMapping("/products/edit")
	public String edit(@RequestParam int id, Model model) {
		Product product = productRepository.findById(id).get();

		List<Category> list = categoryRepository.findAll();
		model.addAttribute("categories", list);
		model.addAttribute("product", product);
		return "/products/edit";
	}

	@PostMapping("/products/update")
	public String update(@RequestParam int id, String description, int quantity, double price, String image,
			int idCategory) {

		Product product = productRepository.findById(id).get();

		Category c = categoryRepository.findById(idCategory).get();
		product.setDescription(description);
		product.setQuantity(quantity);
		product.setPrice(price);
		product.setCategory(c);

		productRepository.save(product);
		return "redirect:/products?success";
	}
	
	@PostMapping("/category/store")
	public String categoryedit(String description) {
		
		Category category = new Category(description);
		
		categoryRepository.save(category);
		return "redirect:/products/create";
	}
}
