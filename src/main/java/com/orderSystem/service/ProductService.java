package com.orderSystem.service;

import com.orderSystem.entity.Product;
import com.orderSystem.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    private final ProductMapper productMapper;
    public ProductService(ProductMapper productMapper){
        this.productMapper = productMapper;
    }
    public List<String> searchAllName(){
        log.info("搜索所有产品名");
        return productMapper.getAllProductName();
    }
    public Product searchById(int id){
        log.info("ID搜索产品，ID：{}",id);
        return productMapper.searchProduct(id);
    }
}
