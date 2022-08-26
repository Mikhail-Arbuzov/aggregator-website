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
public class MonitorController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/monitor")
    public String getMonitorPage(Model model, String messageWeb_monitor, String siteName_monitor,
                                 String month_monitor, String year_monitor){
        WebsiteDto webMonitor = websiteService.addDefaultWebsite();
        List<WebsiteDto> monitorWebsites = websiteService.findAllWebsiteBySection("Монитор",webMonitor);
        List<WebsiteDto> ratingTrafficMonitors = websiteService.findAllWebsiteRatingTrafficBySection("Монитор",webMonitor);
        List<WebsiteDto> ratingMonitorWebsites = websiteService.findAllWebsiteRatingBySection("Монитор",webMonitor);

        DeviceDto monitorDevice = deviceService.addDefaultDevice();
        List<DeviceDto> monitorOffices = deviceService.findAllDevicesBySectionAndDestination("Монитор","Офис",monitorDevice);
        List<DeviceDto> monitorGames = deviceService.findAllDevicesBySectionAndDestination("Монитор","Игры",monitorDevice);
        List<DeviceDto> monitorHomes = deviceService.findAllDevicesBySectionAndDestination("Монитор","Дом",monitorDevice);

        model.addAttribute("monitorWebsites",monitorWebsites);
        model.addAttribute("ratingTrafficMonitors",ratingTrafficMonitors);
        model.addAttribute("ratingMonitorWebsites",ratingMonitorWebsites);

        model.addAttribute("monitorOffices",monitorOffices);
        model.addAttribute("monitorGames",monitorGames);
        model.addAttribute("monitorHomes",monitorHomes);

        if(messageWeb_monitor != null){
            model.addAttribute("messageWeb_monitor",messageWeb_monitor);
        }

        if(siteName_monitor != null){
            model.addAttribute("siteName_monitor",siteName_monitor);
        }

        if(month_monitor != null){
            model.addAttribute("month_monitor",month_monitor);
        }

        if(year_monitor != null){
            model.addAttribute("year_monitor",year_monitor);
        }

        return "equipments/monitor";
    }

    @PutMapping("/allForPC/monitor/id")
    public String updateMonitorWebsite(@RequestParam("id") Long id, Model model){
        WebsiteDto websiteMonitor = websiteService.getWebsiteById(id);
        if(websiteMonitor != null){
            try{
                SiteAnalysisDto siteMonitorAnalysis = siteAnalysisClient.getSiteAnalysis(websiteMonitor.getSiteName());
                if(siteMonitorAnalysis != null){
                    InfoTrafficWebsite trafficMonitorWebsite = websiteService.getInfoTrafficWebsiteDto(websiteMonitor.getId(),siteMonitorAnalysis);
                    String siteName_monitor = siteMonitorAnalysis.getSiteName();
                    String month_monitor = statisticDataConvector.monthConvector(siteMonitorAnalysis.getEngagments().getMonth());
                    String year_monitor = siteMonitorAnalysis.getEngagments().getYear();
                    websiteService.updateWebsite(trafficMonitorWebsite);

                    model.addAttribute("siteName_monitor",siteName_monitor);
                    model.addAttribute("month_monitor",month_monitor);
                    model.addAttribute("year_monitor",year_monitor);
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode10 = e.getStatusCode().value();
                String messageWeb_monitor = "Данных нет, код ошибки - " + statusCode10;
                model.addAttribute("messageWeb_monitor",messageWeb_monitor);
                return "redirect:/allForPC/monitor";
            }
        }
        return "redirect:/allForPC/monitor";
    }

    @DeleteMapping("/allForPC/monitor/id")
    public String deleteMonitorWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/monitor";
    }

    @PutMapping("/allForPC/monitor/number")
    public String updateMonitorPrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/monitor";
    }

    @DeleteMapping("/allForPC/monitor/number")
    public String deleteMonitor(@RequestParam("number") Long number){
        Device monitor = deviceService.getByIdDevice(number);
        if(monitor != null){
            String imageMonitor = monitor.getImage();
            if (deviceService.deleteDevice(monitor.getId())){
                deviceService.deleteOldImage(imageMonitor);
            }
        }
        return "redirect:/allForPC/monitor";
    }
}
