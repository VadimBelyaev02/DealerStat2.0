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
@Table(name = "deals")
public class Deal extends BaseEntity{

    @Column(name = "date")
    private Date date;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "from_id")
    private User fromUser;

    @ManyToOne()
    @JoinColumn(name = "object_id")
    private GameObject gameObject;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "to_id")
    private User toUser;

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
