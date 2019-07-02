package com.example.pizzaapp;

public class CartItemClass {

    private int cartId ;
    private int  customerId ;
    private int itemId ;
    private int itemQuantity  ;
    private String itemStatus ;
    private String itemLocation ;

    public CartItemClass(int cartId, int customerId, int itemId, int itemQuantity, String itemStatus, String itemLocation) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.itemStatus = itemStatus;
        this.itemLocation = itemLocation;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }
}
