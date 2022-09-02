package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.CompilationDto;
import com.aggregator.aggregator_website.dto.ConfiguratorDto;
import com.aggregator.aggregator_website.entities.User;
import com.aggregator.aggregator_website.services.CompilationService;
import com.aggregator.aggregator_website.services.ConfiguratorService;
import com.aggregator.aggregator_website.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ConfiguratorController {
    private final ConfiguratorService configuratorService;
    private final CompilationService compilationService;
    private final UserService userService;

    @GetMapping("/allForPC/configurator")
    public String getConfiguratorPage(Model model){
        User currentUser = userService.getCurrentUser();
        if(currentUser != null){
            model.addAttribute("currentUser",currentUser);
        }
        model.addAttribute("statisticByVisits",configuratorService.getStatisticByVisits());
        model.addAttribute("statisticByBounceRate", configuratorService.getStatisticByBounceRate());
        List<ConfiguratorDto> statisticsByTimeOnSite = configuratorService.getStatisticByTimeOnSite();
        model.addAttribute("statisticsByTimeOnSite",statisticsByTimeOnSite);
        List<ConfiguratorDto> configurators  = configuratorService.getConfigurators();
        model.addAttribute("configurators",configurators);
        List<CompilationDto> compilationDtoList = compilationService.getAllCompilations();
        model.addAttribute("compilationDtoList",compilationDtoList);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        model.addAttribute("dateTimeFormatter", dateTimeFormatter);
        return "configurator";
    }

    @PutMapping("/allForPC/configurator/id")
    public String updateStatisticConfigurator(@RequestParam("id") Long id){
        configuratorService.updateConfigurator(id);
        return "redirect:/allForPC/configurator";
    }

    @DeleteMapping("/allForPC/configurator/id")
    public String deleteCompilation(@RequestParam("id") Long id){
        compilationService.deleteCompilation(id);
        return "redirect:/allForPC/configurator";
    }
}
