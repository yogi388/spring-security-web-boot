package com.example.customLogOutHandler.services;

import com.example.customLogOutHandler.user.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * created by Atiye Mousavi
 * Date: 10/1/2021
 * Time: 1:55 PM
 */
@Service
public class UserCache {

    @PersistenceContext
    private EntityManager entityManager;

    //این یک کش پنهان است برای ذخیره کاربران
    private final ConcurrentMap<String, User> store=new ConcurrentHashMap<>(256);

    public User getByUserName(String userName){
        //یوزر ها از دیتابیس گرفته میشود و در store قرار میدهد
        return store.computeIfAbsent(userName,k ->entityManager.createQuery("from User where login=:login",User.class)
        .setParameter("login",k)
        .getSingleResult());
    }
    public void evictuser(String userName){
        //برای بیرون کردن یک یوزر از همان کش پنهان
        store.remove(userName);
    }
    public int size(){
        return store.size();
    }

}
