package application.photocontest.models.dto;


import application.photocontest.models.Image;
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

    @Positive
    private int phaseId;

    private Set<Integer> participants;

    private Set<Integer> jury;

    private Set<Image> images;

    public ContestDto() {
    }

    public ContestDto(String title,  int categoryId, Timestamp startingDate,  int phaseOne,
                      int phaseTwo,  int typeId,  int phaseId, Set<Integer> participants, Set<Integer> jury, Set<Image> images) {
        this.title = title;
        this.categoryId = categoryId;
        this.startingDate = startingDate;
        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
        this.typeId = typeId;
        this.phaseId = phaseId;
        this.participants = participants;
        this.jury = jury;
        this.images = images;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
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

    public Set<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Integer> participants) {
        this.participants = participants;
    }

    public Set<Integer> getJury() {
        return jury;
    }

    public void setJury(Set<Integer> jury) {
        this.jury = jury;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
