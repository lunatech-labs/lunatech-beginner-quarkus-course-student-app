package com.lunatech.training.quarkus;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SubjectBean {

    public SubjectBean() {
        System.out.println("SubjectBean Constructed!");
    }

    public String subject() {
        return "everyone";
    }

}
