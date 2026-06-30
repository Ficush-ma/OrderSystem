package com.orderSystem.controller;

import com.orderSystem.entity.Product;
import com.orderSystem.entity.Result;
import com.orderSystem.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@Slf4j
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping("all_products")
    @Operation(summary = "商品列表")
    public Result<List<String>> productsList(){
        return Result.success(productService.searchAllName());
    }
    @GetMapping("{id}")
    @Operation(summary = "商品详细信息")
    public Result<Product> productSearch(@PathVariable int id){
        return Result.success(productService.searchById(id));
    }
}
