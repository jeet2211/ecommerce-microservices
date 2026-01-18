package com.ecommerce.inventory.service;

import com.ecommerce.inventory.dto.InventoryRequest;
import com.ecommerce.inventory.dto.InventoryResponse;

public interface InventoryService {
    InventoryResponse checkQuantity(String skuCode);
    void addInventory(InventoryRequest inventoryRequest);
}
