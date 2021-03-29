package application.photocontest.enums;

public enum UserRoles {
    USER,ORGANIZER;


    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
