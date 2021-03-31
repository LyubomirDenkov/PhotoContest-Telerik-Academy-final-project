package application.photocontest.controllers.rest;


import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.modelmappers.ContestMapper;

import application.photocontest.models.Contest;
import application.photocontest.models.Organizer;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.models.dto.ImageDto;
import application.photocontest.models.dto.RateImageDto;
import application.photocontest.service.contracts.ContestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

@RestController
@RequestMapping("/api/contests")
public class ContestController {

    private final ContestService contestService;
    private final AuthenticationHelper authenticationHelper;
    private final ContestMapper contestMapper;

    @Autowired
    public ContestController(ContestService contestService, AuthenticationHelper authenticationHelper, ContestMapper contestMapper) {
        this.contestService = contestService;
        this.authenticationHelper = authenticationHelper;
        this.contestMapper = contestMapper;
    }

    @ApiOperation(value = "Get all contests")
    @GetMapping
    public List<Contest> getAll(@RequestHeader HttpHeaders headers) {
        UserCredentials user = authenticationHelper.tryGetUser(headers);

        try {
            return contestService.getAll(user);
        } catch (
                UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

        }
    }


    @ApiOperation(value = "Get by id")
    @GetMapping("/{id}")
    public Contest getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        UserCredentials user = authenticationHelper.tryGetUser(headers);


        try {
            return contestService.getById(user, id);
        } catch (
                UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

        }
    }

    @ApiOperation(value = "Create contest")
    @PostMapping
    public Contest create(@RequestHeader HttpHeaders headers,
                          @Valid @RequestBody ContestDto contestDto) {

        Organizer organizer = authenticationHelper.tryGetOrganizer(headers);

        try {
            Contest contest = contestMapper.fromDto(contestDto, organizer);
            return contestService.create(organizer, contest);

        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @ApiOperation(value = "Update contest")
    @PutMapping("/{id}")
    public Contest update(@RequestHeader HttpHeaders headers, @PathVariable int id,
                          @Valid @RequestBody ContestDto contestDto) {

        Organizer organizer = authenticationHelper.tryGetOrganizer(headers);

        try {
            Contest contestToUpdate = contestMapper.fromDto(id, contestDto);
            return contestService.update(organizer, contestToUpdate,contestDto);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException | DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @ApiOperation(value = "Add user to contest")
    @PutMapping("/{contestId}/user/{userId}")
    public void addUser(@RequestHeader HttpHeaders headers, @PathVariable int contestId, @PathVariable int userId) {
        UserCredentials user = authenticationHelper.tryGetUser(headers);

        try {
            contestService.addUserToContest(user, contestId, userId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException | DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }


    }

    @ApiOperation(value = "Rate image")
    @PutMapping("/{contestId}/image/{imageId}")
    public void rateImage(@RequestHeader HttpHeaders headers, @PathVariable int contestId,
                          @PathVariable int imageId, @Valid @RequestBody RateImageDto rateImageDto) {
        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);

        try {
            int points = rateImageDto.getPoints();
             contestService.rateImage(userCredentials,contestId, imageId,points);
        }  catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException | DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
