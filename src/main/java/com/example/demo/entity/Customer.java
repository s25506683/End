package com.example.demo.entity;

public class Customer {
  private Long id;

  private String des;
  private int price;
  private String category;
  private int inventory;
  private int safetyStock;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

public int getPrice() {
  return price;
}
public void setPrice(int price) {
 this.price = price;
}

public String getDes() {
  return des;
 }
 public void setDes(String des) {
  this.des = des;
 }

 public String getCategory() {
  return category;
 }
 public void setCategory(String category) {
  this.category = category;
 }

 public int getInventory() {
  return inventory;
 }
 public void setInventory(int inventory) {
  this.inventory = inventory;
 }
 
 public int getSafetyStock() {
  return safetyStock;
 }

 public void setSafetyStock(int safetyStock) {
  this.safetyStock = safetyStock;
 }
}


