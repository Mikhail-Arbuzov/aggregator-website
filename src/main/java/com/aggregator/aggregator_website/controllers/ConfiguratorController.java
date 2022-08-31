package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.ConfiguratorDto;
import com.aggregator.aggregator_website.services.ConfiguratorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @GetMapping("/allForPC/configurator")
    public String getConfiguratorPage(Model model){
        model.addAttribute("statisticByVisits",configuratorService.getStatisticByVisits());
        model.addAttribute("statisticByBounceRate", configuratorService.getStatisticByBounceRate());
        List<ConfiguratorDto> statisticsByTimeOnSite = configuratorService.getStatisticByTimeOnSite();
        model.addAttribute("statisticsByTimeOnSite",statisticsByTimeOnSite);
        List<ConfiguratorDto> configurators  = configuratorService.getConfigurators();
        model.addAttribute("configurators",configurators);
        return "configurator";
    }

    @PutMapping("/allForPC/configurator/id")
    public String updateStatisticConfigurator(@RequestParam("id") Long id){
        configuratorService.updateConfigurator(id);
        return "redirect:/allForPC/configurator";
    }
}
