/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.cloudinary.Cloudinary;
import com.entity.Product;
import com.entity.ProductImage;
import com.sessionbean.ProductFacadeLocal;
import com.sessionbean.ProductImageFacadeLocal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author OS
 */
@Named(value = "productImageMB")
@RequestScoped
public class ProductImageMB {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private ProductImageFacadeLocal productImageFacade;

    private ProductImage productImage;
    private int idProductImage;
    private int idProduct;
    private Part file;
    private String fileMessage;
    String imageSql;

    public ProductImageMB() {
        productImage = new ProductImage();
    }

    public String showProductImagePage(int id) {
        Product prod = productFacade.find(id);
        idProduct = prod.getProductID();
        return "productimage_all";
    }

//    public void showProductImagePage() {
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        idProduct = Integer.valueOf(ec.getRequestParameterMap().get("idP"));
//        System.out.println(idProduct);
////        Product prod = productFacade.find(id);
//        try {
//                FacesContext.getCurrentInstance().getExternalContext().redirect("productimage_all.xhtml");
//            } catch (IOException ex) {
//                Logger.getLogger(ProductImageMB.class.getName()).log(Level.SEVERE, null, ex);
//            }
////        return "productimage_all";
//    }
    public List<ProductImage> showProductImageByProductID() {
//        System.out.println(idProduct);
        Product prod = productFacade.find(idProduct);
        return productImageFacade.findProductImageByProductID(prod);
    }

    public boolean checkInsertForm() {
        boolean valid = true;

        if (file == null) {
            fileMessage = "Image cannot be empty";
            valid = false;
        } else {
            fileMessage = "";
        }

        return valid;
    }

    public String insertProductImage() throws ServletException, IOException {
//        try {
//            if (checkInsertForm()) {
//                for (Part part : getAllParts(file)) {
//                    productImage.setProductImagePath("null");
//                    Product prod = productFacade.find(idProduct);
//                    productImage.setProductID(prod);
//                    productImageFacade.create(productImage);
//
//                    String imageCloud = "Product" + idProduct + "_" + productImage.getProductImageID();
//                    uploadFile(imageCloud, part);
//
//                    ProductImage pi = productImageFacade.find(productImage.getProductImageID());
//                    pi.setProductImagePath(imageSql);
//                    productImageFacade.edit(pi);
//                }
//                FacesContext.getCurrentInstance().getExternalContext().redirect("productimage_all.xhtml");
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(ProductImageMB.class.getName()).log(Level.SEVERE, null, ex);
//        }

        if (file == null) {
            fileMessage = "Image cannot be empty";
            return "productimage_all";
        }
        for (Part part : getAllParts(file)) {
            productImage.setProductImagePath("null");
            Product prod = productFacade.find(idProduct);
            productImage.setProductID(prod);
            productImageFacade.create(productImage);

            String imageCloud = "Product" + idProduct + "_" + productImage.getProductImageID();
            uploadFile(imageCloud, part);

            ProductImage pi = productImageFacade.find(productImage.getProductImageID());
            pi.setProductImagePath(imageSql);
            productImageFacade.edit(pi);
        }

        return "productimage_all";
    }

    public static List<javax.servlet.http.Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

    public void uploadFile(String image, Part fileCloud) {
//        if (file != null) {
        Map config = new HashMap<>();
        config.put("cloud_name", "dk6tfexdn");
        config.put("api_key", "538259883143198");
        config.put("api_secret", "LqOt7ScJTK6gFNkYUSeSpGdn9zg");
        Cloudinary cloudinary = new Cloudinary(config);
        Map url = new HashMap();
        url.put("public_id", "Biolife/ProductImage/" + image);
        url.put("overwrite", true);
        url.put("resource_type", "image");
        try {
            Map r = cloudinary.uploader().upload(changeFile(fileCloud), url);
            imageSql = (String) r.get("secure_url");
            System.out.println(imageSql);
        } catch (IOException ex) {
            ex.getMessage();
        }
//        }
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

    public String deleteProductImage(int id) {
        System.out.println("Delete img");
        productImage = productImageFacade.find(id);
        productImageFacade.remove(productImage);
        return "productimage_all";
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    public int getIdProductImage() {
        return idProductImage;
    }

    public void setIdProductImage(int idProductImage) {
        this.idProductImage = idProductImage;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getFileMessage() {
        return fileMessage;
    }

    public void setFileMessage(String fileMessage) {
        this.fileMessage = fileMessage;
    }

}
