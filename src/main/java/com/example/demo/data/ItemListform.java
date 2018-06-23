package com.example.demo.data;

import java.util.List;
import javax.validation.Valid;

public class ItemListform {

  @Valid
  private List<Item> itemList;

  public List<Item> getItemList() {
    return itemList;
  }

  public void setItemList(List<Item> itemList) {
    this.itemList = itemList;
  }

}
