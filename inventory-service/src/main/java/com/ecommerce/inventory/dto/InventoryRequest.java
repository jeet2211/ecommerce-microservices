package com.ecommerce.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    @NotBlank(message = "sku code must not be blank")
    private String skuCode;
    //stock keeper unit code
    @Min(value = 0,message = "quantity must be positive")
    private Integer quantity;
}
