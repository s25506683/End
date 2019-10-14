package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
  private List<Product> productList = new ArrayList<>();
  private int count=0;
  public ProductController(){
    productList.add(new Product(1, "iPhone 11", 20000, "智慧型手機", 20, 10));
    productList.add(new Product(2, "iPhone 11 Pro", 25000, "智慧型手機", 10, 5));
    productList.add(new Product(3, "iPhone X", 18000, "智慧型手機", 3, 5));
    count = 3;
  }

 @GetMapping("/product")
    public List<Product> get() {
        return productList;
    }   
}

