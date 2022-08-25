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
public class CoolerForCorpusController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/cooler-corpus")
    public String getCoolerForCorpusPage(Model model, String messageWeb_cooler, String siteName_cooler,
                                         String month_cooler, String year_cooler){
        WebsiteDto webCooler = websiteService.addDefaultWebsite();
        List<WebsiteDto> coolerWebsites = websiteService.findAllWebsiteBySection("Cooler-Corpus",webCooler);
        List<WebsiteDto> ratingTrafficCoolers = websiteService.findAllWebsiteRatingTrafficBySection("Cooler-Corpus",webCooler);
        List<WebsiteDto> ratingCoolerWebsites = websiteService.findAllWebsiteRatingBySection("Cooler-Corpus",webCooler);

        DeviceDto coolerDevice = deviceService.addDefaultDevice();
        List<DeviceDto> coolerOffices = deviceService.findAllDevicesBySectionAndDestination("Cooler-Corpus","Офис",coolerDevice);
        List<DeviceDto> coolerGames = deviceService.findAllDevicesBySectionAndDestination("Cooler-Corpus","Игры",coolerDevice);
        List<DeviceDto> coolerHomes = deviceService.findAllDevicesBySectionAndDestination("Cooler-Corpus","Дом",coolerDevice);

        model.addAttribute("coolerWebsites",coolerWebsites);
        model.addAttribute("ratingTrafficCoolers",ratingTrafficCoolers);
        model.addAttribute("ratingCoolerWebsites",ratingCoolerWebsites);

        model.addAttribute("coolerOffices",coolerOffices);
        model.addAttribute("coolerGames",coolerGames);
        model.addAttribute("coolerHomes",coolerHomes);

        if(messageWeb_cooler != null){
            model.addAttribute("messageWeb_cooler",messageWeb_cooler);
        }

        if(siteName_cooler != null){
            model.addAttribute("siteName_cooler",siteName_cooler);
        }

        if(month_cooler != null){
            model.addAttribute("month_cooler",month_cooler);
        }

        if(year_cooler != null){
            model.addAttribute("year_cooler",year_cooler);
        }

        return "equipments/cooler-corpus";
    }

    @PutMapping("/allForPC/cooler-corpus/id")
    public String updateCoolerWebsite(@RequestParam("id") Long id, Model model){
        WebsiteDto coolerWebsite = websiteService.getWebsiteById(id);
        if(coolerWebsite != null){
            try{
                SiteAnalysisDto siteAnalysisCooler = siteAnalysisClient.getSiteAnalysis(coolerWebsite.getSiteName());
                if(siteAnalysisCooler != null){
                    InfoTrafficWebsite coolerInfoTraffic = websiteService.getInfoTrafficWebsiteDto(coolerWebsite.getId(),siteAnalysisCooler);
                    String siteName_cooler = siteAnalysisCooler.getSiteName();
                    String month_cooler = statisticDataConvector.monthConvector(siteAnalysisCooler.getEngagments().getMonth());
                    String year_cooler = siteAnalysisCooler.getEngagments().getYear();
                    websiteService.updateWebsite(coolerInfoTraffic);

                    model.addAttribute("siteName_cooler",siteName_cooler);
                    model.addAttribute("month_cooler",month_cooler);
                    model.addAttribute("year_cooler",year_cooler);
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode8 = e.getStatusCode().value();
                String messageWeb_cooler = "Данных нет, код ошибки - " + statusCode8;
                model.addAttribute("messageWeb_cooler", messageWeb_cooler);
                return "redirect:/allForPC/cooler-corpus";
            }
        }
        return "redirect:/allForPC/cooler-corpus";
    }

    @DeleteMapping("/allForPC/cooler-corpus/id")
    public String deleteCoolerWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/cooler-corpus";
    }

    @PutMapping("/allForPC/cooler-corpus/number")
    public String updateCoolerPrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/cooler-corpus";
    }

    @DeleteMapping("/allForPC/cooler-corpus/number")
    public String deleteCoolerForCorpus(@RequestParam("number") Long number){
        Device coolerForCorpus = deviceService.getByIdDevice(number);
        if (coolerForCorpus != null){
            String imageCooler = coolerForCorpus.getImage();
            if(deviceService.deleteDevice(coolerForCorpus.getId())){
                deviceService.deleteOldImage(imageCooler);
            }
        }
        return "redirect:/allForPC/cooler-corpus";
    }
}
