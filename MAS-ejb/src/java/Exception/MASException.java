/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author victor
 */
public class MASException extends Exception {
    private String errorCode;
    
    public MASException(){
    
    }
    public MASException(String errorCode){
        this.setErrorCode(errorCode);
    }
    
    /*Error Code first 
    *AT: AircraftType
    */

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    

    
}
