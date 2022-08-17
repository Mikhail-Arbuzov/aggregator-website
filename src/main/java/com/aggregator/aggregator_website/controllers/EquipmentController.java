package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.InfoTrafficWebsite;
import com.aggregator.aggregator_website.dto.WebsiteDto;
import com.aggregator.aggregator_website.dto.siteanalysis.SiteAnalysisDto;
import com.aggregator.aggregator_website.services.SiteAnalysisClient;
import com.aggregator.aggregator_website.services.StatisticDataConvector;
import com.aggregator.aggregator_website.services.WebsiteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Controller
@AllArgsConstructor
public class EquipmentController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/processor")
    public String getProcessorsPage(Model model,String messageWeb, String siteName,
                                    String month,String year){
        List<WebsiteDto> processorWebsites = websiteService.getAllWebsiteByProcessor();
        List<WebsiteDto> ratingTrafficProcessorWebsites = websiteService.getAllWebsiteRatingTrafficByProcessor();
        List<WebsiteDto> ratingProcessorWebsites = websiteService.getAllWebsiteRatingByProcessor();

        model.addAttribute("processorWebsites",processorWebsites);
        model.addAttribute("ratingProcessorWebsites",ratingProcessorWebsites);
        model.addAttribute("ratingTrafficProcessorWebsites",ratingTrafficProcessorWebsites);

        if(messageWeb != null){
            model.addAttribute("messageWeb",messageWeb);
        }

        if(siteName != null){
            model.addAttribute("siteName",siteName);
        }
        if (month != null){
            model.addAttribute("month", month);
        }
        if (year != null ){
            model.addAttribute("year",year);
        }

        return "equipments/procesor";
    }

    @PutMapping("/allForPC/processor/id")
    public String updateWebsiteById(@RequestParam("id") Long id, Model model){
        WebsiteDto websiteDto = websiteService.getWebsiteById(id);
        InfoTrafficWebsite trafficWebsite = new InfoTrafficWebsite();
        if (websiteDto != null){
           try{
               SiteAnalysisDto analysisDto = siteAnalysisClient.getSiteAnalysis(websiteDto.getSiteName());
               if(analysisDto != null){
                   String siteName = analysisDto.getSiteName();
                   trafficWebsite.setId(websiteDto.getId());
                   String timeOnSite = statisticDataConvector.timeOnSiteConvector(analysisDto.getEngagments().getTimeOnSite());
                   trafficWebsite.setTimeOnSite(timeOnSite);
                   double bounceRate = statisticDataConvector.bounceRateConvector(analysisDto.getEngagments().getBounceRate());
                   trafficWebsite.setBounceRate(bounceRate);
                   String visits = statisticDataConvector.visitsConvectorInStr(analysisDto.getEngagments().getVisits());
                   trafficWebsite.setVisits(visits);
                   trafficWebsite.setRatingInWorld(analysisDto.getGlobalRank().getRank());
                   trafficWebsite.setRatingInCountry(analysisDto.getCountryRank().getRank());
                   String month = statisticDataConvector.monthConvector(analysisDto.getEngagments().getMonth());
                   trafficWebsite.setMonth(month);
                   String year = analysisDto.getEngagments().getYear();
                   trafficWebsite.setYear(year);
                   websiteService.updateWebsite(trafficWebsite);

                   model.addAttribute("siteName",siteName);
                   model.addAttribute("month", month);
                   model.addAttribute("year",year);
               }
           }
           catch (HttpClientErrorException e){
               e.getMessage();
               int statusCode = e.getStatusCode().value();
               String messageWeb = "Данных нет, код ошибки - " + statusCode;
               model.addAttribute("messageWeb",messageWeb);
               return "redirect:/allForPC/processor";
           }
        }

        return "redirect:/allForPC/processor";
    }

    @DeleteMapping("/allForPC/processor/id")
    public String deleteWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/processor";
    }

//    private InfoTrafficWebsite getIfoTrafficWebsite(Long id){
//        InfoTrafficWebsite trafficWebsite = new InfoTrafficWebsite();
//        WebsiteDto websiteDto = websiteService.getWebsiteById(id);
//        if(websiteDto != null){
//            String siteName = websiteDto.getSiteName();
//            Integer ratingTrafficWorld = websiteDto.getRatingInWorld();
//            Integer ratingTrafficCountry = websiteDto.getRatingInCountry();
//            String month = websiteDto.getMonth();
//            String year = websiteDto.getYear();
//            Long number = websiteDto.getId();
//
//            trafficWebsite.setSiteName(siteName);
//            trafficWebsite.setRatingTrafficWorld(ratingTrafficWorld);
//            trafficWebsite.setRatingTrafficCountry(ratingTrafficCountry);
//            trafficWebsite.setMonth(month);
//            trafficWebsite.setYear(year);
//            trafficWebsite.setNumber(number);
//        }
//        return trafficWebsite;
//    }
}
