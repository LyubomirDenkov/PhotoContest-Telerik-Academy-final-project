package application.photocontest.repository.contracts;

import application.photocontest.models.Type;

import java.util.List;

public interface TypeRepository {

    List<Type> getAll();

    Type getById(int id);
}
