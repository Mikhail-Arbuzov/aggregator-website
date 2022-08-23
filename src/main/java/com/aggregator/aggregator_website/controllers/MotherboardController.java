package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.DeviceDto;
import com.aggregator.aggregator_website.dto.InfoTrafficWebsite;
import com.aggregator.aggregator_website.dto.WebsiteDto;
import com.aggregator.aggregator_website.dto.siteanalysis.SiteAnalysisDto;
import com.aggregator.aggregator_website.entities.Device;
import com.aggregator.aggregator_website.services.DeviceService;
import com.aggregator.aggregator_website.services.SiteAnalysisClient;
import com.aggregator.aggregator_website.services.StatisticDataConvector;
import com.aggregator.aggregator_website.services.WebsiteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
public class MotherboardController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/motherboard")
    public String getMotherboardPage(Model model, String messageWeb1,String siteName1,
                                     String month1, String year1){
        WebsiteDto websiteDefault = websiteService.addDefaultWebsite();
        List<WebsiteDto> motherboardWebsites = websiteService.findAllWebsiteBySection("Материнка",websiteDefault);
        List<WebsiteDto> ratingTrafficMotherboardWebsites = websiteService.findAllWebsiteRatingTrafficBySection("Материнка",websiteDefault);
        List<WebsiteDto> ratings = websiteService.findAllWebsiteRatingBySection("Материнка",websiteDefault);

        DeviceDto defaultDeviceDto1 = deviceService.addDefaultDevice();
        List<DeviceDto> officeMotherboards = deviceService.findAllDevicesBySectionAndDestination("Материнка","Офис",defaultDeviceDto1);
        List<DeviceDto> motherboardsForGames = deviceService.findAllDevicesBySectionAndDestination("Материнка","Игры",defaultDeviceDto1);
        List<DeviceDto> motherboardsForHome = deviceService.findAllDevicesBySectionAndDestination("Материнка","Дом",defaultDeviceDto1);

        model.addAttribute("motherboardWebsites",motherboardWebsites);
        model.addAttribute("ratingTrafficMotherboardWebsites",ratingTrafficMotherboardWebsites);
        model.addAttribute("ratings", ratings);

        model.addAttribute("officeMotherboards", officeMotherboards);
        model.addAttribute("motherboardsForGames",motherboardsForGames);
        model.addAttribute("motherboardsForHome",motherboardsForHome);

        if(messageWeb1 != null){
            model.addAttribute("messageWeb1",messageWeb1);
        }

        if(siteName1 != null){
            model.addAttribute("siteName1",siteName1);
        }
        if (month1 != null){
            model.addAttribute("month1", month1);
        }
        if (year1 != null ){
            model.addAttribute("year1",year1);
        }

        return "equipments/motherboard";
    }

    @PutMapping("/allForPC/motherboard/id")
    public String updateMotherboardWebsiteById(@RequestParam("id") Long id, Model model){
        WebsiteDto websiteDto1 = websiteService.getWebsiteById(id);
        if(websiteDto1 != null){
            try{
                SiteAnalysisDto site = siteAnalysisClient.getSiteAnalysis(websiteDto1.getSiteName());
                if(site != null){
                    InfoTrafficWebsite trafficWebsite1 = websiteService.getInfoTrafficWebsiteDto(websiteDto1.getId(),site);
                    String siteName1 = site.getSiteName();
                    String month1 = statisticDataConvector.monthConvector(site.getEngagments().getMonth());
                    String year1 = site.getEngagments().getYear();
                    websiteService.updateWebsite(trafficWebsite1);

                    model.addAttribute("siteName1",siteName1);
                    model.addAttribute("month1", month1);
                    model.addAttribute("year1",year1);
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode1 = e.getStatusCode().value();
                String messageWeb1 = "Данных нет, код ошибки - " + statusCode1;
                model.addAttribute("messageWeb1",messageWeb1);
                return "redirect:/allForPC/motherboard";
            }
        }

        return "redirect:/allForPC/motherboard";
    }

    @DeleteMapping("/allForPC/motherboard/id")
    public String deleteMotherboardWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/motherboard";
    }

    @PutMapping("/allForPC/motherboard/number")
    public String updateMotherboardPrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/motherboard";
    }

    @DeleteMapping("/allForPC/motherboard/number")
    public String deleteMotherboard(@RequestParam("number") Long number){
        Device motherboard = deviceService.getByIdDevice(number);
        if(motherboard != null){
            String img = motherboard.getImage();
            if(deviceService.deleteDevice(motherboard.getId())){
                deviceService.deleteOldImage(img);
            }
        }
        return "redirect:/allForPC/motherboard";
    }
}
