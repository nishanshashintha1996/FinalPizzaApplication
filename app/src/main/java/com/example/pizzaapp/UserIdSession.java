package com.example.pizzaapp;

public class UserIdSession {

    private static String usId;
    private static String userName;
    private static String ipAdress;
    private static int itemId;

    public static int getItemId() {
        return itemId;
    }

    public static void setItemId(int itemId) {
        UserIdSession.itemId = itemId;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserIdSession.userName = userName;
    }

    public static String getIpAdress() {
        return ipAdress;
    }

    public static void setIpAdress(String ipAdress) {
        UserIdSession.ipAdress = ipAdress;
    }



    public static String getUsId() {
        return usId;
    }

    public static void setUsId(String usId) {
        UserIdSession.usId = usId;
    }
}
