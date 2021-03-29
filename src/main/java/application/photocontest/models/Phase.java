package application.photocontest.models;

import javax.persistence.*;

@Entity
@Table(name = "contest_phase")
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phase_id")
    private int id;

    @Column(name = "name")
    private String name;

    public Phase() {
    }


    public Phase(int id, String name) {
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
