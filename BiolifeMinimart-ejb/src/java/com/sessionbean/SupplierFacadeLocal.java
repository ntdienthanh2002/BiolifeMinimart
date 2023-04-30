/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbean;

import com.entity.Supplier;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface SupplierFacadeLocal {

    void create(Supplier supplier);

    void edit(Supplier supplier);

    void remove(Supplier supplier);

    Supplier find(Object id);

    List<Supplier> findAll();

    List<Supplier> findRange(int[] range);

    int count();
    
    public List<Supplier> findEnableSuppliers();
    
    void updateProductStatusBySupplier(Supplier id, int status);

    boolean checkProductBySupplier(Supplier id);
    
    boolean checkSupplierExist(String name);
    
    boolean checkUpdateSupplierExist(String name, int id);
    
}
