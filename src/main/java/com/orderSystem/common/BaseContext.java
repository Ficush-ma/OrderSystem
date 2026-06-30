package com.orderSystem.common;

public class BaseContext{
    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    public static int getCurrentUser(){
        return threadLocal.get();
    }
    public static void setCurrentUser(int userId){
        threadLocal.set(userId);
    }
    public static void removeNowUser(){
        threadLocal.remove();
    }
}
