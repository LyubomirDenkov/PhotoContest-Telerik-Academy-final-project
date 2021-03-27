package application.photocontest.enums;

public enum UserRoles {
    USER,ORGANIZER,ADMIN;


    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
