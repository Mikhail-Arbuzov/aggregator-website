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
public class HDDController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/hdd")
    public String getHDDPage(Model model, String messageWeb4, String siteName4,
                             String month4, String year4){
        WebsiteDto web1= websiteService.addDefaultWebsite();
        List<WebsiteDto> hddWebsites = websiteService.findAllWebsiteBySection("HDD",web1);
        List<WebsiteDto> ratingTrafficHDDWebsites = websiteService.findAllWebsiteRatingTrafficBySection("HDD",web1);
        List<WebsiteDto> ratingHDDWebsites = websiteService.findAllWebsiteRatingBySection("HDD",web1);

        DeviceDto deviceDefault3 = deviceService.addDefaultDevice();
        List<DeviceDto> hddOffices = deviceService.findAllDevicesBySectionAndDestination("HDD","Офис",deviceDefault3);
        List<DeviceDto> hddForGames = deviceService.findAllDevicesBySectionAndDestination("HDD","Игры",deviceDefault3);
        List<DeviceDto> hddHomes = deviceService.findAllDevicesBySectionAndDestination("HDD","Дом",deviceDefault3);

        model.addAttribute("hddWebsites",hddWebsites);
        model.addAttribute("ratingTrafficHDDWebsites",ratingTrafficHDDWebsites);
        model.addAttribute("ratingHDDWebsites",ratingHDDWebsites);

        model.addAttribute("hddOffices",hddOffices);
        model.addAttribute("hddForGames", hddForGames);
        model.addAttribute("hddHomes",hddHomes);

        if(messageWeb4 != null){
            model.addAttribute("messageWeb4",messageWeb4);
        }
        if(siteName4 != null){
            model.addAttribute("siteName4",siteName4);
        }
        if (month4 != null){
            model.addAttribute("month4", month4);
        }
        if (year4 != null ){
            model.addAttribute("year4",year4);
        }

        return "equipments/hdd";
    }

    @PutMapping("/allForPC/hdd/id")
    public String updateHDDWebsiteById(@RequestParam("id") Long id, Model model){
        WebsiteDto websiteDto4 = websiteService.getWebsiteById(id);
        if(websiteDto4 != null){
            try{
                SiteAnalysisDto analysisDto2 = siteAnalysisClient.getSiteAnalysis(websiteDto4.getSiteName());
                if(analysisDto2 != null){
                    InfoTrafficWebsite website = websiteService.getInfoTrafficWebsiteDto(websiteDto4.getId(),analysisDto2);
                    String siteName4 = analysisDto2.getSiteName();
                    String month4 = statisticDataConvector.monthConvector(analysisDto2.getEngagments().getMonth());
                    String year4 = analysisDto2.getEngagments().getYear();
                    websiteService.updateWebsite(website);

                    model.addAttribute("siteName4",siteName4);
                    model.addAttribute("month4", month4);
                    model.addAttribute("year4",year4);
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode4 = e.getStatusCode().value();
                String messageWeb4 = "Данных нет, код ошибки - " + statusCode4;
                model.addAttribute("messageWeb4",messageWeb4);
                return "redirect:/allForPC/hdd";
            }
        }
        return "redirect:/allForPC/hdd";
    }

    @DeleteMapping("/allForPC/hdd/id")
    public String deleteHDDWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/hdd";
    }

    @PutMapping("/allForPC/hdd/number")
    public String updateHDDPrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/hdd";
    }

    @DeleteMapping("/allForPC/hdd/number")
    public String deleteHDD(@RequestParam("number") Long number){
        Device hdd = deviceService.getByIdDevice(number);
        if(hdd != null){
            String image3 = hdd.getImage();
            if(deviceService.deleteDevice(hdd.getId())){
                deviceService.deleteOldImage(image3);
            }
        }
        return "redirect:/allForPC/hdd";
    }
}
