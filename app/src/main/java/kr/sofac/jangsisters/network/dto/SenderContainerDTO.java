package kr.sofac.jangsisters.network.dto;

import java.util.HashMap;

public class SenderContainerDTO {

    private Integer id;
    private Integer customer_id;
    private String password;
    private String email;
    private String googleCloudKey;
    private String name;
    private String code;
    private HashMap<Integer, Integer> filter;


    /**
     * signInCustomer
     */
    public SenderContainerDTO(String password, String email, String googleCloudKey) {
        this.password = password;
        this.email = email;
        this.googleCloudKey = googleCloudKey;
    }

    /**
     * signUpCustomer
     */
    public SenderContainerDTO(String password, String email, String name, String googleCloudKey) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.googleCloudKey = googleCloudKey;
    }

    /**
     * signUpCustomerVerification
     */
    public SenderContainerDTO(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    /**
     * getListPosts
     */
    public SenderContainerDTO(HashMap<Integer, Integer> filter) {
        this.filter = filter;
    }

    /**
     * getPost
     */
    public SenderContainerDTO(Integer id, Integer customer_id) {
        this.id = id;
        this.customer_id = customer_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogleCloudKey() {
        return googleCloudKey;
    }

    public void setGoogleCloudKey(String googleCloudKey) {
        this.googleCloudKey = googleCloudKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SenderContainerDTO{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", googleCloudKey='" + googleCloudKey + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
