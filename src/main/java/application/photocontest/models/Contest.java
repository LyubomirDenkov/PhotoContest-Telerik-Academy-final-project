package application.photocontest.models;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
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
    @JoinColumn(name = "organizer")
    private Organizer organizer;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

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
            name = "contest_jury",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> jury;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contest_image",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contest_jury_organizers",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "organizer_id"))
    private Set<Organizer> organizersJury;

    public Contest() {
    }

    public Contest(int id, String title, Category category, LocalDateTime startingDate, int phaseOne, int phaseTwo,
                   Organizer organizer, Type type, Phase phase,Boolean isPointsAwarded, Set<User> participants, Set<User> jury,
                   Set<Image> images,Set<Organizer> organizersJury) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.startingDate = startingDate;
        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
        this.organizer = organizer;
        this.type = type;
        this.phase = phase;
        this.isPointsAwarded = isPointsAwarded;
        this.participants = participants;
        this.jury = jury;
        this.images = images;
        this.organizersJury = organizersJury;
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


    public boolean isPointsAwarded() {
        return isPointsAwarded;
    }

    public void setPointsAwarded(boolean pointsAwarded) {
        isPointsAwarded = pointsAwarded;
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

    public Set<Organizer> getOrganizersJury() {
        return organizersJury;
    }

    public void setOrganizersJury(Set<Organizer> organizersJury) {
        this.organizersJury = organizersJury;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contest)) return false;
        Contest contest = (Contest) o;
        return getId() == contest.getId() && getPhaseOne() == contest.getPhaseOne() && getPhaseTwo() == contest.getPhaseTwo()
                && isPointsAwarded() == contest.isPointsAwarded() && getTitle().equals(contest.getTitle()) &&
                getCategory().equals(contest.getCategory()) && getStartingDate().equals(contest.getStartingDate()) && getOrganizer().equals(contest.getOrganizer()) && getType().equals(contest.getType()) && getPhase().equals(contest.getPhase()) && getParticipants().equals(contest.getParticipants()) && getJury().equals(contest.getJury()) && getImages().equals(contest.getImages()) && getOrganizersJury().equals(contest.getOrganizersJury());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getCategory(), getStartingDate(), getPhaseOne(), getPhaseTwo(),
                getOrganizer(), getType(), getPhase(), isPointsAwarded(), getParticipants(), getJury(), getImages(), getOrganizersJury());
    }
}
