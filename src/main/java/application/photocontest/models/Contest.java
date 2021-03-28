package application.photocontest.models;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

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
    private Timestamp phaseOne;

    @Column(name = "phase_two")
    private Timestamp phaseTwo;


    @Column(name = "creator")
    private String creator;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contest_jury",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> jury;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contest_participants",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants;



    public Contest() {
    }

    public Contest(int id, String title, Category category, Timestamp phaseOne,
                   Timestamp phaseTwo, String creator, Set<User> jury, Set<User> participants) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
        this.creator = creator;
        this.jury = jury;
        this.participants = participants;
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

    public Timestamp getPhaseOne() {
        return phaseOne;
    }

    public void setPhaseOne(Timestamp phaseOne) {
        this.phaseOne = phaseOne;
    }

    public Timestamp getPhaseTwo() {
        return phaseTwo;
    }

    public void setPhaseTwo(Timestamp phaseTwo) {
        this.phaseTwo = phaseTwo;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Set<User> getJury() {
        return jury;
    }

    public void setJury(Set<User> jury) {
        this.jury = jury;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }
}
