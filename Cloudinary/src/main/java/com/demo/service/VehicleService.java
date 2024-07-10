package com.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.demo.entity.Vehicle;

public interface VehicleService {

	Vehicle saveProduct(Vehicle vehicle, MultipartFile file);

	Vehicle getProductById(Long id);

	void deleteProductById(Long id);

	Vehicle updateProduct(Long id, Vehicle productDetails, MultipartFile image);

	List<Vehicle> getAllData();
}
