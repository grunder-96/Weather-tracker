package org.edu.pet.controller;

import lombok.RequiredArgsConstructor;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.dto.req.AddLocationDto;
import org.edu.pet.dto.req.DeleteLocationDto;
import org.edu.pet.dto.resp.ReadUserDto;
import org.edu.pet.mapper.UserDtoMapper;
import org.edu.pet.model.User;
import org.edu.pet.service.UserLocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final UserDtoMapper userDtoMapper;
    private final UserLocationService userLocationService;

    @PostMapping("/add")
    public String addLocationToUser(@ModelAttribute AddLocationDto addLocationDto,
                                    @RequestAttribute("user") ReadUserDto readUserDto) {

        User user = userDtoMapper.toEntity(readUserDto);
        userLocationService.add(user, addLocationDto);

        return WebRoutes.redirectTo(WebRoutes.MAIN);
    }

    @DeleteMapping("/delete")
    public String deleteUserLocation(@ModelAttribute DeleteLocationDto deleteLocationDto,
                                     @RequestAttribute("user") ReadUserDto readUserDto) {

        User user = userDtoMapper.toEntity(readUserDto);
        userLocationService.removeByCoordinates(user, deleteLocationDto);

        return WebRoutes.redirectTo(WebRoutes.MAIN);
    }
}