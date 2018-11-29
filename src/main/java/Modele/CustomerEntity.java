/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author Arceus-San
 */
public class CustomerEntity {
    
    private int id;
    private String discount_code;
    private int zip;
    private String name;
    private String adress1;
    private String adress2;
    private String city;
    private String state;
    private String phone;
    private String fax;
    private String email;
    private int credit_limit;
    
    
    
    public CustomerEntity(int custid, String discode, int codepost,String nom, String adresse1, String adresse2, String ville, String etat,String telephone,String faxe,String mail, int credit){
        this.id=custid;
        this.discount_code=discode;
        this.zip=codepost;
        this.name=nom;
        this.adress1=adresse1;
        this.adress2=adresse2;
        this.city=ville;
        this.state=etat;
        this.phone=telephone;
        this.fax=faxe;
        this.email=mail;
        this.credit_limit=credit;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getZIP(){
        return this.zip;
    }
    
    public int getCreditLimit(){
        return this.credit_limit;
    }
    
    public String getDiscountCode(){
        return this.discount_code;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getAdress1(){
        return this.adress1;
    }
    
    public String getAdress2(){
        return this.adress2;
    }
    
    public String getVille(){
        return this.city;
    }
    
    public String getSate(){
        return this.state;
    }
    
    public String gePhone(){
        return this.phone;
    }
    
    public String getFax(){
        return this.fax;
    }
    
    public String getEmail(){
        return this.email;
    }
    
}
