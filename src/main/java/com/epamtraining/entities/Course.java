package com.epamtraining.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Entity class for Course table.
 * @author Sergey Bondarenko
 */
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String description;
    private List<Rating> ratingList;
    private Status status;
    private Account teacher;
    private Integer listeners;
    private Integer maxListeners;

    public Course() {
    }

    public Course(Integer id, String name, String description, Status status, Account teacher, Integer listeners, Integer maxListeners) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.teacher = teacher;
        this.listeners = listeners;
        this.maxListeners = maxListeners;
    }

    public Course(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public void setTitle(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public void addCourseStudent(Account account) {
        //ratingList.add(account);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Account getTeacher() {
        return teacher;
    }

    public void setTeacher(Account teacher) {
        this.teacher = teacher;
    }

    public Integer getListeners() {
        return listeners;
    }

    public void setListeners(Integer listeners) {
        this.listeners = listeners;
    }

    public Integer getMaxListeners() {
        return maxListeners;
    }

    public void setMaxListeners(Integer maxListeners) {
        this.maxListeners = maxListeners;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", teacher=" + teacher +
                '}';
    }

}
