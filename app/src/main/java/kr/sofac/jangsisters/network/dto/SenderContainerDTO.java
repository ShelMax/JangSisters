package kr.sofac.jangsisters.network.dto;

/**
 * Created by Maxim on 27.12.2017.
 */

public class SenderContainerDTO {

    String password;
    String email;
    String googleCloudKey;
    String name;
    String id;
    String code;

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
    public SenderContainerDTO(String id, String code) {
        this.id = id;
        this.code = code;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
