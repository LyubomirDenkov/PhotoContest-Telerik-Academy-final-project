package application.photocontest.controllers.rest;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.modelmappers.UserMapper;
import application.photocontest.models.User;
import application.photocontest.models.dto.RegisterDto;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public List<User> getAll(){
       return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@RequestHeader HttpHeaders headers,@PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        return userService.getById(id);
    }

    @PostMapping
    public User create(@Valid @RequestBody RegisterDto dto) {

        User user = userMapper.fromDto(dto);

        return userService.create(user);
    }

    @PutMapping("/{id}")
    public User update(@RequestHeader HttpHeaders headers, @PathVariable int id){

        User user = authenticationHelper.tryGetUser(headers);

        return userService.update(user,id);
    }

}