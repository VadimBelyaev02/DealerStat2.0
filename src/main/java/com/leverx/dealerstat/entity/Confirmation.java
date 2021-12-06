package com.leverx.dealerstat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "confirmations")
public class Confirmation extends BaseEntity{

    @Column(name = "hash_code")
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration_time")
    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
