package application.photocontest.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mailbox")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private String date;

    @Column(name = "is_seen")
    private boolean isSeen;

    public Message() {
    }

    public Message(int id, String title, String message, String date, boolean isSeen) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.date = date;
        this.isSeen = isSeen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return getId() == message1.getId() && isSeen() == message1.isSeen() && getTitle().equals(message1.getTitle()) && getMessage().equals(message1.getMessage()) && getDate().equals(message1.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getMessage(), getDate(), isSeen());
    }
}
