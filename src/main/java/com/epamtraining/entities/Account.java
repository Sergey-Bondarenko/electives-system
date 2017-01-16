package com.epamtraining.entities;

import java.io.Serializable;

/**
 * Entity class for Account table.
 * @author Sergey Bondarenko
 */
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Account id
     */
    private Integer id;
    /**
     * Account name
     */
    private String name;
    /**
     * Account surname
     */
    private String surname;
    /**
     * Account login in system
     */
    private String login;
    /**
     * Account password
     */
    private String password;
    /**
     * Account user type
     */
    private UserType userType;

    public Account() {
    }

    public Account(Integer id, String name, String surname, String login, String password, UserType userType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.userType = userType;
    }

    public Account(Integer id, String name, String surname, UserType userType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", userTypeId=" + userType +
                '}';
    }

}
