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
public class MFDController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/mfd")
    public String getMFDPage(Model model, String messageWeb_mfd, String siteName_mfd,
                             String month_mfd, String year_mfd){
        WebsiteDto webMFD = websiteService.addDefaultWebsite();
        List<WebsiteDto> mfdWebsites = websiteService.findAllWebsiteBySection("МФУ",webMFD);
        List<WebsiteDto> ratingTrafficMFDWebsites = websiteService.findAllWebsiteRatingTrafficBySection("МФУ",webMFD);
        List<WebsiteDto> ratingMFDWebsites = websiteService.findAllWebsiteRatingBySection("МФУ",webMFD);

        DeviceDto mfdDevice = deviceService.addDefaultDevice();
        List<DeviceDto> mfdOffices = deviceService.findAllDevicesBySectionAndDestination("МФУ","Офис",mfdDevice);
        List<DeviceDto> mfdHomes = deviceService.findAllDevicesBySectionAndDestination("МФУ","Дом",mfdDevice);

        model.addAttribute("mfdWebsites",mfdWebsites);
        model.addAttribute("ratingTrafficMFDWebsites",ratingTrafficMFDWebsites);
        model.addAttribute("ratingMFDWebsites",ratingMFDWebsites);

        model.addAttribute("mfdOffices",mfdOffices);
        model.addAttribute("mfdHomes",mfdHomes);

        if(messageWeb_mfd != null){
            model.addAttribute("messageWeb_mfd",messageWeb_mfd);
        }

        if(siteName_mfd != null){
            model.addAttribute("siteName_mfd",siteName_mfd);
        }

        if(month_mfd != null){
            model.addAttribute("month_mfd",month_mfd);
        }

        if(year_mfd != null){
            model.addAttribute("year_mfd",year_mfd);
        }

        return "equipments/mfd";
    }

    @PutMapping("/allForPC/mfd/id")
    public String updateMFDWebsite(@RequestParam("id") Long id, Model model){
        WebsiteDto mfdWebsite = websiteService.getWebsiteById(id);
        if(mfdWebsite != null){
            try{
                SiteAnalysisDto mfdSite = siteAnalysisClient.getSiteAnalysis(mfdWebsite.getSiteName());
                if(mfdSite != null){
                    InfoTrafficWebsite trafficMFDWebsite = websiteService.getInfoTrafficWebsiteDto(mfdWebsite.getId(),mfdSite);
                    String siteName_mfd = mfdSite.getSiteName();
                    String month_mfd = statisticDataConvector.monthConvector(mfdSite.getEngagments().getMonth());
                    String year_mfd = mfdSite.getEngagments().getYear();
                    websiteService.updateWebsite(trafficMFDWebsite);

                    model.addAttribute("siteName_mfd",siteName_mfd);
                    model.addAttribute("month_mfd",month_mfd);
                    model.addAttribute("year_mfd",year_mfd);
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode13 = e.getStatusCode().value();
                String messageWeb_mfd = "Данных нет, код ошибки - " + statusCode13;
                model.addAttribute("messageWeb_mfd",messageWeb_mfd);
                return "redirect:/allForPC/mfd";
            }
        }
        return "redirect:/allForPC/mfd";
    }

    @DeleteMapping("/allForPC/mfd/id")
    public String deleteMFDWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/mfd";
    }

    @PutMapping("/allForPC/mfd/number")
    public String updateMFDPrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/mfd";
    }

    @DeleteMapping("/allForPC/mfd/number")
    public String deleteMFD(@RequestParam("number") Long number){
        Device mfd = deviceService.getByIdDevice(number);
        if (mfd != null){
            String imageMFD = mfd.getImage();
            if (deviceService.deleteDevice(mfd.getId())){
                deviceService.deleteOldImage(imageMFD);
            }
        }
        return "redirect:/allForPC/mfd";
    }
}
