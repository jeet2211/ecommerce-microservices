package com.ecommerce.inventory.exception;

public class InventoryNotFoundException extends RuntimeException{
    public InventoryNotFoundException(String skuCode){
         super("inventory not found for skucode"+skuCode);
    }
}
