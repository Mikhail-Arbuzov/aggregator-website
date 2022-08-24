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
public class PowerSupplyController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/powerSupply")
    public String getPowerSupplyPage(Model model, String messageWeb_powerS, String siteName_powerS,
                                     String month_powerS, String year_powerS){
        WebsiteDto websiteDtoPowerS = websiteService.addDefaultWebsite();
        List<WebsiteDto> powerSWebsites = websiteService.findAllWebsiteBySection("БП",websiteDtoPowerS);
        List<WebsiteDto> powerSTrafficRatings = websiteService.findAllWebsiteRatingTrafficBySection("БП",websiteDtoPowerS);
        List<WebsiteDto> powerSRatings = websiteService.findAllWebsiteRatingBySection("БП",websiteDtoPowerS);

        DeviceDto deviceDtoPowerS = deviceService.addDefaultDevice();
        List<DeviceDto> powerSOffices = deviceService.findAllDevicesBySectionAndDestination("БП","Офис",deviceDtoPowerS);
        List<DeviceDto> powerSGames = deviceService.findAllDevicesBySectionAndDestination("БП","Игры",deviceDtoPowerS);
        List<DeviceDto> powerSHomes = deviceService.findAllDevicesBySectionAndDestination("БП","Дом",deviceDtoPowerS);

        model.addAttribute("powerSWebsites",powerSWebsites);
        model.addAttribute("powerSTrafficRatings",powerSTrafficRatings);
        model.addAttribute("powerSRatings",powerSRatings);

        model.addAttribute("powerSOffices",powerSOffices);
        model.addAttribute("powerSGames",powerSGames);
        model.addAttribute("powerSHomes",powerSHomes);

        if(siteName_powerS != null){
            model.addAttribute("siteName_powerS", siteName_powerS);
        }
        if (month_powerS != null){
            model.addAttribute("month_powerS", month_powerS);
        }
        if (year_powerS != null ){
            model.addAttribute("year_powerS", year_powerS);
        }

        if(messageWeb_powerS != null){
            model.addAttribute("messageWeb_powerS", messageWeb_powerS);
        }
        return "equipments/power-supply";
    }

    @PutMapping("/allForPC/powerSupply/id")
    public String updatePowerSWebsite(@RequestParam("id") Long id, Model model){
        WebsiteDto sitePowerS = websiteService.getWebsiteById(id);
        if(sitePowerS != null){
            try{
                SiteAnalysisDto analysisDtoPowerS = siteAnalysisClient.getSiteAnalysis(sitePowerS.getSiteName());
                String siteName_powerS = analysisDtoPowerS.getSiteName();
                String month_powerS = statisticDataConvector.monthConvector(analysisDtoPowerS.getEngagments().getMonth());
                String year_powerS = analysisDtoPowerS.getEngagments().getYear();
                InfoTrafficWebsite trafficWebsitePowerS = websiteService.getInfoTrafficWebsiteDto(sitePowerS.getId(),analysisDtoPowerS);
                websiteService.updateWebsite(trafficWebsitePowerS);

                model.addAttribute("siteName_powerS", siteName_powerS);
                model.addAttribute("month_powerS", month_powerS);
                model.addAttribute("year_powerS", year_powerS);

            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode6 = e.getStatusCode().value();
                String messageWeb_powerS = "Данных нет, код ошибки - " + statusCode6;
                model.addAttribute("messageWeb_powerS", messageWeb_powerS);
                return "redirect:/allForPC/powerSupply";
            }
        }
        return "redirect:/allForPC/powerSupply";
    }

    @DeleteMapping("/allForPC/powerSupply/id")
    public String deletePowerSWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/powerSupply";
    }

    @PutMapping("/allForPC/powerSupply/number")
    public String updatePricePowerSupply(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/powerSupply";
    }

    @DeleteMapping("/allForPC/powerSupply/number")
    public String deletePowerSupply(@RequestParam("number") Long number){
        Device powerSupply = deviceService.getByIdDevice(number);
        if(powerSupply != null){
            String imagePS = powerSupply.getImage();
            if (deviceService.deleteDevice(powerSupply.getId())){
                deviceService.deleteOldImage(imagePS);
            }
        }
        return "redirect:/allForPC/powerSupply";
    }
}
