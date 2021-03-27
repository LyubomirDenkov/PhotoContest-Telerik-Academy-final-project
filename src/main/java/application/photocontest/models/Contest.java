package application.photocontest.models;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "contest")
public class Contest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contest_id")
    private int id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "phase_one")
    private Date phaseOne;

    @Column(name = "phase_two")
    private Time phaseTwo;

    public Contest() {
    }

    public Contest(int id, String title, Category category, Date phaseOne, Time phaseTwo) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getPhaseOne() {
        return phaseOne;
    }

    public void setPhaseOne(Date phaseOne) {
        this.phaseOne = phaseOne;
    }

    public Time getPhaseTwo() {
        return phaseTwo;
    }

    public void setPhaseTwo(Time phaseTwo) {
        this.phaseTwo = phaseTwo;
    }


}
