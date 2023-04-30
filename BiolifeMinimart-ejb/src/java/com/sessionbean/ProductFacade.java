/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Category;
import com.entity.Product;
import com.entity.Supplier;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author OS
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> implements ProductFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }

    @Override
    public List<Product> showSortProduct(int pageNumber, String sort) {
        Query query = em.createQuery("SELECT p FROM Product p ORDER BY p.productUnitPrice " + sort);
        int pageSize = 7;
        query.setMaxResults(pageSize);
        query.setFirstResult((pageNumber - 1) * pageSize);
        return query.getResultList();
    }

    @Override
    public List<Product> showPagination(int pageNumber) {
        Query query = em.createQuery("SELECT p FROM Product p");
        int pageSize = 7;
        query.setMaxResults(pageSize);
        query.setFirstResult((pageNumber - 1) * pageSize);
        return query.getResultList();
    }

    @Override
    public List<Product> showCategoryPagination(List<Category> listCategory, int pageNumber, List<Supplier> listSupplier, double price1, double price2, String keyword) {
        List<Product> listProduct = new ArrayList<>();
        if (keyword == null) {
            keyword = "";
        }
        Query query;
        if (keyword.equals("")) {
            query = em.createQuery("SELECT p FROM Product p WHERE p.productStatus = :productStatus AND p.categoryID IN :listCategory AND p.supplierID IN :listSupplier AND p.productUnitPrice >= :price1 AND p.productUnitPrice <= :price2 AND p.productName LIKE :keyword");
            query.setParameter("listCategory", listCategory);
            query.setParameter("listSupplier", listSupplier);
            query.setParameter("productStatus", 1);
            query.setParameter("price1", price1);
            query.setParameter("price2", price2);
            query.setParameter("keyword", "%" + keyword + "%");
            int pageSize = 9;
            query.setMaxResults(pageSize);
            query.setFirstResult((pageNumber - 1) * pageSize);
            listProduct = query.getResultList();
        } else {
            List<Object[]> list = new ArrayList<>();
            String[] listKeyword = keyword.trim().split(" ");
            String str = "";
            for (String listKeyword1 : listKeyword) {
                str += "w.value LIKE \'%" + listKeyword1 + "%\' OR ";
            }
            String sql = str.substring(0, str.length() - 4);

            //list category
            String cateSql = "";
            if (listCategory.size() == 1) {
                cateSql = "(" + listCategory.get(0).getCategoryID() + ")";
            } else {
                String cateStr = "(";
                for (int i = 0; i < listCategory.size(); i++) {
                    cateStr += listCategory.get(i).getCategoryID() + ", ";
                }
                cateSql = cateStr.substring(0, cateStr.length() - 2);
                cateSql += ")";
            }

            //list supplier
            String supSql = "";
            if (listSupplier.size() == 1) {
                supSql = "(" + listSupplier.get(0).getSupplierID() + ")";
            } else {
                String supStr = "(";
                for (int i = 0; i < listSupplier.size(); i++) {
                    supStr += listSupplier.get(i).getSupplierID() + ", ";
                }
                supSql = supStr.substring(0, supStr.length() - 2);
                supSql += ")";
            }

            String q = "select a.* from (select p.ProductID, count(*) as result_count from Product p cross apply string_split(ProductName, ' ') w where p.productStatus = ?1 AND p.categoryID IN " + cateSql + " AND p.supplierID IN " + supSql + " AND p.productUnitPrice >= ?2 AND p.productUnitPrice <= ?3 AND " + sql + " group by ProductID) c inner join Product a on a.ProductID = c.ProductID order by c.result_count desc";
            query = em.createNativeQuery(q);
            query.setParameter(1, 1);
            query.setParameter(2, price1);
            query.setParameter(3, price2);
            System.out.println(query.toString());
            int pageSize = 9;
            query.setMaxResults(pageSize);
            query.setFirstResult((pageNumber - 1) * pageSize);
            list = query.getResultList();

            for (int i = 0; i < list.size(); i++) {
                Object[] obj = list.get(i);
                Product p = find(obj[0]);
                listProduct.add(p);
            }

            System.out.println(cateSql);
            System.out.println(supSql);
        }
        return listProduct;
    }
    
    @Override
    public List<Product> showSortCategoryProduct(List<Category> listCategory, int pageNumber, String sort, List<Supplier> listSupplier, double price1, double price2, String keyword) {
        List<Product> listProduct = new ArrayList<>();
        if (keyword == null) {
            keyword = "";
        }
        Query query;
        if (keyword.equals("")) {
            System.out.println("Hi");
            query = em.createQuery("SELECT p FROM Product p WHERE p.productStatus = :productStatus AND p.categoryID IN :listCategory AND p.supplierID IN :listSupplier AND p.productUnitPrice >= :price1 AND p.productUnitPrice <= :price2 AND p.productName LIKE :keyword ORDER BY p.productUnitPrice " + sort);
            query.setParameter("listCategory", listCategory);
            query.setParameter("listSupplier", listSupplier);
            query.setParameter("productStatus", 1);
            query.setParameter("price1", price1);
            query.setParameter("price2", price2);
            query.setParameter("keyword", "%" + keyword + "%");
            int pageSize = 9;
            query.setMaxResults(pageSize);
            query.setFirstResult((pageNumber - 1) * pageSize);
            listProduct = query.getResultList();
        } else {
            List<Object[]> list = new ArrayList<>();
            String[] listKeyword = keyword.trim().split(" ");
            String str = "";
            for (String listKeyword1 : listKeyword) {
                str += "w.value LIKE \'%" + listKeyword1 + "%\' OR ";
            }
            String sql = str.substring(0, str.length() - 4);

            //list category
            String cateSql = "";
            if (listCategory.size() == 1) {
                cateSql = "(" + listCategory.get(0).getCategoryID() + ")";
            } else {
                String cateStr = "(";
                for (int i = 0; i < listCategory.size(); i++) {
                    cateStr += listCategory.get(i).getCategoryID() + ", ";
                }
                cateSql = cateStr.substring(0, cateStr.length() - 2);
                cateSql += ")";
            }

            //list supplier
            String supSql = "";
            if (listSupplier.size() == 1) {
                supSql = "(" + listSupplier.get(0).getSupplierID() + ")";
            } else {
                String supStr = "(";
                for (int i = 0; i < listSupplier.size(); i++) {
                    supStr += listSupplier.get(i).getSupplierID() + ", ";
                }
                supSql = supStr.substring(0, supStr.length() - 2);
                supSql += ")";
            }

            String q = "select a.* from (select p.ProductID, count(*) as result_count from Product p cross apply string_split(ProductName, ' ') w where p.productStatus = ?1 AND p.categoryID IN " + cateSql + " AND p.supplierID IN " + supSql + " AND p.productUnitPrice >= ?2 AND p.productUnitPrice <= ?3 AND " + sql + " group by ProductID) c inner join Product a on a.ProductID = c.ProductID order by a.productUnitPrice " + sort;
            query = em.createNativeQuery(q);
            query.setParameter(1, 1);
            query.setParameter(2, price1);
            query.setParameter(3, price2);
            int pageSize = 9;
            query.setMaxResults(pageSize);
            query.setFirstResult((pageNumber - 1) * pageSize);
            list = query.getResultList();

            for (int i = 0; i < list.size(); i++) {
                Object[] obj = list.get(i);
                Product p = find(obj[0]);
                listProduct.add(p);
            }
        }

        return listProduct;
    }
    
    @Override
    public List<Product> showAllProducts(List<Category> listCategory, int pageNumber, List<Supplier> listSupplier, double price1, double price2, String keyword) {
        List<Product> listProduct = new ArrayList<>();
        if (keyword == null) {
            keyword = "";
        }
        Query query;
        if (keyword.equals("")) {
            query = em.createQuery("SELECT p FROM Product p WHERE p.productStatus = :productStatus AND p.categoryID IN :listCategory AND p.supplierID IN :listSupplier AND p.productUnitPrice >= :price1 AND p.productUnitPrice <= :price2 AND p.productName LIKE :keyword");
            query.setParameter("listCategory", listCategory);
            query.setParameter("listSupplier", listSupplier);
            query.setParameter("productStatus", 1);
            query.setParameter("price1", price1);
            query.setParameter("price2", price2);
            query.setParameter("keyword", "%" + keyword + "%");
            listProduct = query.getResultList();
        } else {
            List<Object[]> list = new ArrayList<>();
            String[] listKeyword = keyword.trim().split(" ");
            String str = "";
            for (String listKeyword1 : listKeyword) {
                str += "w.value LIKE \'%" + listKeyword1 + "%\' OR ";
            }
            String sql = str.substring(0, str.length() - 4);

            //list category
            String cateSql = "";
            if (listCategory.size() == 1) {
                cateSql = "(" + listCategory.get(0).getCategoryID() + ")";
            } else {
                String cateStr = "(";
                for (int i = 0; i < listCategory.size(); i++) {
                    cateStr += listCategory.get(i).getCategoryID() + ", ";
                }
                cateSql = cateStr.substring(0, cateStr.length() - 2);
                cateSql += ")";
            }

            //list supplier
            String supSql = "";
            if (listSupplier.size() == 1) {
                supSql = "(" + listSupplier.get(0).getSupplierID() + ")";
            } else {
                String supStr = "(";
                for (int i = 0; i < listSupplier.size(); i++) {
                    supStr += listSupplier.get(i).getSupplierID() + ", ";
                }
                supSql = supStr.substring(0, supStr.length() - 2);
                supSql += ")";
            }

            String q = "select a.* from (select p.ProductID, count(*) as result_count from Product p cross apply string_split(ProductName, ' ') w where p.productStatus = ?1 AND p.categoryID IN " + cateSql + " AND p.supplierID IN " + supSql + " AND p.productUnitPrice >= ?2 AND p.productUnitPrice <= ?3 AND " + sql + " group by ProductID) c inner join Product a on a.ProductID = c.ProductID";
            query = em.createNativeQuery(q);
            query.setParameter(1, 1);
            query.setParameter(2, price1);
            query.setParameter(3, price2);

            list = query.getResultList();

            for (int i = 0; i < list.size(); i++) {
                Object[] obj = list.get(i);
                Product p = find(obj[0]);
                listProduct.add(p);
            }
        }

        return listProduct;
    }


    @Override
    public List<Product> getHighestProduct() {
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.productStatus = :productStatus ORDER BY p.productUnitPrice DESC");
        query.setParameter("productStatus", 1);
        return query.setMaxResults(1).getResultList();
    }

    @Override
    public List<Product> showProductByCatgory(Category category) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.categoryID = :categoryID AND p.productStatus = :productStatus");
        query.setParameter("categoryID", category);
        query.setParameter("productStatus", 1);
        return query.getResultList();
    }

    @Override
    public List<Product> searchProduct(List<Category> listCategory, List<Supplier> listSupplier, String keyword) {
        List<Product> listProduct = new ArrayList<>();
        List<Object[]> list = new ArrayList<>();
        if (keyword == null || keyword.equals("")) {
            return listProduct;
        }
        String[] listKeyword = keyword.trim().split(" ");
        String str = "";
        for (String listKeyword1 : listKeyword) {
            str += "w.value LIKE \'%" + listKeyword1 + "%\' OR ";
        }

        //list category
        String cateSql = "";
        if (listCategory.size() == 1) {
            cateSql = "(" + listCategory.get(0).getCategoryID() + ")";
        } else {
            String cateStr = "(";
            for (int i = 0; i < listCategory.size(); i++) {
                cateStr += listCategory.get(i).getCategoryID() + ", ";
            }
            cateSql = cateStr.substring(0, cateStr.length() - 2);
            cateSql += ")";
        }

        //list supplier
        String supSql = "";
        if (listSupplier.size() == 1) {
            supSql = "(" + listSupplier.get(0).getSupplierID() + ")";
        } else {
            String supStr = "(";
            for (int i = 0; i < listSupplier.size(); i++) {
                supStr += listSupplier.get(i).getSupplierID() + ", ";
            }
            supSql = supStr.substring(0, supStr.length() - 2);
            supSql += ")";
        }

        String sql = str.substring(0, str.length() - 4);
        String q = "select a.* from (select p.ProductID, count(*) as result_count from Product p cross apply string_split(ProductName, ' ') w where p.productStatus = 1 AND p.categoryID IN " + cateSql + " AND p.supplierID IN " + supSql + " AND " + sql + " group by ProductID) c inner join Product a on a.ProductID = c.ProductID order by c.result_count desc";
        Query query = em.createNativeQuery(q);
        list = query.getResultList();

        for (int i = 0; i < list.size(); i++) {
            Object[] obj = list.get(i);
            Product p = find(obj[0]);
            listProduct.add(p);
        }
        System.out.println("Search: " + listProduct.size());
        return listProduct;
    }

    @Override
    public Product findBestSeller(Category category) {
        Query query = em.createQuery("SELECT o.productID, SUM(o.orderDetailsQuantity) AS SumQuantity FROM OrderDetails o JOIN o.productID p WHERE p.productStatus = :productStatus AND p.categoryID = :categoryID GROUP BY o.productID ORDER BY SumQuantity DESC");
        query.setParameter("productStatus", 1);
        query.setParameter("categoryID", category);
        List<Object[]> listSell = query.getResultList();
//        List<Product> listProduct = new ArrayList<>();
//        listProduct.add(find(listSell.get(0)[0]));
        if (listSell.size() > 0) {
            Product p = (Product) listSell.get(0)[0];
            return p;
        } else {
            return null;
        }
    }

    @Override
    public boolean hideBtnStatusByCategory(int idC, int idS) {
        Query queryC = em.createQuery("SELECT c FROM Category c WHERE c.categoryID = :cateID AND c.categoryStatus = 0");
        queryC.setParameter("cateID", idC);

        Query queryS = em.createQuery("SELECT s FROM Supplier s WHERE s.supplierID = :suppID AND s.supplierStatus = 0");
        queryS.setParameter("suppID", idS);

        if (!queryC.getResultList().isEmpty()) {
            return true;
        } else if (!queryS.getResultList().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkOrderDetailsByProduct(Product id) {
        Query query = em.createQuery("SELECT od FROM OrderDetails od WHERE od.productID = :proID");
        query.setParameter("proID", id);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkProductExist(String name) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.productName = :proName");
        query.setParameter("proName", name);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean checkUpdateProductExist(String name, int id) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.productName = :proName AND p.productID != :proID");
        query.setParameter("proName", name);
        query.setParameter("proID", id);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void deleteProductImageByProduct(Product id) {
        Query query = em.createQuery("DELETE FROM ProductImage pi WHERE pi.productID = :proID");
        query.setParameter("proID", id);
        query.executeUpdate();
    }

    @Override
    public void deletePromotionDetailsByProduct(Product id) {
        Query query = em.createQuery("DELETE FROM PromotionDetails pd WHERE pd.productID = :proID");
        query.setParameter("proID", id);
        query.executeUpdate();
    }

    @Override
    public void deleteCartByProduct(Product id) {
        Query query = em.createQuery("DELETE FROM Cart c WHERE c.productID = :proID");
        query.setParameter("proID", id);
        query.executeUpdate();
    }
    
    @Override
    public List<String> findProductName(Category id) {
        Query query = em.createQuery("SELECT p.productName FROM Product p WHERE p.categoryID = :cateID");
        query.setParameter("cateID", id);
        return (List<String>) query.getResultList();
    }
    
    @Override
    public List<Number> countProductQuantity(Category id) {
        Query query = em.createQuery("SELECT p.productQuantity FROM Product p WHERE p.categoryID = :cateID");
        query.setParameter("cateID", id);
        return (List<Number>) query.getResultList();
    }

}
