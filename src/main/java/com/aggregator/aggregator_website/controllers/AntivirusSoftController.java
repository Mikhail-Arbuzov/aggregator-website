package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.AntivirusSoftDto;
import com.aggregator.aggregator_website.services.AntivirusSoftService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class AntivirusSoftController {
    private final AntivirusSoftService antivirusSoftService;

    @GetMapping("/allForPC/antivirus-soft")
    public String getAntivirusSoftPage(Model model){
        List<AntivirusSoftDto> antivirusSoftList = antivirusSoftService.getAllAntivirusSoft();
        model.addAttribute("antivirusSoftList", antivirusSoftList);
        return "antivirus-soft";
    }

    @DeleteMapping("/allForPC/antivirus-soft/id")
    public String deleteAntivirusSoft(@RequestParam("id") Long id){
        antivirusSoftService.deleteAntivirusSoft(id);
        return "redirect:/allForPC/antivirus-soft";
    }
}
