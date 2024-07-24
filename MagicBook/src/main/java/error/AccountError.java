/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

/**
 *
 * @author Asus
 */
public class AccountError {
    private String firstNameError;
    private String lastNameError;
    private String emailError;
    private String phoneError;
    private String dobError;
    private String passwordError;
    private String confirmPasswordError;
    

    public AccountError() {
    }

    public AccountError(String firstNameError, String lastNameError, String emailError, String phoneError, String dobError, String passwordError, String confirmPasswordError) {
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.emailError = emailError;
        this.phoneError = phoneError;
        this.dobError = dobError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getFirstNameError() {
        return firstNameError;
    }

    public void setFirstNameError(String firstNameError) {
        this.firstNameError = firstNameError;
    }

    public String getLastNameError() {
        return lastNameError;
    }

    public void setLastNameError(String lastNameError) {
        this.lastNameError = lastNameError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getDobError() {
        return dobError;
    }

    public void setDobError(String dobError) {
        this.dobError = dobError;
    }

    public String getConfirmPasswordError() {
        return confirmPasswordError;
    }

    public void setConfirmPasswordError(String confirmPasswordError) {
        this.confirmPasswordError = confirmPasswordError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }



    
}
