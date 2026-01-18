package com.ecommerce.inventory.mapper;

import com.ecommerce.inventory.dto.InventoryRequest;
import com.ecommerce.inventory.dto.InventoryResponse;
import com.ecommerce.inventory.event.Inventory;

public class InventoryMapper {
    public InventoryResponse toDto(Inventory inventory){
        return new InventoryResponse(
                inventory.getSkuCode(),
                inventory.getQuantity()>0,
                inventory.getQuantity()
        );
    }

    public Inventory toEntity(InventoryRequest dto){
        Inventory inventory = new Inventory();
        inventory.setSkuCode(dto.getSkuCode());
        inventory.setQuantity(dto.getQuantity());
        return inventory;
    }
}
