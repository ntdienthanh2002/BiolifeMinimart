/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entity.Customer;
import com.sessionbean.CustomerFacadeLocal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;
import org.hibernate.validator.constraints.NotEmpty;
import com.cloudinary.Cloudinary;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Pattern;

/**
 *
 * @author OS
 */
@Named(value = "customerMB")
@SessionScoped
public class CustomerMB implements Serializable {

    @EJB
    private CustomerFacadeLocal customerFacade;

    @NotEmpty(message = "Please enter Confirm Password")
    private String passConf;

    @NotEmpty(message = "Please enter New Password")
    private String passNew;

    private Customer customer;

    @NotEmpty(message = "Please enter Username/ Email/ Phone")
    private String uname;

    @NotEmpty(message = "Please enter Password")
    private String pword;

    @NotEmpty(message = "Please enter Gender")
    private String gender;

    private Part file;

    private boolean flag = false;
    private String mess;

    private String otpCode;

    @NotEmpty(message = "Please enter Email")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is invalidate. Ex: info@123.gmail.com")
    private String email;

    @NotEmpty(message = "Please enter OTP code")
    private String optConf;
    private boolean valid = false;

    public CustomerMB() {
        customer = new Customer();
        customer.setCustomerAvatar("https://res.cloudinary.com/dk6tfexdn/image/upload/v1679917899/Biolife/Customer/customer_default.jpg");
    }

    public void login() throws NoSuchAlgorithmException {
        Customer c = new Customer();

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pword.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest);

        c = customerFacade.login(uname, myHash);

        if (c.getCustomerUsername() == null) {
            mess = "Login fail! Username/ Email/ Phone or Password incorrect";
        } else {
            customer = c;
            gender = c.getCustomerGender() + "";
            flag = true;
            pword = null;
            mess = null;
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void checkLogin() {
        if (flag == false) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updatePersonal() {
        uploadFile(customer.getCustomerUsername());
        customer.setCustomerGender(Integer.parseInt(gender));

        customerFacade.editCustomer(customer);

        gender = customer.getCustomerGender() + "";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("setting.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void changePassword() throws NoSuchAlgorithmException {

        String pTemp = pword;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pTemp.getBytes());
        byte[] digest = md.digest();
        pTemp = DatatypeConverter.printHexBinary(digest);

        if (!customer.getCustomerPassword().equals(pTemp)) {
            mess = "Old Password incorrect";
        } else if (!passNew.equals(passConf)) {
            mess = "Confirm Password mus be the same as Password";
        } else {
            md.update(passNew.getBytes());
            digest = md.digest();
            passNew = DatatypeConverter.printHexBinary(digest);
            customer.setCustomerPassword(passNew);

            customerFacade.changePassword(customer.getCustomerUsername(), passNew);

            mess = null;
            pword = null;
            passConf = null;
            passNew = null;
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("setting.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void logout() {

        flag = false;
        customer = new Customer();
        customer.setCustomerAvatar("https://res.cloudinary.com/dk6tfexdn/image/upload/v1679917899/Biolife/Customer/customer_default.jpg");
        gender = null;
        uname = null;
        pword = null;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void register() throws NoSuchAlgorithmException {

        if (!customer.getCustomerPassword().equals(passConf)) {
            mess = "Confirm Password must be the same as Password";
        } else if (customerFacade.checkExistUsername(customer.getCustomerUsername())) {
            mess = "Username already exists";
        } else if (customerFacade.checkExistPhone(customer.getCustomerPhone())) {
            mess = "Phone already exists";
        } else if (customerFacade.checkExistEmail(customer.getCustomerEmail())) {
            mess = "Email already exists";
        } else {
            // ma hoa password
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(customer.getCustomerPassword().getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest);
            customer.setCustomerPassword(myHash);

            uploadFile(customer.getCustomerUsername());
            customer.setCustomerGender(Integer.parseInt(gender));

            customer.setCustomerStatus(1);
            customerFacade.create(customer);

            customer = new Customer();
            customer.setCustomerAvatar("https://res.cloudinary.com/dk6tfexdn/image/upload/v1679917899/Biolife/Customer/customer_default.jpg");
            file = null;
            mess = null;
            passConf = null;

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            url.put("public_id", "Biolife/Customer/Customer_" + image);
            url.put("overwrite", true);
            url.put("resource_type", "image");
            try {
                Map r = cloudinary.uploader().upload(changeFile(file), url);
                String imgurl = (String) r.get("secure_url");
                System.out.println(imgurl);

                customer.setCustomerAvatar(imgurl);
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

    public void sendOTP() {
        if (customerFacade.checkExistEmail(email)) {
            otpCode = Math.round(Math.random() * 1000000) + "";

            String to = email;
            final String from = "biolifeminimart@gmail.com";
            final String password = "evwvnrakctrcthqr";
            String host = "smtp.gmail.com";
            String displayName = "Biolife Minimart";

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.port", "587");

            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });
            mess = "";
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from, displayName));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Biolife Minimart");
                String messText = "<div align='center'>"
                        + "<div style='width: 400px;;border-style:solid;border-width:thin;border-color:#dadce0;border-radius:8px;padding:40px 20px'  align='center'>"
                        + "  <img   src='https://res.cloudinary.com/dk6tfexdn/image/upload/v1682308391/Biolife/Logo/logo-biolife-1_yi9krq.png' height='30' aria-hidden='true' style='margin-bottom:16px' alt='Google' class='CToWUd' data-bit='iit'>"
                        + "  <div style='border-bottom:thin solid #dadce0;color:rgba(0,0,0,0.87);line-height:32px;padding-bottom:24px;text-align:center;word-break:break-word'>"
                        + "    <div style='font-size:24px'> Hello " + email + " !</div>"
                        + "  </div>"
                        + "  <table align='justify' style='margin-top:8px'>"
                        + "    <tbody>"
                        + "      <tr style='line-height:normal'>"
                        + "        <td>You have just implemented the forgot password function of Biolife website. Please get the OTP to create a new password.</td>"
                        + "      </tr>"
                        + "      <tr>"
                        + "        <td>Your OTP code:</td>"
                        + "      </tr>"
                        + "      <tr>"
                        + "        <td align='center'>"
                        + "          <h1 style='background-color: #7faf51;color: #ffffff;'>" + otpCode + "</h1>"
                        + "        </td>"
                        + "      </tr>"
                        + "      <tr>"
                        + "        <td style='color: red;'>Please do not give OTP to anyone !!!</td>"
                        + "      </tr>"
                        + "    </tbody>"
                        + "  </table>"
                        + "</div>"
                        + "</div>";
                message.setContent(messText, "text/html");

                Transport.send(message);
                // System.out.println("Your OTP code is :<h1>" + otpCode + "</h1>");
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("forgot_password.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (MessagingException | UnsupportedEncodingException ex) {
            }

        } else {
            mess = "Email is not exits";
        }
    }

    public void checkOTP() {
        if (otpCode.equals(optConf)) {
            valid = true;
            mess = "";
        } else {
            mess = "OTP code is incorrect!!!";
        }
    }

    public void forgot_password() throws NoSuchAlgorithmException {
        if (passNew.equals(passConf)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passNew.getBytes());
            byte[] digest = md.digest();
            passNew = DatatypeConverter.printHexBinary(digest);
            customerFacade.forgot_pass(email, passNew);
            email = "";
            passNew = "";
            passConf = "";
            optConf = "";
            otpCode = "";
            mess = "";
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            mess = "Confirm Password mus be the same as New Password";
        }
    }

    public List<Customer> showAllCustomers() {
        return customerFacade.findAll();
    }

    public String formatGender(String username) {
        customer = customerFacade.find(username);
        switch (customer.getCustomerGender()) {
            case 0:
                return "Male";
            case 1:
                return "Female";
            default:
                return "Other";
        }
    }

    public String formatStatus(String username) {
        customer = customerFacade.find(username);
        if (customer.getCustomerStatus() == 1) {
            return "Enable";
        } else {
            return "Disable";
        }
    }

    public String buttonStatus(String username) {
        customer = customerFacade.find(username);
        if (customer.getCustomerStatus() == 1) {
            return "badge-success";
        } else {
            return "badge-warning";
        }
    }

    public String updateCustomerStatus(String username) {
        customer = customerFacade.find(username);
        if (customer.getCustomerStatus() == 0) {
            customer.setCustomerStatus(1);
            customerFacade.edit(customer);
        } else {
            customer.setCustomerStatus(0);
            customerFacade.edit(customer);
        }
        return "customer_all";
    }

    public String getPassConf() {
        return passConf;
    }

    public void setPassConf(String passConf) {
        this.passConf = passConf;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getPassNew() {
        return passNew;
    }

    public void setPassNew(String passNew) {
        this.passNew = passNew;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOptConf() {
        return optConf;
    }

    public void setOptConf(String optConf) {
        this.optConf = optConf;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
