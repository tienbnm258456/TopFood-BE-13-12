package com.demo.shop.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.shop.entity.Product;
import com.demo.shop.repository.ProductRepository;
import com.demo.shop.util.ExcelHelper;

@Service
public class ExcelService {
  @Autowired
  ProductRepository repository;
  @Autowired
  ExcelHelper excelHelper;

  public void save(MultipartFile file) {
    try {
      List<Product> tutorials = excelHelper.excelToTutorials(file.getInputStream());
      repository.saveAll(tutorials);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<Product> tutorials = repository.findAll();

    ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(tutorials);
    return in;
  }

  public List<Product> getAllTutorials() {
    return repository.findAll();
  }
}
