package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.OfficeProgramDto;
import com.aggregator.aggregator_website.services.OfficeProgramService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class OfficeProgramController {
    private final OfficeProgramService officeProgramService;

    @GetMapping("/allForPC/officeProgram")
    public String getOfficeProgramPage(Model model){
        List<OfficeProgramDto> officePrograms = officeProgramService.getAllOfficePrograms();
        model.addAttribute("officePrograms",officePrograms);
        return "office-program";
    }

    @DeleteMapping("/allForPC/officeProgram/id")
    public String deleteOfficeProgram(@RequestParam("id") Long id){
        officeProgramService.deleteOfficeProgram(id);
        return "redirect:/allForPC/officeProgram";
    }
}
