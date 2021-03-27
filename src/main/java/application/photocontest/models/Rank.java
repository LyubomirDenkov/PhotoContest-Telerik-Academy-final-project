package application.photocontest.models;

import javax.persistence.*;

@Entity
@Table(name = "ranks")
public class Rank {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private int id;

    @Column(name = "name")
    private String name;

    public Rank() {
    }

    public Rank(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
