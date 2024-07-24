/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

/**
 *
 * @author ADMIN
 */
public class ChangePassError {
    
    private String oldPasswordError;
    private String newPasswordError;
    private String confirmError;

    public ChangePassError() {
    }

    public ChangePassError(String oldPasswordError, String newPasswordError, String confirmError) {
        this.oldPasswordError = oldPasswordError;
        this.newPasswordError = newPasswordError;
        this.confirmError = confirmError;
    }

    public String getOldPasswordError() {
        return oldPasswordError;
    }

    public void setOldPasswordError(String oldPasswordError) {
        this.oldPasswordError = oldPasswordError;
    }

    public String getNewPasswordError() {
        return newPasswordError;
    }

    public void setNewPasswordError(String newPasswordError) {
        this.newPasswordError = newPasswordError;
    }

    public String getConfirmError() {
        return confirmError;
    }

    public void setConfirmError(String confirmError) {
        this.confirmError = confirmError;
    }
    
    
}
