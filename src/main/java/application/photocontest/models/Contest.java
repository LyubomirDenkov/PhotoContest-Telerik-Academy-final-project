package application.photocontest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

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

    @Column(name = "first_phase")
    private Date timeTillVoting;

    @Column(name = "second_phase")
    private Date timeTillFinished;

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


    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contest_participants",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants;

    @OneToMany(fetch = FetchType.EAGER)
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contest_winner_images",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> winnerImages;

    @JsonIgnore
    @Column(name = "is_jury")
    private boolean isJury;

    @JsonIgnore
    @Column(name = "is_participant")
    private boolean isParticipant;

    @JsonIgnore
    @Column(name = "has_image_uploaded")
    private boolean hasImageUploaded;

    public Contest() {
    }

    public Contest(int id,
                   String title,
                   Category category,
                   Date timeTillVoting,
                   Date timeTillFinished,
                   User user,
                   Type type,
                   String backgroundImage,
                   Phase phase,
                   Set<User> participants,
                   Set<User> jury,
                   Set<Image> images,
                   boolean isJury,
                   boolean isParticipant,
                   boolean hasImageUploaded) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.timeTillVoting = timeTillVoting;
        this.timeTillFinished = timeTillFinished;
        this.user = user;
        this.type = type;
        this.backgroundImage = backgroundImage;
        this.phase = phase;
        this.participants = participants;
        this.jury = jury;
        this.images = images;
        this.isJury = isJury;
        this.isParticipant = isParticipant;
        this.hasImageUploaded = hasImageUploaded;
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

    public Date getTimeTillVoting() {
        return timeTillVoting;
    }

    public void setTimeTillVoting(Date timeTillVoting) {
        this.timeTillVoting = timeTillVoting;
    }

    public Date getTimeTillFinished() {
        return timeTillFinished;
    }

    public void setTimeTillFinished(Date timeTillFinished) {
        this.timeTillFinished = timeTillFinished;
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

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
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

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<Image> getWinnerImages() {
        return winnerImages;
    }

    public void setWinnerImages(Set<Image> winnerImages) {
        this.winnerImages = winnerImages;
    }

    @JsonIgnore
    public boolean isJury() {
        return isJury;
    }

    public void setIsJury(boolean userIsJury) {
        this.isJury = userIsJury;
    }

    @JsonIgnore
    public boolean isParticipant() {
        return isParticipant;
    }

    public void setParticipant(boolean participant) {
        isParticipant = participant;
    }

    @JsonIgnore
    public boolean isHasImageUploaded() {
        return hasImageUploaded;
    }

    public void setHasImageUploaded(boolean hasImageUploaded) {
        this.hasImageUploaded = hasImageUploaded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contest contest = (Contest) o;
        return getId() == contest.getId() && isJury() == contest.isJury() && isParticipant() == contest.isParticipant() && isHasImageUploaded() == contest.isHasImageUploaded() && getTitle().equals(contest.getTitle()) && getCategory().equals(contest.getCategory()) && getTimeTillVoting().equals(contest.getTimeTillVoting()) && getTimeTillFinished().equals(contest.getTimeTillFinished()) && getUser().equals(contest.getUser()) && getType().equals(contest.getType()) && getBackgroundImage().equals(contest.getBackgroundImage()) && getPhase().equals(contest.getPhase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getCategory(), getTimeTillVoting(), getTimeTillFinished(), getUser(), getType(), getBackgroundImage(), getPhase(), isJury(), isParticipant(), isHasImageUploaded());
    }
}