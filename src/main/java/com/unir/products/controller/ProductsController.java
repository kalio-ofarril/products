package com.unir.products.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;
import com.unir.products.service.ProductServiceImpl;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ProductsController {

	@Autowired
	private final ProductServiceImpl service;

	@CrossOrigin
	@GetMapping("/")
	public String welcome() {

		return "Welcome to Products";
	}

	@CrossOrigin
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {

		List<Product> products = service.getProducts();

		if (products != null) {
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.ok(Collections.emptyList());
		}
	}

	@CrossOrigin
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable String productId) {
		Product product = service.getProduct(productId);

		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
		Boolean removed = service.removeProduct(productId);

		if (Boolean.TRUE.equals(removed)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin(origins = "https://react-tienda-online-ten.vercel.app")
	@PostMapping("/products")
	public ResponseEntity<Product> getProduct(@RequestBody CreateProductRequest request) {

		Product createdProduct = service.createProduct(request);

		if (createdProduct != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}