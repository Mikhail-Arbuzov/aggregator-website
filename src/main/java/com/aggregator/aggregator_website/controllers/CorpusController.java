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
public class CorpusController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/corpus")
    public String getCorpusPage(Model model, String messageWeb_corpus, String siteName_corpus,
                                String month_corpus, String year_corpus){
        WebsiteDto webCorpus = websiteService.addDefaultWebsite();
        List<WebsiteDto> corpusWebsites = websiteService.findAllWebsiteBySection("Корпус",webCorpus);
        List<WebsiteDto> corpusTrafficRatings = websiteService.findAllWebsiteRatingTrafficBySection("Корпус",webCorpus);
        List<WebsiteDto> corpusWebsiteRatings = websiteService.findAllWebsiteRatingBySection("Корпус",webCorpus);

        DeviceDto corpusDevice = deviceService.addDefaultDevice();
        List<DeviceDto> corpusOffices = deviceService.findAllDevicesBySectionAndDestination("Корпус","Офис",corpusDevice);
        List<DeviceDto> corpusGames = deviceService.findAllDevicesBySectionAndDestination("Корпус","Игры",corpusDevice);
        List<DeviceDto> corpusHomes = deviceService.findAllDevicesBySectionAndDestination("Корпус","Дом",corpusDevice);

        model.addAttribute("corpusWebsites",corpusWebsites);
        model.addAttribute("corpusTrafficRatings",corpusTrafficRatings);
        model.addAttribute("corpusWebsiteRatings",corpusWebsiteRatings);

        model.addAttribute("corpusOffices",corpusOffices);
        model.addAttribute("corpusGames",corpusGames);
        model.addAttribute("corpusHomes",corpusHomes);

        if(messageWeb_corpus != null){
            model.addAttribute("messageWeb_corpus",messageWeb_corpus);
        }

        if(siteName_corpus != null){
            model.addAttribute("siteName_corpus",siteName_corpus);
        }

        if(month_corpus != null){
            model.addAttribute("month_corpus",month_corpus);
        }

        if(year_corpus != null){
            model.addAttribute("year_corpus",year_corpus);
        }
        return "equipments/corpus";
    }

    @PutMapping("/allForPC/corpus/id")
    public String updateCorpusWebsite(@RequestParam("id") Long id, Model model){
        WebsiteDto corpusWebsite = websiteService.getWebsiteById(id);
        if(corpusWebsite != null){
            try{
                SiteAnalysisDto corpusSiteAnalysis = siteAnalysisClient.getSiteAnalysis(corpusWebsite.getSiteName());
                if(corpusSiteAnalysis != null){
                    InfoTrafficWebsite trafficCorpusWebsite = websiteService.getInfoTrafficWebsiteDto(corpusWebsite.getId(),corpusSiteAnalysis);
                    String siteName_corpus = corpusSiteAnalysis.getSiteName();
                    String month_corpus = statisticDataConvector.monthConvector(corpusSiteAnalysis.getEngagments().getMonth());
                    String year_corpus = corpusSiteAnalysis.getEngagments().getYear();
                    websiteService.updateWebsite(trafficCorpusWebsite);

                    model.addAttribute("siteName_corpus",siteName_corpus);
                    model.addAttribute("month_corpus",month_corpus);
                    model.addAttribute("year_corpus",year_corpus);
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode9 = e.getStatusCode().value();
                String messageWeb_corpus = "Данных нет, код ошибки - " + statusCode9;
                model.addAttribute("messageWeb_corpus",messageWeb_corpus);
                return "redirect:/allForPC/corpus";
            }
        }
        return "redirect:/allForPC/corpus";
    }

    @DeleteMapping("/allForPC/corpus/id")
    public String deleteCorpusWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/corpus";
    }

    @PutMapping("/allForPC/corpus/number")
    public String updateCorpusPrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/corpus";
    }

    @DeleteMapping("/allForPC/corpus/number")
    public String deleteCorpus(@RequestParam("number") Long number){
        Device corpus = deviceService.getByIdDevice(number);
        if(corpus != null){
            String imageCorpus = corpus.getImage();
            if(deviceService.deleteDevice(corpus.getId())){
                deviceService.deleteOldImage(imageCorpus);
            }
        }
        return "redirect:/allForPC/corpus";
    }
}
