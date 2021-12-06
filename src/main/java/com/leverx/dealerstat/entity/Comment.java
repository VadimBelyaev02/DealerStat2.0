package com.leverx.dealerstat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date creatingDate;

    @Column(name = "approved")
    private Boolean approved;

    @Min(1)
    @Max(5)
    @Column(name = "rate")
    private Double rate;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(Date creatingDate) {
        this.creatingDate = creatingDate;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
