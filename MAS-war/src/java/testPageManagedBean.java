/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author wang
 */
@Named(value = "testPageManagedBean")
@ViewScoped
public class testPageManagedBean implements Serializable{
//private Map<String, Boolean> testMap=new HashMap<String, Boolean>();
private List<Integer> testMap=new ArrayList< Integer>();
//private Boolean flag;
private Integer flag=0;
private String inputString;
private Boolean test;
private String testStr = new String("asdfasdfasdf");
private String testStr2 = new String("qwerqwerqwe");

@PostConstruct

public void init(){
    testMap.add(0);
    testMap.add(1);
    
}

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    public String getTestStr2() {
        return testStr2;
    }

    public void setTestStr2(String testStr2) {
        this.testStr2 = testStr2;
    }

    public String getInputString() {
        return inputString;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void setInputString(String inputString) {
        this.inputString = inputString;
    }


    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public List<Integer> getTestMap() {
        return testMap;
    }

    public void setTestMap(List<Integer> testMap) {
        this.testMap = testMap;
    }


    public Integer getFlag() {
          System.out.println("getFlag: "+flag);
        return flag;
    }

    public void setFlag(Integer fg) {
         System.out.println("setFlag: "+fg);
        this.flag = fg;
    }
    

      public void onSelect(){
          System.out.println("Flag: "+this.getFlag());
          if (flag==null||flag==0)
              test=false;
          else 
              test=true;
          System.out.println("render: "+test);
    }
     
    /**
     * Creates a new instance of testPageManagedBean
     */
    public testPageManagedBean() {
    }
    
}
