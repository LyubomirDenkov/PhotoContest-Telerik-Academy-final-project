package application.photocontest.controllers.rest;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.modelmappers.UserMapper;
import application.photocontest.models.Contest;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.RegisterDto;
import application.photocontest.models.dto.UpdateUserDto;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<User> getAll(@RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);

        try {
            return userService.getAll(user);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/leaderboard")
    public List<User> getLeaderboard(@RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        try {
            return userService.getLeaderboard(user);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public User getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {

        User user = authenticationHelper.tryGetUser(headers);

        try {
            return userService.getById(user, id);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{userId}/contests")
    public List<Contest> getUserContests(@RequestParam(name = "phase", required = false) Optional<String> phase,
                                         @RequestHeader HttpHeaders headers,
                                         @PathVariable int userId) {
        User user = authenticationHelper.tryGetUser(headers);

        try {
            return userService.getUserContests(user, userId);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(consumes = {"multipart/form-data", "application/json"})
    public User create(@Valid @RequestPart(value = "dto") RegisterDto dto,
                       @RequestParam(name = "file") Optional<MultipartFile> file,
                       @RequestParam(name = "url") Optional<String> url) {

        User user = userMapper.fromDto(dto);

        try {
            return userService.create(user, file, url);
        } catch (DuplicateEntityException | IOException | UnsupportedOperationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data", "application/json"})
    public User update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestPart("dto") UpdateUserDto userDto,
                       @RequestParam(name = "file") Optional<MultipartFile> file,
                       @RequestParam(name = "url") Optional<String> url) {

        User user = authenticationHelper.tryGetUser(headers);
        try {
            User userToUpdate = userMapper.fromDto(id, userDto);
            return userService.update(user, userToUpdate, file, url);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException | DuplicateEntityException | UnsupportedOperationException | IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
