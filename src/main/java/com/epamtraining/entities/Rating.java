package com.epamtraining.entities;

import java.io.Serializable;

/**
 * Entity class for StudentCourse table.
 * @author Sergey Bondarenko
 */
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;
    private Account student;
    private Course course;
    private String rating;
    private String comment;

    public Rating(Account student, Course course, String rating, String comment) {
        this.student = student;
        this.course = course;
        this.rating = rating;
        this.comment = comment;
    }

    public Rating(Account student, String rating, String comment) {
        this.student = student;
        this.rating = rating;
        this.comment = comment;
    }

    public Rating() {
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Account getStudent() {
        return student;
    }

    public void setStudent(Account student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Rating rating1 = (Rating) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(getStudent(), rating1.getStudent())
                .append(getCourse(), rating1.getCourse())
                .append(getRating(), rating1.getRating())
                .append(getComment(), rating1.getComment())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(getStudent())
                .append(getCourse())
                .append(getRating())
                .append(getComment())
                .toHashCode();
    }
}
