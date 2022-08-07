package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.DomainRequest;
import com.aggregator.aggregator_website.dto.siteanalysis.SiteAnalysisDto;
import com.aggregator.aggregator_website.services.SiteAnalysisClient;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class AdminController {
    private final SiteAnalysisClient siteAnalysisClient;

    @GetMapping("/admin")
    public String getAdminPage(){
        return "admin";
    }

    @GetMapping("/admin/section-equipment")
    public String getEquipmentSectionSite(Model model, Double bounceRatePercent,
                                          String emptyDate,Integer errCod,Integer errCod1){
        DomainRequest domainRequest = new DomainRequest();
        model.addAttribute("domainRequest",domainRequest);
        if(bounceRatePercent != null){
            model.addAttribute("bounceRatePercent",bounceRatePercent);
        }
        if(emptyDate !=null){
            model.addAttribute("emptyDate",emptyDate);
        }

        if(errCod != null){
            model.addAttribute("errCod",errCod);
        }

        if(errCod1 != null){
            model.addAttribute("errCod1",errCod1);
        }

        return "sitesections/komplect";
    }

    @PostMapping("/admin/result-bounceRate")
    public String getBounceRate(@Valid @ModelAttribute("domainRequest")DomainRequest domainRequest,
                                BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "sitesections/komplect";
        }
        else {
            try{
                SiteAnalysisDto analysisDto = siteAnalysisClient.getSiteAnalysis(domainRequest.getValue());
                if(analysisDto != null){
                    String bounceRate = analysisDto.getEngagments().getBounceRate();
                    double percent = Double.parseDouble(bounceRate);
                    double scale = Math.pow(10,2);
                    Double bounceRatePercent = Math.ceil(percent*100*scale)/scale;
                    model.addAttribute("bounceRatePercent",bounceRatePercent);
                    return "redirect:/admin/section-equipment";
                }
                else{
                    String emptyDate ="not date";
                    model.addAttribute("emptyDate",emptyDate);
                    return "redirect:/admin/section-equipment";
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode = e.getStatusCode().value();
                if(statusCode == 404){
                    Integer errCod = statusCode;
                    model.addAttribute("errCod",errCod);
                }
                else {
                    Integer errCod1 = statusCode;
                    model.addAttribute("errCod1",errCod1);
                }

                return "redirect:/admin/section-equipment";
            }
//            catch (RuntimeException ex){
//                ex.getMessage();
//                Integer errCod1 = 404;
//                model.addAttribute("errCod1",errCod1);
//                return "redirect:/admin/section-equipment";
//            }
        }

    }
}
