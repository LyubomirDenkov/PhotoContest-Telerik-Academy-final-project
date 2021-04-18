package application.photocontest.controllers.rest;


import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.modelmappers.ContestMapper;

import application.photocontest.modelmappers.ImageMapper;
import application.photocontest.modelmappers.ImageReviewMapper;
import application.photocontest.models.*;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.models.dto.ImageDto;
import application.photocontest.models.dto.ImageReviewDto;
import application.photocontest.service.contracts.ContestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/contests")
public class ContestController {

    private final ContestService contestService;
    private final AuthenticationHelper authenticationHelper;
    private final ContestMapper contestMapper;
    private final ImageMapper imageMapper;
    private final ImageReviewMapper imageReviewMapper;

    @Autowired
    public ContestController(ContestService contestService, AuthenticationHelper authenticationHelper, ContestMapper contestMapper, ImageMapper imageMapper, ImageReviewMapper imageReviewMapper) {
        this.contestService = contestService;
        this.authenticationHelper = authenticationHelper;
        this.contestMapper = contestMapper;
        this.imageMapper = imageMapper;
        this.imageReviewMapper = imageReviewMapper;
    }

    @ApiOperation(value = "Get all contests")
    @GetMapping
    public List<Contest> getAll(@RequestHeader HttpHeaders headers,
                                @RequestParam(name = "phase") Optional<String> phase) {

        User user = authenticationHelper.tryGetUser(headers);

        try {
            return contestService.getAll(user, phase);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (UnsupportedOperationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/{contestId}/participants")
    public List<User> getContestParticipants(@RequestHeader HttpHeaders headers, @PathVariable int contestId) {

        User user = authenticationHelper.tryGetUser(headers);

        try {
            return contestService.getContestParticipants(user, contestId);
        } catch (
                UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

        }
    }

    @GetMapping("/{contestId}/images")

    public List<Image> getContestImages(@RequestHeader HttpHeaders headers, @PathVariable int contestId) {

        User user = authenticationHelper.tryGetUser(headers);

        try {
            return contestService.getContestImages(user, contestId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

        }
    }

    @ApiOperation(value = "Get by id")
    @GetMapping("/{id}")
    public Contest getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {

        User user = authenticationHelper.tryGetUser(headers);

        try {
            return contestService.getById(user, id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

        }
    }

    @ApiOperation(value = "Create contest", consumes = "multipart/form-data")
    @PostMapping(consumes = {"multipart/form-data", "application/json"})
    public Contest create(@RequestHeader HttpHeaders headers,
                          @Valid @RequestPart("dto") ContestDto contestDto,
                          @RequestParam(name = "file") Optional<MultipartFile> file,
                          @RequestParam(name = "url") Optional<String> url) {

        User user = authenticationHelper.tryGetUser(headers);
        try {
            Contest contest = contestMapper.fromDto(contestDto, user);
            Set<Integer> jurySet = contestDto.getJury();
            Set<Integer> participantsSet = contestDto.getParticipants();
            return contestService.create(user, contest, jurySet, participantsSet, file, url);

        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (DuplicateEntityException | UnsupportedOperationException | IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @ApiOperation(value = "Update contest")
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data", "application/json"})
    public Contest update(@RequestHeader HttpHeaders headers, @PathVariable int id,
                          @Valid @RequestPart("dto") ContestDto contestDto,
                          @RequestParam(name = "file") Optional<MultipartFile> file,
                          @RequestParam(name = "url") Optional<String> url) {

        User user = authenticationHelper.tryGetUser(headers);

        try {
            Contest contestToUpdate = contestMapper.fromDto(id, contestDto);
            Set<Integer> jurySet = contestDto.getJury();
            Set<Integer> participantsSet = contestDto.getParticipants();
            return contestService.update(user, contestToUpdate, jurySet, participantsSet, file, url);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException | DuplicateEntityException | IOException e) {
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

    @PostMapping(value = "/{contestId}/upload", consumes = {"multipart/form-data", "application/json"})
    public Image uploadImageToContest(@RequestHeader HttpHeaders headers,
                                      @PathVariable int contestId,
                                      @RequestPart(value = "dto") ImageDto dto,
                                      @RequestParam(name = "file") Optional<MultipartFile> file,
                                      @RequestParam(name = "url") Optional<String> url) {

        User user = authenticationHelper.tryGetUser(headers);
        Image image = imageMapper.fromDto(user, dto);
        try {
            return contestService.uploadImageToContest(user, image, contestId, file, url);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


    @ApiOperation(value = "Add image to contest")
    @PutMapping("/{contestId}/image/{imageId}")
    public Image addImage(@RequestHeader HttpHeaders headers, @PathVariable int contestId,
                          @PathVariable int imageId) {
        User user = authenticationHelper.tryGetUser(headers);

        try {
            return contestService.addImageToContest(user, contestId, imageId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException | DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @ApiOperation(value = "Add image to contest")
    @PutMapping("/{contestId}/image/{imageId}/remove")
    public void removeImage(@RequestHeader HttpHeaders headers, @PathVariable int contestId,
                            @PathVariable int imageId) {
        User user = authenticationHelper.tryGetUser(headers);

        try {
            contestService.removeImageFromContest(user, contestId, imageId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @ApiOperation(value = "Rate image")
    @PostMapping("/{contestId}/image/{imageId}/rate")
    public ImageReview rateImage(@RequestHeader HttpHeaders headers, @PathVariable int contestId,
                                 @PathVariable int imageId, @Valid @RequestBody ImageReviewDto imageReviewDto) {

        User user = authenticationHelper.tryGetUser(headers);

        try {
            ImageReview imageReview = imageReviewMapper.fromDto(imageReviewDto);
            return contestService.rateImage(user, imageReview, contestId, imageId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException | DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
