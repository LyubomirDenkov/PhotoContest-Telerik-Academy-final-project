package application.photocontest.services;

import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Category;
import application.photocontest.models.Contest;
import application.photocontest.models.Role;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.CategoryRepository;
import application.photocontest.service.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static application.photocontest.Helpers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTests {


    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;



    @Test
    public void getAll_Should_ReturnAllCategories_When_IsCall() {


        List<Category> result = new ArrayList<>();

        when(categoryService.getAll())
                .thenReturn(result);

        categoryService.getAll();

        verify(categoryRepository,times(1)).getAll();

    }


    @Test
    public void getById_Should_ReturnCategory_When_Exist() {

        Category category = createMockCategory();
        User user = createMockUser();
        user.setRoles(Set.of(new Role(2,"organizer")));

        when(categoryRepository.getById(1))
                .thenReturn(category);

        categoryService.getById(user,1);

        Mockito.verify(categoryRepository, Mockito.times(1)).getById(1);

    }

    @Test
    public void create_Should_ThrowException_When_UserIsNotOrganizer(){
        User user = createMockUser();
        Category category = createMockCategory();

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> categoryService.create(user,category));
    }

    @Test
    public void getById_Should_Throw_When_Not_Exist() {


        when(categoryRepository.getById(254)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> categoryRepository.getById(254));
    }


    @Test
    public void create_Should_ThrowException_When_DuplicateName(){
        User organizer = createMockOrganizer();
        Category category = createMockCategory();

        Assertions.assertThrows(DuplicateEntityException.class,
                () -> categoryService.create(organizer,category));
    }

    @Test
    public void create_Should_Create_When_ValidationsOk(){
        User organizer = createMockOrganizer();
        Category category = createMockCategory();

        when(categoryRepository.getByName(category.getName())).thenThrow(EntityNotFoundException.class);

        categoryService.create(organizer,category);

        verify(categoryRepository, times(1)).create(category);

    }
}
