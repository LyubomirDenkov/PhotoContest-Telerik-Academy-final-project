package application.photocontest.controllers.rest;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.modelmappers.OrganizerMapper;
import application.photocontest.models.Organizer;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.RegisterDto;
import application.photocontest.models.dto.UpdateUserDto;
import application.photocontest.service.contracts.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {


    private final OrganizerService organizeService;
    private final AuthenticationHelper authenticationHelper;
    private final OrganizerMapper organizerMapper;

    @Autowired
    public OrganizerController(OrganizerService organizerService, AuthenticationHelper authenticationHelper, OrganizerMapper organizerMapper) {
        this.organizeService = organizerService;
        this.authenticationHelper = authenticationHelper;
        this.organizerMapper = organizerMapper;
    }


    @GetMapping
    public List<Organizer> getAll(@RequestHeader HttpHeaders headers) {

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);

        return organizeService.getAll(userCredentials);
    }

    @GetMapping("/{id}")
    public Organizer getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);

        try {
            return organizeService.getById(userCredentials, id);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping
    public Organizer create(@RequestHeader HttpHeaders headers, @Valid @RequestBody RegisterDto dto) {

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);
        Organizer organizer = organizerMapper.fromDto(dto);

        try {
            return organizeService.create(userCredentials, organizer);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Organizer update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody UpdateUserDto userDto) {

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);

        try {
            Organizer organizerToUpdate = organizerMapper.fromDto(id, userDto);
            return organizeService.update(userCredentials, organizerToUpdate);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException | DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);

        try {
            organizeService.delete(userCredentials, id);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
