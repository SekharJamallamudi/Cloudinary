package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
    
      @Bean
      public Cloudinary cloudinary() {
          return new Cloudinary(ObjectUtils.asMap(
                  "cloud_name", "dzldywbrh",
                  "api_key", "534571325557693",
                  "api_secret", "pt5OASffwPlSX45VZJdZ8I7b01s",
                  "secure", true));
      }
}
