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

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "phase_one")
    private Date phaseOne;

    @Column(name = "phase_two")
    private Time phaseTwo;

    public Contest() {
    }

    public Contest(int id, String title, int categoryId, Date phaseOne, Time phaseTwo) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
