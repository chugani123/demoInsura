package com.demo.spring.datajpa.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.spring.datajpa.service.FileService;

@Service
public class FileServiceImpl implements FileService{
	private final Path root = Paths.get("C:\\");
	
	  public String save(MultipartFile file) {
	    try {
	      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
	    } catch (Exception e) {
	      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
	    }
	    return root.toString().concat("\\").concat(file.getOriginalFilename());
	  }
}
