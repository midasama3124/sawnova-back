package com.mmanchola.sawnovaback.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserService {

    @Value("${modes.testing}")
    private boolean testingMode;
    private List<String> admins;

    public List<String> getAdmins() {
        return this.admins;
    }

    // Init some users for testing
    @PostConstruct
    private void initDataForTesting() {
        admins = new ArrayList<>();

        String user1 = "midasama3124@gmail.com";
        String user2 = "rcabrera@sawnova.com";
        String user3 = "ymachado@sawnova.com";
        String user4 = "ycampo@sawnova.com";
        String user5 = "jcruz@sawnova.com";
        String user6 = "sales@sawnova.com";

        if (testingMode) {
            admins.add(user1);
        } else {
            admins.add(user2);
            admins.add(user3);
            admins.add(user4);
            admins.add(user5);
            admins.add(user6);
        }
    }
}
