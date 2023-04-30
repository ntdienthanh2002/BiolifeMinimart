/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbean;

import com.entity.Category;
import com.entity.Product;
import com.entity.Supplier;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author OS
 */
@Local
public interface ProductFacadeLocal {

    void create(Product product);

    void edit(Product product);

    void remove(Product product);

    Product find(Object id);

    List<Product> findAll();

    List<Product> findRange(int[] range);

    int count();
    
    public List<Product> showSortProduct(int pageNumber, String sort);
    
    public List<Product> showPagination(int pageNumber);

    public List<Product> showCategoryPagination(List<Category> listCategory, int pageNumber, List<Supplier> listSupplier, double price1, double price2, String keyword);

    public List<Product> showSortCategoryProduct(List<Category> listCategory, int pageNumber, String sort, List<Supplier> listSupplier, double price1, double price2, String keyword);

    public List<Product> showAllProducts(List<Category> listCategory, int pageNumber, List<Supplier> listSupplier, double price1, double price2, String keyword);
    
    public List<Product> getHighestProduct();
    
    List<Product> showProductByCatgory(Category category);

    List<Product> searchProduct(List<Category> listCategory, List<Supplier> listSupplier, String keyword);
    
    public Product findBestSeller(Category category);
    
    boolean hideBtnStatusByCategory(int idC, int idS);
    
    public boolean checkOrderDetailsByProduct(Product id);
        
    void deleteProductImageByProduct(Product id);
    
    void deletePromotionDetailsByProduct(Product id);
    
    void deleteCartByProduct(Product id);
    
    public boolean checkProductExist(String name);
    
    public boolean checkUpdateProductExist(String name, int id);
    
    List<String> findProductName(Category id);
    
    List<Number> countProductQuantity(Category id);
}
