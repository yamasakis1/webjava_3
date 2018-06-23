package com.example.demo.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cartitems")
public class CartItem {
    @Id
    @Column(name="item_id")
    public String itemId;
    @Column(name="item_name")
    public String itemName;
    @Column(name="price")
    public String price;
    @Column(name="number")
    public String number;

    public String getItemId() {
      return itemId;
    }

    public void setItemId(String itemId) {
      this.itemId = itemId;
    }

    public String getItemName() {
      return itemName;
    }

    public void setItemName(String itemName) {
      this.itemName = itemName;
    }

    public String getPrice() {
      return price;
    }

    public void setPrice(String price) {
      this.price = price;
    }

    public String getNumber() {
      return number;
    }

    public void setNumber(String number) {
      this.number = number;
    }
}
