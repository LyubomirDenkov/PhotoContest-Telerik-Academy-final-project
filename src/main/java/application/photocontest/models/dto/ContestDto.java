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

    private Timestamp starting_date;

    @Positive
    private int phaseOne;

    @Positive
    private int phaseTwo;

    @Positive
    private int organizerId;

    @Positive
    private int typeId;

    @Positive
    private int phaseId;

    private Set<User> participants;

    private Set<User> jury;

    private Set<User> images;

    public ContestDto() {
    }

    public ContestDto(String title,  int categoryId, Timestamp starting_date,  int phaseOne,  int phaseTwo,  int organizerId,  int typeId,  int phaseId, Set<User> participants, Set<User> jury, Set<User> images) {
        this.title = title;
        this.categoryId = categoryId;
        this.starting_date = starting_date;
        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
        this.organizerId = organizerId;
        this.typeId = typeId;
        this.phaseId = phaseId;
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

    public Timestamp getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(Timestamp starting_date) {
        this.starting_date = starting_date;
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

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
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
