package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.SystemSourceDto;
import com.aggregator.aggregator_website.services.SystemSourceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class SystemSourceController {
    private final SystemSourceService systemSourceService;

    @GetMapping("/allForPC/operative")
    public String getOperativeController(Model model){
        List<SystemSourceDto> systemSourcesDto = systemSourceService.getAllSystemSources();
        model.addAttribute("systemSourcesDto",systemSourcesDto);
        return "operative";
    }

    @DeleteMapping("/allForPC/operative/id")
    public String deleteSystemSource(@RequestParam("id") Long id){
        systemSourceService.deleteSystemSource(id);
        return "redirect:/allForPC/operative";
    }
}
