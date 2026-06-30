package com.orderSystem.mapper;

import com.orderSystem.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    /*
        获取所有商品名
     */
    List<String> getAllProductName();
    /*
        id搜索product信息
     */
    Product searchProduct(int id);
    /*
        设置产品库存
    */
    void setStock(int productId,int num);

}
