package org.edu.pet.controller;

import lombok.RequiredArgsConstructor;
import org.edu.pet.constant.TemplateNames;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.dto.resp.ReadUserDto;
import org.edu.pet.dto.resp.WeatherCardDto;
import org.edu.pet.mapper.UserDtoMapper;
import org.edu.pet.model.User;
import org.edu.pet.service.WeatherCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final WeatherCardService weatherCardService;
    private final UserDtoMapper userDtoMapper;

    @GetMapping(WebRoutes.MAIN)
    public String getMainPage(@RequestAttribute("user") ReadUserDto readUserDto, Model model) {

        User user = userDtoMapper.toEntity(readUserDto);
        List<WeatherCardDto> weatherCards = weatherCardService.getWeatherCards(user);
        model.addAttribute("weatherCards", weatherCards);

        return TemplateNames.MAIN;
    }
}