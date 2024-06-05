package com.example.facultymobileproject;



public class Faculty implements Comparable<Faculty>{
    private String f_Id;
    private String f_Lname;
    private String f_Fname;
    private double f_Salary;
    private double f_bonusRate;

    public Faculty() {
        this.f_Id = "ID";
        this.f_Lname ="Lname";
        this.f_Fname ="Fname";
        this.f_Salary = 0;
        this.f_bonusRate = 0;
    }

    public Faculty(String f_Id,String f_Lname,String f_Fname,double f_Salary,double f_bonusRate) {
        this.f_Id = f_Id;
        this.f_Lname =f_Lname;
        this.f_Fname =f_Fname;
        this.f_Salary = f_Salary;
        this.f_bonusRate = f_bonusRate;
    }

    public Faculty(Faculty otherFaculty) {
        this.f_Id = otherFaculty.f_Id;
        this.f_Lname =otherFaculty.f_Lname;
        this.f_Fname =otherFaculty.f_Fname;
        this.f_Salary = otherFaculty.f_Salary;
        this.f_bonusRate = otherFaculty.f_bonusRate;
    }


    public String getF_Id() {
        return f_Id;
    }

    public void setF_Id(String f_Id) {
        this.f_Id = f_Id;
    }

    public String getF_Lname() {
        return f_Lname;
    }

    public void setF_Lname(String f_Lname) {
        this.f_Lname = f_Lname;
    }

    public String getF_Fname() {
        return f_Fname;
    }

    public void setF_Fname(String f_Fname) {
        this.f_Fname = f_Fname;
    }


    public double getF_Salary() {
        return f_Salary;
    }

    public void setF_Salary(double f_Salary) {
        this.f_Salary = f_Salary;
    }

    public double getF_bonusRate() {
        return f_bonusRate;
    }

    public void setF_bonusRate(double f_bonusRate) {
        this.f_bonusRate = f_bonusRate;
    }

    public double calculate_Bonus() {
        return this.f_Salary * f_bonusRate/100;
    }

    @Override public int compareTo(Faculty faculty){

        return (int)(this.calculate_Bonus() - faculty.calculate_Bonus());

    }

    @Override
    public String toString() {
        return "Faculty{" +
                "f_Id='" + f_Id + '\'' +
                ", f_Lname='" + f_Lname + '\'' +
                ", f_Fname='" + f_Fname + '\'' +
                ", f_Salary=" + f_Salary +
                ", f_bonusRate=" + f_bonusRate +
                '}';
    }
}
