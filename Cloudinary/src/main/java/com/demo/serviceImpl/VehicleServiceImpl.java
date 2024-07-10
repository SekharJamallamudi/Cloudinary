package com.demo.serviceImpl;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.demo.entity.Vehicle;
import com.demo.repository.VehicleRepository;
import com.demo.service.VehicleService;
@Service
public class VehicleServiceImpl implements VehicleService {
	  @Autowired
	    private VehicleRepository vehicleRepository;

	  @Autowired
	    private Cloudinary cloudinary;

	    @Override
	    public Vehicle saveProduct(Vehicle vehicle, MultipartFile file) {
	        try {
	            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
	            vehicle.setImageUrl(uploadResult.get("url").toString());
	        } catch (IOException e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }
	        return vehicleRepository.save(vehicle);
	    }

		@Override
		public Vehicle getProductById(Long id) {
			 return vehicleRepository.findById(id)
		                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
		    }

		@Override
	    public void deleteProductById(Long id) {
	        Vehicle vehicle = getProductById(id); // Ensures the product exists
	        vehicleRepository.deleteById(id);

	        // Delete the image from Cloudinary
	        String publicId = extractPublicIdFromUrl(vehicle.getImageUrl());
	        try {
	            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
	        } catch (IOException e) {
	            e.printStackTrace(); // Consider proper exception handling/logging
	        }
	    }

		private String extractPublicIdFromUrl(String imageUrl) {
	        String[] parts = imageUrl.split("/");
	        String lastPart = parts[parts.length - 1];
	        // Extract the part before the file extension
	        String filename = lastPart.split("\\.")[0];
	        // If versioning is used, the public ID is the part after the version number
	        String publicId = filename.contains("v") ? filename.substring(filename.lastIndexOf("v") + 1) : filename;
	        return publicId;
	    }
		
		 @Override
		    public Vehicle updateProduct(Long id, Vehicle productDetails, MultipartFile image) {
			 Vehicle product = vehicleRepository.findById(id)
		                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

		        if (!image.isEmpty()) {
		            // Optionally, delete the old image from Cloudinary
		            String publicId = extractPublicIdFromUrl(product.getImageUrl());
		            try {
		                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
		            } catch (IOException e) {
		                e.printStackTrace(); // Handle appropriately
		            }

		            // Upload the new image to Cloudinary
		            try {
		                Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
		                String newImageUrl = (String) uploadResult.get("url");
		                product.setImageUrl(newImageUrl); // Update the product's image URL with the new one
		            } catch (IOException e) {
		                e.printStackTrace(); // Handle appropriately
		            }
		        }

		        // Update product details
		        product.setName(productDetails.getName());
		        product.setDescription(productDetails.getDescription());
		        // Update other fields as needed

		        return vehicleRepository.save(product);
		    }

		@Override
		public List<Vehicle> getAllData() {
			// TODO Auto-generated method stub
			return vehicleRepository.findAll();
		}
}