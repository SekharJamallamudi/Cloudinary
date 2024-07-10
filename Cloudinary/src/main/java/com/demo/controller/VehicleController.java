package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.entity.Vehicle;
import com.demo.service.VehicleService;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
	@Autowired	
    private  VehicleService vehicleService;

	 @PostMapping
	    public ResponseEntity<Vehicle> createProduct(@RequestParam("name") String name,
	                                                 @RequestParam("description") String description,
	                                                 @RequestParam("image") MultipartFile image) {
		 Vehicle vehicle = new Vehicle();
		 vehicle.setName(name);
		 vehicle.setDescription(description);
		 Vehicle savedProduct = vehicleService.saveProduct(vehicle, image);
	        return ResponseEntity.ok(savedProduct);
	    }
	 
	 @GetMapping("/{id}")
	    public ResponseEntity<Vehicle> getProductById(@PathVariable Long id) {
		 Vehicle vehicle = vehicleService.getProductById(id);
	        return ResponseEntity.ok(vehicle);
	    }
	 @GetMapping("/all")
	 public ResponseEntity<List<Vehicle>> getAllData()
	 {
		 List<Vehicle> vehicle = vehicleService.getAllData();
	        return ResponseEntity.ok(vehicle);
	 }
	 
	  @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
	        vehicleService.deleteProductById(id);
	        return ResponseEntity.ok().build();
	    }
	  
	  @PutMapping("/{id}")
	    public ResponseEntity<Vehicle> updateProduct(@PathVariable Long id, 
	                                                 @RequestParam("name") String name,
	                                                 @RequestParam("description") String description,
	                                                 @RequestParam("image") MultipartFile image) {
		  Vehicle productDetails = new Vehicle();
	        productDetails.setName(name);
	        productDetails.setDescription(description);
	        // Set other product details as needed

	        Vehicle updatedProduct = vehicleService.updateProduct(id, productDetails, image);
	        return ResponseEntity.ok(updatedProduct);
	    }
}