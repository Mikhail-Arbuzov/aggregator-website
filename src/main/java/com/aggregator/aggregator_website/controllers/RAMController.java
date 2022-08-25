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
public class RAMController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/ram")
    public String getRAMPage(Model model, String messageWeb_ram, String siteName_ram,
                             String month_ram, String year_ram){
        WebsiteDto webRAM = websiteService.addDefaultWebsite();
        List<WebsiteDto> ramWebsites = websiteService.findAllWebsiteBySection("ОЗУ",webRAM);
        List<WebsiteDto> ramTrafficRatings = websiteService.findAllWebsiteRatingTrafficBySection("ОЗУ",webRAM);
        List<WebsiteDto> ramRatings = websiteService.findAllWebsiteRatingBySection("ОЗУ",webRAM);

        DeviceDto ramDeviceDef = deviceService.addDefaultDevice();
        List<DeviceDto> ramOffices = deviceService.findAllDevicesBySectionAndDestination("ОЗУ","Офис",ramDeviceDef);
        List<DeviceDto> ramGames = deviceService.findAllDevicesBySectionAndDestination("ОЗУ","Игры",ramDeviceDef);
        List<DeviceDto> ramHomes = deviceService.findAllDevicesBySectionAndDestination("ОЗУ","Дом",ramDeviceDef);

        model.addAttribute("ramWebsites",ramWebsites);
        model.addAttribute("ramTrafficRatings",ramTrafficRatings);
        model.addAttribute("ramRatings",ramRatings);

        model.addAttribute("ramOffices",ramOffices);
        model.addAttribute("ramGames",ramGames);
        model.addAttribute("ramHomes",ramHomes);

        if(messageWeb_ram != null){
            model.addAttribute("messageWeb_ram", messageWeb_ram);
        }

        if(siteName_ram != null){
            model.addAttribute("siteName_ram", siteName_ram);
        }
        if (month_ram != null){
            model.addAttribute("month_ram", month_ram);
        }
        if (year_ram != null ){
            model.addAttribute("year_ram", year_ram);
        }

        return "equipments/ram";
    }

    @PutMapping("/allForPC/ram/id")
    public String updateRAMWebsite(@RequestParam("id") Long id, Model model){
        WebsiteDto websiteDtoRAM = websiteService.getWebsiteById(id);
        if(websiteDtoRAM != null){
            try{
                SiteAnalysisDto ramSiteAnalysis = siteAnalysisClient.getSiteAnalysis(websiteDtoRAM.getSiteName());
                if(ramSiteAnalysis != null){
                    InfoTrafficWebsite ramInfoWebsite = websiteService.getInfoTrafficWebsiteDto(websiteDtoRAM.getId(),ramSiteAnalysis);
                    websiteService.updateWebsite(ramInfoWebsite);
                    String siteName_ram = ramSiteAnalysis.getSiteName();
                    String month_ram = statisticDataConvector.monthConvector(ramSiteAnalysis.getEngagments().getMonth());
                    String year_ram = ramSiteAnalysis.getEngagments().getYear();

                    model.addAttribute("siteName_ram", siteName_ram);
                    model.addAttribute("month_ram", month_ram);
                    model.addAttribute("year_ram", year_ram);
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode7 = e.getStatusCode().value();
                String messageWeb_ram = "Данных нет, код ошибки - " + statusCode7;
                model.addAttribute("messageWeb_ram", messageWeb_ram);
                return "redirect:/allForPC/ram";
            }
        }
        return "redirect:/allForPC/ram";
    }

    @DeleteMapping("/allForPC/ram/id")
    public String deleteRAMWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/ram";
    }

    @PutMapping("/allForPC/ram/number")
    public String updateRAMPrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/ram";
    }

    @DeleteMapping("/allForPC/ram/number")
    public String deleteRAM(@RequestParam("number") Long number){
        Device ram = deviceService.getByIdDevice(number);
        if(ram != null){
            String imageRAM = ram.getImage();
            if(deviceService.deleteDevice(ram.getId())){
                deviceService.deleteOldImage(imageRAM);
            }
        }
        return "redirect:/allForPC/ram";
    }
}
