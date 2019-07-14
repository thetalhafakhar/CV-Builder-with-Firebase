package com.example.talhafakhar.cvonline;

public class CV_attributes {
    public CV_attributes() {
    }
    String cv_id;
    String name;
    String fname;
    String age;
    String dob;
    String school;
    String clg;
    String uni;
    String edu;
    String pjob;
    String expy;
    String job;
    String field;
    String achi;
    String awards;
    String certificate;
    public CV_attributes(String name, String fname, String age, String dob, String school, String clg, String uni, String edu, String pjob, String expy, String job, String field, String achi, String awards, String certificate) {
        this.name = name;
        this.fname = fname;
        this.age = age;
        this.dob = dob;
        this.school = school;
        this.clg = clg;
        this.uni = uni;
        this.edu = edu;
        this.pjob = pjob;
        this.expy = expy;
        this.job = job;
        this.field = field;
        this.achi = achi;
        this.awards = awards;
        this.certificate = certificate;
    }
    public void set_cv_id(String id)
    {
        this.cv_id= id;
    }



}
