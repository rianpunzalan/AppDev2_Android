package com.example.facultymobileproject;



public class Faculty implements Comparable<Faculty>{
    private String f_Id;
    private String f_Lname;
    private String f_Fname;
    private String f_zipcodeBirth;
    private double f_Salary;
    private double f_bonusRate;

    public Faculty() {
        this.f_Id = "ID";
        this.f_Lname ="Lname";
        this.f_Fname ="Fname";
        this.f_zipcodeBirth = "0000";
        this.f_Salary = 0;
        this.f_bonusRate = 0;
    }

    public Faculty(String f_Id,String f_Lname,String f_Fname,String f_zipcodeBirth,double f_Salary,double f_bonusRate) {
        this.f_Id = f_Id;
        this.f_Lname =f_Lname;
        this.f_Fname =f_Fname;
        this.f_zipcodeBirth = f_zipcodeBirth;
        this.f_Salary = f_Salary;
        this.f_bonusRate = f_bonusRate;
    }

    public Faculty(Faculty otherFaculty) {
        this.f_Id = otherFaculty.f_Id;
        this.f_Lname =otherFaculty.f_Lname;
        this.f_Fname =otherFaculty.f_Fname;
        this.f_zipcodeBirth = otherFaculty.f_zipcodeBirth;
        this.f_Salary = otherFaculty.f_Salary;
        this.f_bonusRate = otherFaculty.f_bonusRate;
    }



    public String getf_Lname() {
        return this.f_Lname;
    }

    public String getf_Fname() {
        return this.f_Fname;
    }

    public double getbonusRate() {
        return this.f_bonusRate;
    }

    public String getf_Id() {
        return this.f_Id;
    }


    public double getf_Salary() {
        return this.f_Salary;
    }


    public String getf_zipcodeBirth() {
        return this.f_zipcodeBirth;
    }


    public double calculate_Bonus() {
        return this.f_Salary * f_bonusRate/100;
    }

    @Override public int compareTo(Faculty faculty){

        return (int)(this.calculate_Bonus() - faculty.calculate_Bonus());

    }

}

