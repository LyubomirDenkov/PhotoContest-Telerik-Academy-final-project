package application.photocontest.enums;

public enum UserRanks {
    JUNKIE,ENTHUSIAST,MASTER,DICTATOR;


    @Override
    public String toString() {
        return super.name().toLowerCase();
    }
}
