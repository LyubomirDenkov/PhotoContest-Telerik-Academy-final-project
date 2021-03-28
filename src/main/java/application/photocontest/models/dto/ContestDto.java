package application.photocontest.models.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.Set;

public class ContestDto {

    private static final String CITY_ID_ERROR_MESSAGE = "City Id must be positive.";
    private static final String TITLE_ERROR_MESSAGE = "Contest title must be between 5 and 50 symbols.";
    public static final String CREATOR_ID_ERROR_MESSAGE = "Creator Id must be positive.";


    @NotNull
    @Size(min = 5, max = 50, message = TITLE_ERROR_MESSAGE)
    private String title;

    @Positive(message = CITY_ID_ERROR_MESSAGE)
    private int categoryId;


    private Timestamp phaseOne;

    private Timestamp phaseTwo;

    @Positive(message = CREATOR_ID_ERROR_MESSAGE)
    private int creatorId;

    private Set<Integer> participants;

    private Set<Integer> jury;

    public ContestDto() {
    }

    public ContestDto(String title, int categoryId, Timestamp phaseOne,
                      Timestamp phaseTwo, int creatorId, Set<Integer> participants, Set<Integer> jury) {
        this.title = title;
        this.categoryId = categoryId;
        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
        this.creatorId = creatorId;
        this.participants = participants;
        this.jury = jury;
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

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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
}
