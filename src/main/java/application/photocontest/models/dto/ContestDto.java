package application.photocontest.models.dto;


import application.photocontest.models.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.Set;


public class ContestDto {

    private static final String CATEGORY_ID_ERROR_MESSAGE = "Category Id must be positive.";
    private static final String TITLE_ERROR_MESSAGE = "Contest title must be between 5 and 50 symbols.";

    @NotNull
    @Size(min = 5, max = 50, message = TITLE_ERROR_MESSAGE)
    private String title;

    @Positive(message = CATEGORY_ID_ERROR_MESSAGE)
    private int categoryId;

    private Timestamp startingDate;

    @Positive
    private int phaseOne;

    @Positive
    private int phaseTwo;

    @Positive
    private int typeId;

    private Set<User> participants;

    private Set<User> jury;

    private Set<User> images;

    public ContestDto() {
    }

    public ContestDto(String title, int categoryId, Timestamp startingDate, int phaseOne, int phaseTwo, int typeId, Set<User> participants, Set<User> jury, Set<User> images) {
        this.title = title;
        this.categoryId = categoryId;
        this.startingDate = startingDate;
        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
        this.typeId = typeId;
        this.participants = participants;
        this.jury = jury;
        this.images = images;
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

    public Timestamp getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Timestamp startingDate) {
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


    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
