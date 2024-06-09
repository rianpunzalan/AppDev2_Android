package com.example.courseproject;


import java.text.DecimalFormat;

public class Course {
    private String course_no, course_name;
    private int max_enrl;
    public static int credits;

    public Course() {
        this.course_no = "Course Number";
        this.course_name = "Course Name";
        this.max_enrl = 0;
        credits = 0;
    }

    public Course(String course_no, String course_name, int max_enrl, int creds) {
        this.course_no = course_no;
        this.course_name = course_name;
        this.max_enrl = max_enrl;
        credits = creds;
    }

    public Course(Course otherCourse) {
        this.course_no = otherCourse.course_no;
        this.course_name = otherCourse.course_name;
        this.max_enrl = otherCourse.max_enrl;
        Course.credits = otherCourse.credits;
    }

    public String getcourse_no() {
        return this.course_no;
    }

    public void setcourse_no(String course_no) {
        this.course_no = course_no;
    }

    public String getcourse_name() {
        return this.course_name;
    }
    public void setcourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getmax_enrl() {
        return this.max_enrl;
    }
    public void setmax_enrl(int max_enrl) {
        this.max_enrl = max_enrl;
    }

    public double CalculateTotalFees() {
        double TotalFees = this.max_enrl * 250.0;
        return TotalFees;
    }

    @Override public String toString(){

        DecimalFormat df = new DecimalFormat("#.##");
        return 	"course_no: " + course_no +
                ", course_name: " + course_name +
                ", max_enrl: " + max_enrl +
                ", Total Course Fees: " + df.format(CalculateTotalFees()) + "$";}
}

