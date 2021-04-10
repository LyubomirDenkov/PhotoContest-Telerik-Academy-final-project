package application.photocontest.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contest_type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int id;

    @Column(name = "name")
    private String name;

    public Type() {
    }

    public Type(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return getId() == type.getId() && getName().equals(type.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
