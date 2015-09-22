/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Xu
 */
public class ExistException extends Exception {
    public ExistException(){}
    
    public ExistException(String message){
        super(message);
    }
}