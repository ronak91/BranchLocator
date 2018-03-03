package com.branchlocator.branchlocator.Pojo;

/**
 * Created by PRINCE on 18/02/2018.
 */

public class Dataset {

    int id;
    String bankname;
    String branchname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }
}
