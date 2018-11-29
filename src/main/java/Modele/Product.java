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
class Product {
    
    private int id;
    private int manuf_id;
    private String prod_code;
    private float cost;
    private int quantity;
    private float markup;
    private String available;
    private String description;
    
    
    public Product(int idprod, int manuf, String prodcode, float prix, int quantite, float marge, String dispo, String desc){
        this.id=idprod;
        this.manuf_id=manuf;
        this.prod_code=prodcode;
        this.cost=prix;
        this.quantity=quantite;
        this.markup=marge;
        this.available=dispo;
        this.description=desc;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getManufId(){
        return this.manuf_id;
    }
    
    public String getProdCode(){
        return this.prod_code;
    }
    
    public float getCost(){
        return this.cost;
    }
    
    public int getQuantity(){
        return this.quantity;
    }
    
    public float getMarkup(){
        return this.markup;
    }
    
    public String getAvailable(){
        return this.available;
    }
    
    public String getDescription(){
        return this.description;
    }
    
}
