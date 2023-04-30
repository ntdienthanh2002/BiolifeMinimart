/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.cloudinary.Cloudinary;
import com.entity.Category;
import com.entity.Product;
import com.sessionbean.CategoryFacadeLocal;
import com.sessionbean.ProductFacadeLocal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
@Named(value = "categoryMB")
@SessionScoped
public class CategoryMB implements Serializable {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private CategoryFacadeLocal categoryFacade;

    private Category category;
    private int idCategory;
    private Part file;
    String imageSql;
    private String nameMessage;
    private String imageMessage;

    public CategoryMB() {
        category = new Category();
    }

    public int countProductsByCategory(int id) {
        List<Product> listProduct = productFacade.showProductByCatgory(categoryFacade.find(id));
        return listProduct.size();
    }

    public List<Category> showAllCategories() {
        return categoryFacade.findAll();
    }

    public List<Category> showEnableCategories() {
        return categoryFacade.findEnableCategories();
    }

    public String formatStatus(int id) {
        category = categoryFacade.find(id);
        if (category.getCategoryStatus() == 1) {
            return "Enable";
        } else {
            return "Disable";
        }
    }

    public String buttonStatus(int id) {
        category = categoryFacade.find(id);
        if (category.getCategoryStatus() == 1) {
            return "badge-success";
        } else {
            return "badge-warning";
        }
    }

    public String showInsertCategoryForm() {
        category = new Category();
        nameMessage = "";
        imageMessage = "";
        return "category_create";
    }

    public boolean checkInsertForm() {
        boolean valid = true;

        if (category.getCategoryName().equals("")) {
            nameMessage = "Name cannot be empty";
            valid = false;
        } else if (categoryFacade.checkCategoryExist(category.getCategoryName())) {
            nameMessage = "Name already exists";
            valid = false;
        } else {
            nameMessage = "";
        }

        if (file == null) {
            imageMessage = "Image cannot be empty";
            valid = false;
        } else {
            imageMessage = "";
        }

        return valid;
    }

    public void insertCategory() {
        try {
            if (checkInsertForm()) {
                category.setCategoryImage("null");
                category.setCategoryStatus(1);
                categoryFacade.create(category);

                String imageCloud = "Category" + category.getCategoryID();
                uploadFile(imageCloud);

                Category ca = categoryFacade.find(category.getCategoryID());
                ca.setCategoryImage(imageSql);
                categoryFacade.edit(ca);

                FacesContext.getCurrentInstance().getExternalContext().redirect("category_all.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(CategoryMB.class.getName()).log(Level.SEVERE, null, ex);
        }

//        return "category_all";
    }

    public void uploadFile(String image) {
        if (file != null) {
            Map config = new HashMap<>();
            config.put("cloud_name", "dk6tfexdn");
            config.put("api_key", "538259883143198");
            config.put("api_secret", "LqOt7ScJTK6gFNkYUSeSpGdn9zg");
            Cloudinary cloudinary = new Cloudinary(config);
            Map url = new HashMap();
            url.put("public_id", "Biolife/Category/" + image);
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

    public String showUpdateCategoryForm(int id) {
        category = categoryFacade.find(id);
        idCategory = category.getCategoryID();
        nameMessage = "";
        imageMessage = "";
        return "category_update";
    }

    public boolean checkUpdateForm() {
        boolean valid = true;

        if (category.getCategoryName().equals("")) {
            nameMessage = "Name cannot be empty";
            valid = false;
        } else if (categoryFacade.checkUpdateCategoryExist(category.getCategoryName(), category.getCategoryID())) {
            nameMessage = "Name already exists";
            valid = false;
        } else {
            nameMessage = "";
        }

        return valid;
    }

    public void updateCategory() {
        try {
            if (checkUpdateForm()) {
                System.out.println("update 1 category");
                Category ca = categoryFacade.find(idCategory);
                System.out.println(idCategory);
                System.out.println(ca.getCategoryID());
                System.out.println("update category");
                if (file != null) {
                    System.out.println("update 2 category");
                    String imageCloud = "Category" + idCategory;
                    System.out.println("update 3 category");
                    uploadFile(imageCloud);
                    System.out.println("update 4 category");
                    ca.setCategoryImage(imageSql);
                    System.out.println("update 5 category");
                }
                ca.setCategoryName(category.getCategoryName());
                System.out.println(category.getCategoryName());
                System.out.println("update 6 category");
                categoryFacade.edit(ca);
                System.out.println(ca.getCategoryName());
                System.out.println("update 7 category");
                FacesContext.getCurrentInstance().getExternalContext().redirect("category_all.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(CategoryMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String updateCategoryStatus(int id) {
        category = categoryFacade.find(id);
        if (category.getCategoryStatus() == 0) {
            category.setCategoryStatus(1);
            categoryFacade.edit(category);
            categoryFacade.updateProductStatusByCategory(category, 1);
        } else {
            category.setCategoryStatus(0);
            categoryFacade.edit(category);
            categoryFacade.updateProductStatusByCategory(category, 0);
        }
        return "category_all";
    }

    public String disableCategory(int id) {
        category = categoryFacade.find(id);
        category.setCategoryStatus(0);
        categoryFacade.edit(category);
        categoryFacade.updateProductStatusByCategory(category, 0);
        return "category_all";
    }

    public String deleteCategory(int id) {
        category = categoryFacade.find(id);
        if (categoryFacade.checkProductByCategory(category)) {
            disableCategory(category.getCategoryID());
            return "category_all";
        }
        categoryFacade.remove(category);
        return "category_all";
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getNameMessage() {
        return nameMessage;
    }

    public void setNameMessage(String nameMessage) {
        this.nameMessage = nameMessage;
    }

    public String getImageMessage() {
        return imageMessage;
    }

    public void setImageMessage(String imageMessage) {
        this.imageMessage = imageMessage;
    }

}
