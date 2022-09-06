package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.SoftwareDto;
import com.aggregator.aggregator_website.services.SoftwareService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class SoftwareController {
    private final SoftwareService softwareService;

    @GetMapping("/allForPC/soft")
    public String getSoftPage(Model model){
        List<SoftwareDto> softwareDtoList = softwareService.getAllSoftware();
        model.addAttribute("softwareDtoList",softwareDtoList);
        return "soft";
    }

    @DeleteMapping("/allForPC/soft/id")
    public String deleteSoftware(@RequestParam("id") Long id){
        softwareService.deleteSoftware(id);
        return "redirect:/allForPC/soft";
    }
}
