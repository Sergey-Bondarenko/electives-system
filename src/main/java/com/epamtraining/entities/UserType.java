package com.epamtraining.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Entity class for User_Type table.
 * @author Sergey Bondarenko
 */
public class UserType implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String userType;
    private List<Account> accountList;

    public UserType() {
    }

    public UserType(Integer id, String userType) {
        this.id = id;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public UserType setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public UserType setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserType)) {
            return false;
        }
        UserType other = (UserType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", userType='" + userType + '\'' +
                '}';
    }

}
