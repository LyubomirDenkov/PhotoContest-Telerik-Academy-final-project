package application.photocontest.enums;

public enum ContestTypes {

    OPEN, INVITATIONAL;

    @Override
    public String toString() {
        return super.name().toLowerCase();
    }
}
