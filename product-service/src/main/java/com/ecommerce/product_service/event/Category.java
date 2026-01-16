package com.ecommerce.product_service.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@Data
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(length = 200)
    private String description;
    private Long parentId;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> product;
}
