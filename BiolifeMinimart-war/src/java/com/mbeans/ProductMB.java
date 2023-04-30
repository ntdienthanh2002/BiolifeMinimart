/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.cloudinary.Cloudinary;
import com.entity.Category;
import com.entity.Product;
import com.entity.ProductImage;
import com.entity.Supplier;
import com.sessionbean.CategoryFacadeLocal;
import com.sessionbean.ProductFacadeLocal;
import com.sessionbean.SupplierFacadeLocal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author OS
 */
@Named(value = "productMB")
@SessionScoped
public class ProductMB implements Serializable {

    @EJB
    private SupplierFacadeLocal supplierFacade;

    @EJB
    private CategoryFacadeLocal categoryFacade;

    @EJB
    private ProductFacadeLocal productFacade;

    private int pageNumber = 1;

    private int cateID;

    private String sortSelected;

    private int filterPrice = 0;

    private int filterBrand = 0;

    private int productIDDetails;

    private double price1 = 0;
    private double price2 = 0;

    private String keyword;

    private Product product;
    private int idProduct;
    private int idCategory;
    private int idSupplier;
    private Part file;
    String imageSql;
    private double importPrice = 0;
    private double unitPrice = 0;
    private String nameMessage;
    private String quantityMessage;
    private String importPriceMessage;
    private String unitPriceMessage;
    private String perUnitMessage;
    private String descriptionMessage;
    private String imageMessage;

    public ProductMB() {
        product = new Product();
    }
    
    public int getProductQuantity(int id) {
        return productFacade.find(id).getProductQuantity();
    }

    public List<Product> showProductByCatgory(int id) {
        List<Product> listProduct = productFacade.showProductByCatgory(categoryFacade.find(id));
        return listProduct;
    }

    public List<Product> showProducts() {
        List<Product> listProduct = new ArrayList<>();
        List<Category> listCategory = new ArrayList<>();
        List<Supplier> listSupplier = new ArrayList<>();

        if (cateID == 0) {
            listCategory = categoryFacade.findEnableCategories();
        } else {
            listCategory.add(categoryFacade.find(cateID));
        }

        if (filterBrand == 0) {
            listSupplier = supplierFacade.findEnableSuppliers();
        } else {
            listSupplier.add(supplierFacade.find(filterBrand));
        }

        Product highestProduct = productFacade.getHighestProduct().get(0);
        switch (filterPrice) {
            case 0:
                price1 = 0;
                price2 = highestProduct.getProductUnitPrice().doubleValue();
                break;
            case 1:
                price1 = 0;
                price2 = 5;
                break;
            case 2:
                price1 = 5;
                price2 = 10;
                break;
            case 3:
                price1 = 10;
                price2 = 20;
                break;
            case 4:
                price1 = 20;
                price2 = 45;
                break;
            case 5:
                price1 = 45;
                price2 = 100;
                break;
            case 6:
                price1 = 100;
                price2 = 150;
                break;
            case 7:
                price1 = 150;
                price2 = highestProduct.getProductUnitPrice().doubleValue();
                break;
        }

        if (sortSelected == null || sortSelected.equals("default")) {
            listProduct = productFacade.showCategoryPagination(listCategory, pageNumber, listSupplier, price1, price2, keyword);
        } else {
            listProduct = productFacade.showSortCategoryProduct(listCategory, pageNumber, sortSelected, listSupplier, price1, price2, keyword);
        }
        return listProduct;
    }

    public List<Integer> calculateMaxPageNumber() {
        List<Product> listProduct = new ArrayList<>();
        List<Category> listCategory = new ArrayList<>();
        List<Supplier> listSupplier = new ArrayList<>();

        if (cateID == 0) {
            listCategory = categoryFacade.findEnableCategories();
        } else {
            listCategory.add(categoryFacade.find(cateID));
        }

        if (filterBrand == 0) {
            listSupplier = supplierFacade.findEnableSuppliers();
        } else {
            listSupplier.add(supplierFacade.find(filterBrand));
        }

        listProduct = productFacade.showAllProducts(listCategory, pageNumber, listSupplier, price1, price2, keyword);

        int p = (int) Math.ceil((double) listProduct.size() / 9);
        List<Integer> listPage = new ArrayList<>();
        for (int i = 1; i <= p; i++) {
            listPage.add(i);
        }
        return listPage;
    }

    public int getMaxPage() {
        List<Integer> list = calculateMaxPageNumber();
        if (list.size() > 0) {
            return list.get(list.size() - 1);
        } else {
            return 0;
        }
    }

    public String findCategoryName(int id) {
        if (cateID != 0) {
            return categoryFacade.find(id).getCategoryName();
        } else {
            return null;
        }
    }

    public int findProductCategory(int id) {
        if (productIDDetails != 0) {
            return productFacade.find(id).getCategoryID().getCategoryID();
        } else {
            return 0;
        }
    }

    public String findCategoryProductDetails(int id) {
        return categoryFacade.find(id).getCategoryName();
    }

    public String findProductDetailsName(int id) {
        return productFacade.find(id).getProductName();
    }

    public boolean checkCurrentPage(int page) {
        if (page == pageNumber) {
            return true;
        } else {
            return false;
        }
    }

    public String changeSort() {
        pageNumber = 1;
        return "products";
    }

    public void resetListProduct() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        cateID = Integer.valueOf(ec.getRequestParameterMap().get("category"));
        pageNumber = Integer.valueOf(ec.getRequestParameterMap().get("page"));
        keyword = "";
        sortSelected = null;
        filterPrice = 0;
        filterBrand = 0;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("products.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ProductMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewAllResult() {
        System.out.println("Keyword: " + keyword);
        cateID = 0;
        sortSelected = null;
        filterPrice = 0;
        filterBrand = 0;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("products.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ProductMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Product> searchProduct() {
        List<Supplier> listSupplier = supplierFacade.findEnableSuppliers();
        List<Category> listCategory = categoryFacade.findEnableCategories();
        List<Product> listTemp = new ArrayList<>();
        List<Product> listProduct = new ArrayList<>();
        listTemp = (List<Product>) productFacade.searchProduct(listCategory, listSupplier, keyword);
        if (listTemp.size() > 5) {
            listProduct.add(listTemp.get(0));
            listProduct.add(listTemp.get(1));
            listProduct.add(listTemp.get(2));
            listProduct.add(listTemp.get(3));
            listProduct.add(listTemp.get(4));
        } else {
            listProduct.addAll(listTemp);
        }
        return listProduct;
    }

    public int countSearch() {
        List<Supplier> listSupplier = supplierFacade.findEnableSuppliers();
        List<Category> listCategory = categoryFacade.findEnableCategories();
        List<Product> listProduct = (List<Product>) productFacade.searchProduct(listCategory, listSupplier, keyword);
        return listProduct.size();
    }

    public int countProduct() {
        List<Product> listProduct = new ArrayList<>();
        List<Category> listCategory = new ArrayList<>();
        List<Supplier> listSupplier = new ArrayList<>();

        if (cateID == 0) {
            listCategory = categoryFacade.findEnableCategories();
        } else {
            listCategory.add(categoryFacade.find(cateID));
        }

        if (filterBrand == 0) {
            listSupplier = supplierFacade.findEnableSuppliers();
        } else {
            listSupplier.add(supplierFacade.find(filterBrand));
        }
        listProduct = (List<Product>) productFacade.showAllProducts(listCategory, pageNumber, listSupplier, price1, price2, keyword);
        return listProduct.size();
    }

    public List<Product> showProductDetails() {
        List<Product> listProduct = new ArrayList<>();
        Product p = productFacade.find(productIDDetails);
        listProduct.add(p);
        return listProduct;
    }

    public List<ProductImage> showProductImages(int id) {
        return (List<ProductImage>) productFacade.find(id).getProductImageCollection();
    }

    public List<Product> showRelatedProducts(int id) {
        List<Product> listProduct = (List<Product>) categoryFacade.find(productFacade.find(id).getCategoryID().getCategoryID()).getProductCollection();
        List<Product> listRelated = new ArrayList<>();
        for (int i = 0; i < listProduct.size(); i++) {
            if (!listProduct.get(i).getProductID().equals(id)) {
                listRelated.add(listProduct.get(i));
            }
        }

        return listRelated;
    }

    public List<Product> showBestSeller() {
        List<Category> listCategory = categoryFacade.findEnableCategories();
        List<Product> listProduct = new ArrayList<>();
        for (int i = 0; i < listCategory.size(); i++) {
            if (productFacade.findBestSeller(listCategory.get(i)) != null) {
                listProduct.add(productFacade.findBestSeller(listCategory.get(i)));
            }
        }
        return listProduct;
    }

    public List<Product> showAllProducts() {
        return productFacade.findAll();
    }

    public String formatStatus(int id) {
        product = productFacade.find(id);
        if (product.getProductStatus() == 1) {
            return "Enable";
        } else {
            return "Disable";
        }
    }

    public String buttonStatus(int id) {
        product = productFacade.find(id);
        if (product.getProductStatus() == 1) {
            return "badge-success";
        } else {
            return "badge-warning";
        }
    }

    public boolean disableButtonStatus(int id) {
        Product prod = productFacade.find(id);
        return productFacade.hideBtnStatusByCategory(prod.getCategoryID().getCategoryID(), prod.getSupplierID().getSupplierID());
    }

    public String showInsertProductForm() {
        product = new Product();
        importPrice = 0;
        unitPrice = 0;
        nameMessage = "";
        quantityMessage = "";
        importPriceMessage = "";
        unitPriceMessage = "";
        perUnitMessage = "";
        descriptionMessage = "";
        imageMessage = "";
        return "product_create";
    }

    public boolean checkInsertForm() {
        boolean valid = true;

        if (product.getProductName().equals("")) {
            nameMessage = "Name cannot be empty";
            valid = false;
        } else if (productFacade.checkProductExist(product.getProductName())) {
            nameMessage = "Name already exists";
            valid = false;
        } else {
            nameMessage = "";
        }

        if (product.getProductQuantity() <= 0) {
            quantityMessage = "Quantity must be greater than 0";
            valid = false;
        } else {
            quantityMessage = "";
        }

        if (importPrice <= 0) {
            importPriceMessage = "Import Price must be greater than 0";
            valid = false;
        } else {
            importPriceMessage = "";
        }

        if (unitPrice <= 0) {
            unitPriceMessage = "Unit Price must be greater than 0";
            valid = false;
        } else {
            unitPriceMessage = "";
        }

        if (product.getProductQuantityPerUnit().equals("")) {
            perUnitMessage = "Per Unit cannot be empty";
            valid = false;
        } else {
            perUnitMessage = "";
        }

        if (product.getProductDescription().equals("")) {
            descriptionMessage = "Description cannot be empty";
            valid = false;
        } else {
            descriptionMessage = "";
        }

        if (file == null) {
            imageMessage = "Image cannot be empty";
            valid = false;
        } else {
            imageMessage = "";
        }

        return valid;
    }

    public void insertProduct() {
        try {
            if (checkInsertForm()) {
                product.setCategoryID(categoryFacade.find(idCategory));
                product.setSupplierID(supplierFacade.find(idSupplier));
                product.setProductImportPrice(new BigDecimal(importPrice));
                product.setProductUnitPrice(new BigDecimal(unitPrice));
                product.setProductImage("null");
                product.setProductStatus(1);
                productFacade.create(product);

                String imageCloud = "Product" + product.getProductID();
                uploadFile(imageCloud);

                Product prod = productFacade.find(product.getProductID());
                prod.setProductImage(imageSql);
                productFacade.edit(prod);
                FacesContext.getCurrentInstance().getExternalContext().redirect("product_all.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(ProductMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void uploadFile(String image) {
        if (file != null) {
            Map config = new HashMap<>();
            config.put("cloud_name", "dk6tfexdn");
            config.put("api_key", "538259883143198");
            config.put("api_secret", "LqOt7ScJTK6gFNkYUSeSpGdn9zg");
            Cloudinary cloudinary = new Cloudinary(config);
            Map url = new HashMap();
            url.put("public_id", "Biolife/Product/" + image);
            url.put("overwrite", true);
            url.put("resource_type", "image");
            try {
                Map r = cloudinary.uploader().upload(changeFile(file), url);
                imageSql = (String) r.get("secure_url");
                System.out.println(imageSql);
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
    }

    private String changeFile(Part f) {
        String imagePath = null;
        if (f != null) {
            InputStream content = null;
            try {
                content = f.getInputStream();
                FacesContext context = FacesContext.getCurrentInstance();
                ExternalContext ec = context.getExternalContext();
                HttpServletRequest request = (HttpServletRequest) ec.getRequest();
                String applicationPath = request.getServletContext().getRealPath("");
                String uploadFilePath = applicationPath + File.separator + "resources";
                File fileSaveDir = new File(uploadFilePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdirs();
                }
                OutputStream outputStream = null;
                try {
                    File outputFilePath = new File(uploadFilePath + File.separator + f.getSubmittedFileName());
                    imagePath = uploadFilePath + File.separator + f.getSubmittedFileName();
                    content = f.getInputStream();
                    outputStream = new FileOutputStream(outputFilePath);
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = content.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
                    System.out.println("File uploaded successfully!");
                } catch (Exception ex) {
                    ex.toString();
                } finally {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (content != null) {
                        content.close();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(imagePath).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    content.close();
                } catch (IOException ex) {
                    Logger.getLogger(imagePath).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println(imagePath);
        return imagePath;
    }

    public String showUpdateProductForm(int id) {
        product = productFacade.find(id);
        idProduct = product.getProductID();
        idCategory = product.getCategoryID().getCategoryID();
        idSupplier = product.getSupplierID().getSupplierID();
        importPrice = product.getProductImportPrice().doubleValue();
        unitPrice = product.getProductUnitPrice().doubleValue();
        nameMessage = "";
        quantityMessage = "";
        importPriceMessage = "";
        unitPriceMessage = "";
        perUnitMessage = "";
        descriptionMessage = "";
        imageMessage = "";
        return "product_update";
    }

    public boolean checkUpdateForm() {
        boolean valid = true;

        if (product.getProductName().equals("")) {
            nameMessage = "Name cannot be empty";
            valid = false;
        } else if (productFacade.checkUpdateProductExist(product.getProductName(), product.getProductID())) {
            nameMessage = "Name already exists";
            valid = false;
        } else {
            nameMessage = "";
        }

        if (product.getProductQuantity() <= 0) {
            quantityMessage = "Quantity must be greater than 0";
            valid = false;
        } else {
            quantityMessage = "";
        }

        if (importPrice <= 0) {
            importPriceMessage = "Import Price must be greater than 0";
            valid = false;
        } else {
            importPriceMessage = "";
        }

        if (unitPrice <= 0) {
            unitPriceMessage = "Unit Price must be greater than 0";
            valid = false;
        } else {
            unitPriceMessage = "";
        }

        if (product.getProductQuantityPerUnit().equals("")) {
            perUnitMessage = "Per Unit cannot be empty";
            valid = false;
        } else {
            perUnitMessage = "";
        }

        if (product.getProductDescription().equals("")) {
            descriptionMessage = "Description cannot be empty";
            valid = false;
        } else {
            descriptionMessage = "";
        }

        return valid;
    }

    public void updateProduct() {
        try {
            if (checkUpdateForm()) {
                Product prod = productFacade.find(idProduct);
                if (file != null) {
                    String imageCloud = "Product" + idProduct;
                    uploadFile(imageCloud);
                    prod.setProductImage(imageSql);
                }
                prod.setCategoryID(categoryFacade.find(idCategory));
                prod.setSupplierID(supplierFacade.find(idSupplier));
                prod.setProductName(product.getProductName());
                prod.setProductQuantity(product.getProductQuantity());
                prod.setProductImportPrice(new BigDecimal(importPrice));
                prod.setProductUnitPrice(new BigDecimal(unitPrice));
                prod.setProductQuantityPerUnit(product.getProductQuantityPerUnit());
                prod.setProductDescription(product.getProductDescription());
                productFacade.edit(prod);
                FacesContext.getCurrentInstance().getExternalContext().redirect("product_all.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(ProductMB.class.getName()).log(Level.SEVERE, null, ex);
        }

//        return "product_all";
    }

    public String updateProductStatus(int id) {
        product = productFacade.find(id);
        if (product.getProductStatus() == 0) {
            product.setProductStatus(1);
            productFacade.edit(product);
        } else {
            product.setProductStatus(0);
            productFacade.edit(product);
        }
        return "product_all";
    }

    public String disableProduct(int id) {
        product = productFacade.find(id);
        product.setProductStatus(0);
        productFacade.edit(product);
        return "product_all";
    }

    public String deleteProduct(int id) {
        product = productFacade.find(id);
        if (productFacade.checkOrderDetailsByProduct(product)) {
            disableProduct(product.getProductID());
            return "product_all";
        }
        productFacade.deleteProductImageByProduct(product);
        productFacade.deletePromotionDetailsByProduct(product);
        productFacade.deleteCartByProduct(product);
        productFacade.remove(product);
        return "product_all";
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getCateID() {
        return cateID;
    }

    public void setCateID(int cateID) {
        this.cateID = cateID;
    }

    public String getSortSelected() {
        return sortSelected;
    }

    public void setSortSelected(String sortSelected) {
        this.sortSelected = sortSelected;
    }

    public int getProductIDDetails() {
        return productIDDetails;
    }

    public void setProductIDDetails(int productIDDetails) {
        this.productIDDetails = productIDDetails;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public int getFilterPrice() {
        return filterPrice;
    }

    public void setFilterPrice(int filterPrice) {
        this.filterPrice = filterPrice;
    }

    public int getFilterBrand() {
        return filterBrand;
    }

    public void setFilterBrand(int filterBrand) {
        this.filterBrand = filterBrand;
    }

    public double getPrice1() {
        return price1;
    }

    public void setPrice1(double price1) {
        this.price1 = price1;
    }

    public double getPrice2() {
        return price2;
    }

    public void setPrice2(double price2) {
        this.price2 = price2;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getNameMessage() {
        return nameMessage;
    }

    public void setNameMessage(String nameMessage) {
        this.nameMessage = nameMessage;
    }

    public String getQuantityMessage() {
        return quantityMessage;
    }

    public void setQuantityMessage(String quantityMessage) {
        this.quantityMessage = quantityMessage;
    }

    public String getImportPriceMessage() {
        return importPriceMessage;
    }

    public void setImportPriceMessage(String importPriceMessage) {
        this.importPriceMessage = importPriceMessage;
    }

    public String getUnitPriceMessage() {
        return unitPriceMessage;
    }

    public void setUnitPriceMessage(String unitPriceMessage) {
        this.unitPriceMessage = unitPriceMessage;
    }

    public String getPerUnitMessage() {
        return perUnitMessage;
    }

    public void setPerUnitMessage(String perUnitMessage) {
        this.perUnitMessage = perUnitMessage;
    }

    public String getDescriptionMessage() {
        return descriptionMessage;
    }

    public void setDescriptionMessage(String descriptionMessage) {
        this.descriptionMessage = descriptionMessage;
    }

    public String getImageMessage() {
        return imageMessage;
    }

    public void setImageMessage(String imageMessage) {
        this.imageMessage = imageMessage;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
