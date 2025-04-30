package org.edu.pet.controller;

import lombok.RequiredArgsConstructor;
import org.edu.pet.constant.TemplateNames;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.dto.resp.LocationCardDto;
import org.edu.pet.dto.resp.ReadUserDto;
import org.edu.pet.exception.InvalidKeywordException;
import org.edu.pet.mapper.UserDtoMapper;
import org.edu.pet.model.User;
import org.edu.pet.service.LocationCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final LocationCardService locationCardService;
    private final UserDtoMapper userDtoMapper;

    @GetMapping(WebRoutes.SEARCH)
    public String getSearchPage(@RequestParam(value = "keyword", required = false) String keyword,
                                @RequestAttribute("user") ReadUserDto readUserDto,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (isInvalidKeyword(keyword)) {

            throw new InvalidKeywordException();
        }

        String processedKeyword = processKeyword(keyword);

        if (!keyword.equals(processedKeyword)) {

            redirectAttributes.addAttribute("keyword", processedKeyword);
            return WebRoutes.redirectTo(WebRoutes.SEARCH);
        }

        User user = userDtoMapper.toEntity(readUserDto);

        List<LocationCardDto> locations = locationCardService.getLocationCards(user, keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("locations", locations);

        return TemplateNames.SEARCH;
    }

    private boolean isInvalidKeyword(String keyword) {

        return keyword == null || keyword.isBlank();
    }

    private String processKeyword(String param) {

        return param.strip().replaceAll("\\s+", " ");
    }
}