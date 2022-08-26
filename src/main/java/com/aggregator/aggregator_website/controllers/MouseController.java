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
public class MouseController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/mouse")
    public String getMousePage(Model model, String messageWeb_mouse, String siteName_mouse,
                               String month_mouse, String year_mouse){
        WebsiteDto webMouse = websiteService.addDefaultWebsite();
        List<WebsiteDto> mouseWebsites = websiteService.findAllWebsiteBySection("Мышка",webMouse);
        List<WebsiteDto> ratingTrafficMouses = websiteService.findAllWebsiteRatingTrafficBySection("Мышка",webMouse);
        List<WebsiteDto> ratingMouseWebsites = websiteService.findAllWebsiteRatingBySection("Мышка",webMouse);

        DeviceDto mouseDevice = deviceService.addDefaultDevice();
        List<DeviceDto> mouseOffices = deviceService.findAllDevicesBySectionAndDestination("Мышка","Офис",mouseDevice);
        List<DeviceDto> mouseGames = deviceService.findAllDevicesBySectionAndDestination("Мышка","Игры",mouseDevice);
        List<DeviceDto> mouseHomes = deviceService.findAllDevicesBySectionAndDestination("Мышка","Дом",mouseDevice);

        model.addAttribute("mouseWebsites",mouseWebsites);
        model.addAttribute("ratingTrafficMouses",ratingTrafficMouses);
        model.addAttribute("ratingMouseWebsites",ratingMouseWebsites);

        model.addAttribute("mouseOffices",mouseOffices);
        model.addAttribute("mouseGames",mouseGames);
        model.addAttribute("mouseHomes", mouseHomes);

        if(messageWeb_mouse != null){
            model.addAttribute("messageWeb_mouse",messageWeb_mouse);
        }

        if(siteName_mouse != null){
            model.addAttribute("siteName_mouse",siteName_mouse);
        }

        if(month_mouse != null){
            model.addAttribute("month_mouse",month_mouse);
        }

        if(year_mouse != null){
            model.addAttribute("year_mouse",year_mouse);
        }

        return "equipments/mouse";
    }

    @PutMapping("/allForPC/mouse/id")
    public String updateMouseWebsite(@RequestParam("id") Long id, Model model){
        WebsiteDto mouseWebsite = websiteService.getWebsiteById(id);
        if(mouseWebsite != null){
            try{
                SiteAnalysisDto siteAnalysisMouse = siteAnalysisClient.getSiteAnalysis(mouseWebsite.getSiteName());
                if(siteAnalysisMouse != null){
                    InfoTrafficWebsite trafficMouseWebsite = websiteService.getInfoTrafficWebsiteDto(mouseWebsite.getId(),siteAnalysisMouse);
                    String siteName_mouse = siteAnalysisMouse.getSiteName();
                    String month_mouse = statisticDataConvector.monthConvector(siteAnalysisMouse.getEngagments().getMonth());
                    String year_mouse = siteAnalysisMouse.getEngagments().getYear();
                    websiteService.updateWebsite(trafficMouseWebsite);
                    model.addAttribute("siteName_mouse",siteName_mouse);
                    model.addAttribute("month_mouse",month_mouse);
                    model.addAttribute("year_mouse",year_mouse);
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode12 = e.getStatusCode().value();
                String messageWeb_mouse = "Данных нет, код ошибки - " + statusCode12;
                model.addAttribute("messageWeb_mouse",messageWeb_mouse);
                return "redirect:/allForPC/mouse";
            }
        }
        return "redirect:/allForPC/mouse";
    }

    @DeleteMapping("/allForPC/mouse/id")
    public String deleteMouseWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/mouse";
    }

    @PutMapping("/allForPC/mouse/number")
    public String updateMousePrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/mouse";
    }

    @DeleteMapping("/allForPC/mouse/number")
    public String deleteMouse(@RequestParam("number") Long number){
        Device mouse = deviceService.getByIdDevice(number);
        if(mouse != null){
            String imageMouse = mouse.getImage();
            if(deviceService.deleteDevice(mouse.getId())){
                deviceService.deleteOldImage(imageMouse);
            }
        }
        return "redirect:/allForPC/mouse";
    }
}
