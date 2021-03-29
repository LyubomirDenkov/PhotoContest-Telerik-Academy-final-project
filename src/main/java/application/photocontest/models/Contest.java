package application.photocontest.models;

import javax.persistence.*;

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

    @Column(name = "starting_date")
    private Date startingDate;

    @Column(name = "phase1_days")
    private int phaseOne;

    @Column(name = "phase2_hours")
    private int phaseTwo;

    @ManyToOne
    @JoinColumn(name = "organizer")
    private Organizer organizer;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "phase_id")
    private Phase phase;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contest_participants",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contest_jury",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> jury;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contest_image",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<User> images;


    public Contest() {
    }

    public Contest(int id, String title, Category category, int phaseOne, int phaseTwo,
                   Organizer organizer, Set<User> participants, Set<User> jury, Set<User> images) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
        this.organizer = organizer;
        this.participants = participants;
        this.jury = jury;
        this.images = images;
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

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public int getPhaseOne() {
        return phaseOne;
    }

    public void setPhaseOne(int phaseOne) {
        this.phaseOne = phaseOne;
    }

    public int getPhaseTwo() {
        return phaseTwo;
    }

    public void setPhaseTwo(int phaseTwo) {
        this.phaseTwo = phaseTwo;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public Set<User> getJury() {
        return jury;
    }

    public void setJury(Set<User> jury) {
        this.jury = jury;
    }

    public Set<User> getImages() {
        return images;
    }

    public void setImages(Set<User> images) {
        this.images = images;
    }
}
