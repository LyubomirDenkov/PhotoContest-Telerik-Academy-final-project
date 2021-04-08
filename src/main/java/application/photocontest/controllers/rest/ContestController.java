package application.photocontest.controllers.rest;


import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.modelmappers.ContestMapper;

import application.photocontest.models.*;
import application.photocontest.models.dto.ContestDto;
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
import java.util.Set;

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

        User user = authenticationHelper.tryGetUser(headers);

        try {
            return contestService.getAll();
        } catch (
                UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

        }
    }

    @GetMapping("/ongoing")
    public List<Contest> getOngoingContests(){
        return contestService.getOngoingContests();
    }


    @ApiOperation(value = "Get by id")
    @GetMapping("/{id}")
    public Contest getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);


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

        User user = authenticationHelper.tryGetUser(headers);
        try {
            Contest contest = contestMapper.fromDto(contestDto, user);
            Set<Integer> jurySet = contestDto.getJury();
            Set<Integer> participantsSet = contestDto.getParticipants();
            return contestService.create(user, contest,jurySet,participantsSet);

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

        User user = authenticationHelper.tryGetUser(headers);

        try {
            Contest contestToUpdate = contestMapper.fromDto(id, contestDto);
            Set<Integer> jurySet = contestDto.getJury();
            Set<Integer> participantsSet = contestDto.getParticipants();
            return contestService.update(user, contestToUpdate,jurySet,participantsSet);
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
    public void addUser(@RequestHeader HttpHeaders headers, @PathVariable int contestId,
                        @PathVariable int userId) {
        User user = authenticationHelper.tryGetUser(headers);

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
    @ApiOperation(value = "Add image to contest")
    @PutMapping("/{contestId}/image/{imageId}")
    public Image addImage(@RequestHeader HttpHeaders headers, @PathVariable int contestId,
                          @PathVariable int imageId) {
        User user = authenticationHelper.tryGetUser(headers);

        try {
           return contestService.addImage(user, contestId, imageId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException | DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @ApiOperation(value = "Rate image")
    @PostMapping("/{contestId}/rating/{imageId}")
    public void rateImage(@RequestHeader HttpHeaders headers, @PathVariable int contestId,
                          @PathVariable int imageId, @Valid @RequestBody RateImageDto rateImageDto) {

        User user = authenticationHelper.tryGetUser(headers);

        try {
            int points = rateImageDto.getPoints();
            String comment = rateImageDto.getComment();
             contestService.rateImage(user,contestId, imageId,points, comment);
        }  catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException | DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
