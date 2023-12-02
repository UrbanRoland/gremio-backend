package com.gremio.controller;

import com.gremio.facade.UserFacade;
import com.gremio.model.dto.UserDetailsDto;
import com.gremio.model.dto.UserDto;
import com.gremio.model.dto.request.EmailRequest;
import com.gremio.model.dto.request.ResetPasswordRequest;
import com.gremio.model.dto.request.UpdateUserRequest;
import com.gremio.model.dto.response.PageableResponse;
import com.gremio.persistence.entity.User;
import com.gremio.service.interfaces.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController extends AbstractController {

    private final UserService userService;
    private final UserFacade userFacade;

    UserController(final ConversionService conversionService, final UserService userService, final UserFacade userFacade) {
        super(conversionService);
        this.userService = userService;
        this.userFacade = userFacade;
    }

    /**
     * Retrieves a paginated list of all users from the system. Requires "ROLE_ADMIN" authority.
     *
     * @param pageable The pagination information.
     * @return A paginated response containing a list of UserDetailsDto objects.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public PageableResponse<UserDetailsDto> getAllUsers(final Pageable pageable) {
        return this.getPageableResponse(userService.getAllUser(pageable));
    }

    /**
     * Update an existing user.
     *
     * @param request the user request
     * @return the updated user
     */
    @PutMapping("/{id}")
    public  UserDetailsDto updateUser(@PathVariable final long id, @RequestBody final UpdateUserRequest request) {
        final User user = userService.update(id, this.getConversionService().convert(request, UserDto.class));

        return this.getConversionService().convert(user, UserDetailsDto.class);
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<EmailRequest> forgotPassword(@RequestBody final EmailRequest request) {
        userFacade.forgotPassword(request.getEmail());
        //emailService.forgotPasswordEmail("urolir@gmail.com","test5","resetPassword");
        return ResponseEntity.ok(new EmailRequest("Email sent successfully!"));
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<EmailRequest> resetPassword(@RequestBody final ResetPasswordRequest request) {
        System.out.println("token : " + request.getToken() + " password: " + request.getPassword());
       // userFacade.forgotPassword(request.getEmail());
        userFacade.resetPassword(request.getPassword(), request.getToken());
        //emailService.forgotPasswordEmail("urolir@gmail.com","test5","resetPassword");
        return ResponseEntity.ok(new EmailRequest("Email sent successfully!"));
    }
}