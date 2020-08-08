package com.demo.spring.datajpa.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Component("fileUpload")
public interface FileService {
	 public String save(MultipartFile file);
}
