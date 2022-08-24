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
public class SSDController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/ssd")
    public String getSSDPage(Model model,String messageWeb3,String siteName3,
                             String month3,String year3){
        WebsiteDto web = websiteService.addDefaultWebsite();
        List<WebsiteDto> ssdWebsites = websiteService.findAllWebsiteBySection("SSD",web);
        List<WebsiteDto> ratingTrafficSSDWebsites = websiteService.findAllWebsiteRatingTrafficBySection("SSD",web);
        List<WebsiteDto> ratingSSDWebsites = websiteService.findAllWebsiteRatingBySection("SSD",web);

        DeviceDto defDevice = deviceService.addDefaultDevice();
        List<DeviceDto> ssdOffices = deviceService.findAllDevicesBySectionAndDestination("SSD","Офис",defDevice);
        List<DeviceDto> ssdGames = deviceService.findAllDevicesBySectionAndDestination("SSD","Игры",defDevice);
        List<DeviceDto> ssdHomes = deviceService.findAllDevicesBySectionAndDestination("SSD","Дом", defDevice);

        model.addAttribute("ssdWebsites",ssdWebsites);
        model.addAttribute("ratingTrafficSSDWebsites",ratingTrafficSSDWebsites);
        model.addAttribute("ratingSSDWebsites",ratingSSDWebsites);

        model.addAttribute("ssdOffices",ssdOffices);
        model.addAttribute("ssdGames",ssdGames);
        model.addAttribute("ssdHomes",ssdHomes);

        if(siteName3 != null){
            model.addAttribute("siteName3",siteName3);
        }
        if (month3 != null){
            model.addAttribute("month3", month3);
        }
        if (year3 != null ){
            model.addAttribute("year3",year3);
        }
        if(messageWeb3 != null){
            model.addAttribute("messageWeb3",messageWeb3);
        }
        return "equipments/ssd";
    }

    @PutMapping("/allForPC/ssd/id")
    public String updateSSDWebsiteById(@RequestParam("id") Long id, Model model){
        WebsiteDto webDto = websiteService.getWebsiteById(id);
        if(webDto != null){
            try{
                SiteAnalysisDto siteAnalysis = siteAnalysisClient.getSiteAnalysis(webDto.getSiteName());
                if(siteAnalysis != null){
                    InfoTrafficWebsite trafficWebsite3 = websiteService.getInfoTrafficWebsiteDto(webDto.getId(),siteAnalysis);
                    String siteName3 = siteAnalysis.getSiteName();
                    String month3 = statisticDataConvector.monthConvector(siteAnalysis.getEngagments().getMonth());
                    String year3 = siteAnalysis.getEngagments().getYear();
                    websiteService.updateWebsite(trafficWebsite3);

                    model.addAttribute("siteName3",siteName3);
                    model.addAttribute("month3", month3);
                    model.addAttribute("year3",year3);

                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode3 = e.getStatusCode().value();
                String messageWeb3 = "Данных нет, код ошибки - " + statusCode3;
                model.addAttribute("messageWeb3",messageWeb3);
                return "redirect:/allForPC/ssd";
            }
        }

        return "redirect:/allForPC/ssd";
    }

    @DeleteMapping("/allForPC/ssd/id")
    public String deleteSSDWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/ssd";
    }

    @PutMapping("/allForPC/ssd/number")
    public String updateSSDPrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/ssd";
    }

    @DeleteMapping("/allForPC/ssd/number")
    public String deleteSSD(@RequestParam("number") Long number){
        Device ssd = deviceService.getByIdDevice(number);
        if(ssd != null){
            String img2 = ssd.getImage();
            if(deviceService.deleteDevice(ssd.getId())){
                deviceService.deleteOldImage(img2);
            }
        }
        return "redirect:/allForPC/ssd";
    }
}
