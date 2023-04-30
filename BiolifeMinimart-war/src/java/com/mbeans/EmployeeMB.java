/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.cloudinary.Cloudinary;
import com.entity.Employee;
import com.sessionbean.EmployeeFacadeLocal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.DatatypeConverter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author OS
 */
@Named(value = "employeeMB")
@SessionScoped
public class EmployeeMB implements Serializable {

    @EJB
    private EmployeeFacadeLocal employeeFacade;

    private Employee employee;
    private int usernameEmployee;
    private Part file;
    private String message;

    @NotEmpty(message = "Please enter Username/Email/Phone")
    private String uname;

    @NotEmpty(message = "Please enter Password")
    private String pword;

    @NotEmpty(message = "Please enter Confirm Password")
    private String passConf;

    @NotEmpty(message = "Please enter New Password")
    private String passNew;

    private boolean flag = false;

    public void checkLogin() {
        if (!flag) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login_admin.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void login() throws NoSuchAlgorithmException {
        Employee e = new Employee();

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pword.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest);

        e = employeeFacade.login(uname, myHash);
        if (e.getEmployeeUsername() == null) {
            message = "Login fail! Username/ Email/ Phone or Password incorrect";
        } else {
            employee = e;
            uname = "";
            pword = "";
            flag = true;
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("dashboard.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String logout() {
        employee = new Employee();
        flag = false;
        message = "";
        return "login_admin";
    }

    public void updatePersonal() {
        uploadFile(employee.getEmployeeFullname());
        employeeFacade.edit(employee);
        //message = "success";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EmployeeMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changePassword() throws NoSuchAlgorithmException {

        String pTemp = pword;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pTemp.getBytes());
        byte[] digest = md.digest();
        pTemp = DatatypeConverter.printHexBinary(digest);

        if (!employee.getEmployeePassword().equals(pTemp)) {
            message = "Old Password incorrect";
        } else if (!passNew.equals(passConf)) {
            message = "Confirm Password mus be the same as Password";
        } else {
            md.update(passNew.getBytes());
            digest = md.digest();
            passNew = DatatypeConverter.printHexBinary(digest);
            employee.setEmployeePassword(passNew);

            //customerFacade.changePassword(customer.getCustomerUsername(), passNew);
            employeeFacade.edit(employee);
            message = "";
            pword = null;
            passConf = null;
            passNew = null;
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(EmployeeMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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

    public String getPassConf() {
        return passConf;
    }

    public void setPassConf(String passConf) {
        this.passConf = passConf;
    }

    public String getPassNew() {
        return passNew;
    }

    public void setPassNew(String passNew) {
        this.passNew = passNew;
    }
    //Duy Khanh end

    public EmployeeMB() {
        employee = new Employee();
    }

    public String formatGender(String username) {
        employee = employeeFacade.find(username);
        switch (employee.getEmployeeGender()) {
            case 0:
                return "Male";
            case 1:
                return "Female";
            default:
                return "Other";
        }
    }

    public String formatStatus(String username) {
        employee = employeeFacade.find(username);
        if (employee.getEmployeeStatus() == 1) {
            return "Inventory Manager";
        } else {
            return "Customer Manager";
        }
    }

    public String buttonStatus(String username) {
        employee = employeeFacade.find(username);
        if (employee.getEmployeeStatus() == 1) {
            return "badge-success";
        } else {
            return "badge-info";
        }
    }

    public String showInsertEmployeeForm() {
        employee = new Employee();
        return "employee_create";
    }

    public void uploadFile(String image) {
        if (file != null) {
            Map config = new HashMap<>();
            config.put("cloud_name", "dk6tfexdn");
            config.put("api_key", "538259883143198");
            config.put("api_secret", "LqOt7ScJTK6gFNkYUSeSpGdn9zg");
            Cloudinary cloudinary = new Cloudinary(config);
            Map url = new HashMap();
            url.put("public_id", "Biolife/Employee/Employee_" + image);
            url.put("overwrite", true);
            url.put("resource_type", "image");
            try {
                Map r = cloudinary.uploader().upload(changeFile(file), url);
                String imageUrl = (String) r.get("secure_url");
                System.out.println(imageUrl);
                employee.setEmployeeAvatar(imageUrl);
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

    public String updateEmployeeStatus(String username) {
        employee = employeeFacade.find(username);
        if (employee.getEmployeeStatus() == 1) {
            employee.setEmployeeStatus(2);
            employeeFacade.edit(employee);
        } else {
            employee.setEmployeeStatus(1);
            employeeFacade.edit(employee);
        }
        return "employee_all";
    }

    public String deleteEmployee(String username) {
        employeeFacade.remove(employeeFacade.find(username));
        return "employee_all";
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(int usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //Duy Khanh start
    private String otpCode;

    @NotEmpty(message = "Please enter Email")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is invalidate. Ex: info@123.gmail.com")
    private String email;

    @NotEmpty(message = "Please enter OTP code")
    private String optConf;
    private boolean valid = false;

    public void sendOTP() {
        if (employeeFacade.checkEmailExist(email)) {
            otpCode = Math.round(Math.random() * 1000000) + "";
            uname = email;
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
            message = "";
            valid = false;
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from, displayName));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Biolife Minimart");
                message.setContent("Your OTP code is: <h1>" + otpCode + "</h1>", "text/html");

                Transport.send(message);
                System.out.println("Your OTP code is :<h1>" + otpCode + "</h1>");
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("forgot_password_admin.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (MessagingException | UnsupportedEncodingException ex) {
                System.out.println("Loi: " + ex.getMessage());
            }

        } else {
            message = "Email is not exits";
        }
    }

    public void checkOTP() {
        if (otpCode.equals(optConf)) {
            valid = true;
            message = "";
        } else {
            message = "OTP code is incorrect!!!";
        }
    }

    public void forgot_password() throws NoSuchAlgorithmException {
        if (passNew.equals(passConf)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passNew.getBytes());
            byte[] digest = md.digest();
            passNew = DatatypeConverter.printHexBinary(digest);
            employeeFacade.forgot_pass(email, passNew);
            email = "";
            passNew = "";
            passConf = "";
            optConf = "";
            otpCode = "";
            message = "";
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login_admin.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            message = "Confirm Password mus be the same as New Password";
        }
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
    //Duy khanh end

}
