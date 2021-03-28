package application.photocontest.controllers.rest;


import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Contest;
import application.photocontest.models.User;
import application.photocontest.service.contracts.ContestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/contests")
public class ContestController {

    private final ContestService contestService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public ContestController(ContestService contestService, AuthenticationHelper authenticationHelper) {
        this.contestService = contestService;
        this.authenticationHelper = authenticationHelper;
    }

    @ApiOperation(value = "Get all contests")
    @GetMapping
    public List<Contest> getAll(@RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);

        try {
            return contestService.getAll(user);
        } catch (
                UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

        }
    }


        @ApiOperation(value = "Get by id")
        @GetMapping("/{id}")
        public Contest getById (@RequestHeader HttpHeaders headers,@PathVariable int id){
            User user = authenticationHelper.tryGetUser(headers);
            try {
                return contestService.getById(user, id);
            } catch (
                    UnauthorizedOperationException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

            }
        }
    }
