package com.ecommerce.inventory.service;

import com.ecommerce.inventory.dto.InventoryRequest;
import com.ecommerce.inventory.dto.InventoryResponse;
import com.ecommerce.inventory.event.Inventory;
import com.ecommerce.inventory.exception.InventoryNotFoundException;
import com.ecommerce.inventory.mapper.InventoryMapper;
import com.ecommerce.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    @Override
    public InventoryResponse checkQuantity(String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new InventoryNotFoundException(skuCode));
        return inventoryMapper.toDto(inventory);
    }

    @Override
    public void addInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryMapper.toEntity(inventoryRequest);
        inventoryRepository.save(inventory);

    }
}
