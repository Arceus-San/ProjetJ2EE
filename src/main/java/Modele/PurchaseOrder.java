/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author Areus-San
 */
class PurchaseOrder {
    
    private int order_num;
    private int customer_id;
    private int product_id;
    private int quantity;
    private float shipping_cost;
    private String sales_date;
    private String shipping_date;
    private String freight_company;
    
    public PurchaseOrder(int ordernum, int customid, int prodid, int qt, float shipcost, String saledate, String shippingdate, String freightcompany){
        this.order_num=ordernum;
        this.customer_id=customid;
        this.product_id=prodid;
        this.quantity=qt;
        this.shipping_cost=shipcost;
        this.sales_date=saledate;
        this.shipping_date=shippingdate;
        this.freight_company=freightcompany;
    }
    
    public int getOrderNum(){
        return this.order_num;
    }
    
    public int getCustomerId(){
        return this.customer_id;
    }
    
    public int getProductId(){
        return this.product_id;
    }
    
    public int getQuantity(){
        return this.quantity;
    }
    
    public float getShippingCost(){
        return this.shipping_cost;
    }
    
    public String getSalesDate(){
        return this.sales_date;
    }
    
    public String getShippingDate(){
        return this.shipping_date;
    }
    
    public String getFreightCompany(){
        return this.freight_company;
    }
    
}
