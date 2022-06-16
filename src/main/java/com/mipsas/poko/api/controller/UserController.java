package com.mipsas.poko.api.controller;

import static com.mipsas.poko.api.Paths.LOCATIONS;
import static com.mipsas.poko.api.Paths.META_DATA;
import static com.mipsas.poko.api.Paths.USERS;
import com.mipsas.poko.api.model.request.UpdateUserRequest;
import com.mipsas.poko.api.model.response.UserLocationResponse;
import com.mipsas.poko.api.model.response.UserMetaDataResponse;
import com.mipsas.poko.api.model.response.UserResponse;
import com.mipsas.poko.api.service.UserService;
import com.mipsas.poko.data.entity.UserEntity;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(u -> new UserResponse(u.getId(), u.getNickName()))
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return new UserResponse(user.getId(), user.getNickName());
    }

    @PutMapping
    public void updateUser(@RequestBody @Valid UpdateUserRequest request) {
        userService.updateUser(request);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/{id}" + LOCATIONS)
    public List<UserLocationResponse> getUserLocations(@PathVariable Long id) {
        return userService.getUserLocations(id).stream()
                .map(l -> new UserLocationResponse(l.getLatitude(), l.getLongitude()))
                .toList();
    }

    @GetMapping("/{id}" + META_DATA)
    public List<UserMetaDataResponse> getUserMetadata(@PathVariable Long id) {
        return userService.getUserMetaData(id).stream()
                .map(m -> new UserMetaDataResponse(m.getIp(), m.getProvider()))
                .toList();
    }
}
