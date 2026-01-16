package com.ecommerce.product_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    @NotNull(message = "product name is required")
    private String name;
    private String description;
    @Positive(message = "must be positive")
    private double price;
    private double discountPrice;
    @Min(value = 0, message = "quanity must be 0 or more")
    private int quantity;
    private String brand;
    private String imageUrl;

    @NotNull(message = "category id is required")
    private Long categoryId;

}
