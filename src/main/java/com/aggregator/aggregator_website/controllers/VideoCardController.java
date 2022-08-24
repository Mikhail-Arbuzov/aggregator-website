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
public class VideoCardController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/videoCard")
    public String getVideoCardPage(Model model, String messageWeb_videoC, String siteName_videoC,
                                   String month_videoC, String year_videoC){
        WebsiteDto websiteVideo = websiteService.addDefaultWebsite();
        List<WebsiteDto> videoCWebsites = websiteService.findAllWebsiteBySection("Видеокарта",websiteVideo);
        List<WebsiteDto> ratingTrafficVideoCards = websiteService.findAllWebsiteRatingTrafficBySection("Видеокарта",websiteVideo);
        List<WebsiteDto> ratingVideoCWebsites = websiteService.findAllWebsiteRatingBySection("Видеокарта",websiteVideo);

        DeviceDto deviceDtoVideo = deviceService.addDefaultDevice();
        List<DeviceDto> videoCOffices = deviceService.findAllDevicesBySectionAndDestination("Видеокарта","Офис",deviceDtoVideo);
        List<DeviceDto> videoCGames = deviceService.findAllDevicesBySectionAndDestination("Видеокарта","Игры",deviceDtoVideo);
        List<DeviceDto> videoCHomes = deviceService.findAllDevicesBySectionAndDestination("Видеокарта","Дом",deviceDtoVideo);

        model.addAttribute("videoCWebsites",videoCWebsites);
        model.addAttribute("ratingTrafficVideoCards",ratingTrafficVideoCards);
        model.addAttribute("ratingVideoCWebsites",ratingVideoCWebsites);

        model.addAttribute("videoCOffices",videoCOffices);
        model.addAttribute("videoCGames",videoCGames);
        model.addAttribute("videoCHomes",videoCHomes);

        if(messageWeb_videoC != null){
            model.addAttribute("messageWeb_videoC", messageWeb_videoC);
        }
        if(siteName_videoC != null){
            model.addAttribute("siteName_videoC", siteName_videoC);
        }
        if (month_videoC != null){
            model.addAttribute("month_videoC", month_videoC);
        }
        if (year_videoC != null ){
            model.addAttribute("year_videoC", year_videoC);
        }
        return "equipments/videocard";
    }

    @PutMapping("/allForPC/videoCard/id")
    public String updateVideoCWebsite(@RequestParam("id") Long id, Model model){
        WebsiteDto websiteVideoCard = websiteService.getWebsiteById(id);
        if(websiteVideoCard != null){
            try{
                SiteAnalysisDto siteAnalysisVideoC = siteAnalysisClient.getSiteAnalysis(websiteVideoCard.getSiteName());
                if(siteAnalysisVideoC != null){
                    InfoTrafficWebsite trafficWebsiteVideoC = websiteService.getInfoTrafficWebsiteDto(websiteVideoCard.getId(),siteAnalysisVideoC);
                    String siteName_videoC = siteAnalysisVideoC.getSiteName();
                    String month_videoC = siteAnalysisVideoC.getEngagments().getMonth();
                    String year_videoC = siteAnalysisVideoC.getEngagments().getYear();
                    websiteService.updateWebsite(trafficWebsiteVideoC);

                    model.addAttribute("siteName_videoC", siteName_videoC);
                    model.addAttribute("month_videoC", month_videoC);
                    model.addAttribute("year_videoC", year_videoC);
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode5 = e.getStatusCode().value();
                String messageWeb_videoC = "Данных нет, код ошибки - " + statusCode5;
                model.addAttribute("messageWeb_videoC",messageWeb_videoC);
                return "redirect:/allForPC/videoCard";
            }
        }
        return "redirect:/allForPC/videoCard";
    }

    @DeleteMapping("/allForPC/videoCard/id")
    public String deleteVideoCWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/videoCard";
    }

    @PutMapping("/allForPC/videoCard/number")
    public String updateDevicePrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/videoCard";
    }

    @DeleteMapping("/allForPC/videoCard/number")
    public String deleteVideoCard(@RequestParam("number") Long number){
        Device videoCard = deviceService.getByIdDevice(number);
        if(videoCard != null){
            String imageVideoC = videoCard.getImage();
            if(deviceService.deleteDevice(videoCard.getId())){
                deviceService.deleteOldImage(imageVideoC);
            }
        }
        return "redirect:/allForPC/videoCard";
    }
}
