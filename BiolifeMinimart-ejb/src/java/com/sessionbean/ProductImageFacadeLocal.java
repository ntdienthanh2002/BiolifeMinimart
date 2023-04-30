/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbean;

import com.entity.Product;
import com.entity.ProductImage;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface ProductImageFacadeLocal {

    void create(ProductImage productImage);

    void edit(ProductImage productImage);

    void remove(ProductImage productImage);

    ProductImage find(Object id);

    List<ProductImage> findAll();

    List<ProductImage> findRange(int[] range);

    int count();
    
    public List<ProductImage> findProductImageByProductID(Product id);
    
}
