package com.epamtraining.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Entity class for Status table.
 * @author Sergey Bondarenko
 */
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String courseStatus;
    private List<Course> courseList;

    public Status() {
    }

    public Status(Integer id, String courseStatus) {
        this.id = id;
        this.courseStatus = courseStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Status)) {
            return false;
        }
        Status other = (Status) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", courseStatus='" + courseStatus + '\'' +
                '}';
    }

}
