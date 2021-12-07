package com.leverx.dealerstat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game_objects")
public class GameObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created_at")
    private Date dateOfCreating;

    @Column(name = "updated_at")
    private Date dateOfUpdating;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "gameObject", cascade = CascadeType.ALL)
    private List<Deal> deals;

    @ManyToMany
    @JoinTable(name = "gameobject_game",
               joinColumns = @JoinColumn(name = "game_object_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"))
    private List<Game> games;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDateOfCreating() {
        return dateOfCreating;
    }

    public void setDateOfCreating(Date dateOfCreating) {
        this.dateOfCreating = dateOfCreating;
    }

    public Date getDateOfUpdating() {
        return dateOfUpdating;
    }

    public void setDateOfUpdating(Date dateOfUpdating) {
        this.dateOfUpdating = dateOfUpdating;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }



    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
