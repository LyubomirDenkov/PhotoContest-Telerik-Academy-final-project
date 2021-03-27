package application.photocontest.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.util.Date;

public class ContestDto {

    public static final String CITY_ID_ERROR_MESSAGE = "City Id must be positive.";
    public static final String TITLE_ERROR_MESSAGE = "Contest title must be between 5 and 50 symbols.";


    @NotNull
    @Size(min = 5, max = 50, message = TITLE_ERROR_MESSAGE)
    private String title;

    @Positive(message = CITY_ID_ERROR_MESSAGE)
    private int categoryId;


    private Date phaseOne;

    private Time phaseTwo;

    public ContestDto() {
    }

    public ContestDto(String title, int categoryId, Date phaseOne, Time phaseTwo) {
        this.title = title;
        this.categoryId = categoryId;
        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
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
