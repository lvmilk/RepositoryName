/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.Serializable;
import javax.faces.view.ViewScoped;

/**
 *
 * @author LI HAO
 */
@ViewScoped
public class msgHelper implements Serializable{
    private int msgCount;
    
    public msgHelper()
    {
        
    }

    /**
     * @return the msgCount
     */
    public int getMsgCount() {
        return msgCount;
    }

    /**
     * @param msgCount the msgCount to set
     */
    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }
    
}
