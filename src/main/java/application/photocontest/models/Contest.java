package application.photocontest.models;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
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
    private LocalDateTime startingDate;

    @Column(name = "phase1_days")
    private int phaseOne;

    @Column(name = "phase2_hours")
    private int phaseTwo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @Column(name = "image_url")
    private String backgroundImage;

    @ManyToOne
    @JoinColumn(name = "phase_id")
    private Phase phase;

    @Column(name = "isPointsAwarded")
    private boolean isPointsAwarded;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contest_participants",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "jury_users",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> jury;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contest_image",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images;


    public Contest() {
    }

    public Contest(int id, String title, Category category,
                   LocalDateTime startingDate, int phaseOne,
                   int phaseTwo, User user, Type type,
                   Phase phase, boolean isPointsAwarded, String backgroundImage,
                   Set<User> participants, Set<User> jury,
                   Set<Image> images) {

        this.id = id;
        this.title = title;
        this.category = category;
        this.startingDate = startingDate;
        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
        this.user = user;
        this.type = type;
        this.phase = phase;
        this.isPointsAwarded = isPointsAwarded;
        this.backgroundImage = backgroundImage;
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

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public boolean isPointsAwarded() {
        return isPointsAwarded;
    }

    public void setPointsAwarded(boolean pointsAwarded) {
        isPointsAwarded = pointsAwarded;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String imageUrl) {
        this.backgroundImage = imageUrl;
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

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

}
