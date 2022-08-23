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
public class CoolerForCpuController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/cooler-cpu")
    public String getCoolerForCpuPage(Model model, String messageWeb2, String siteName2,
                                     String month2, String year2){
        WebsiteDto websiteDef = websiteService.addDefaultWebsite();
        List<WebsiteDto> websiteCpuCoolers = websiteService.findAllWebsiteBySection("Cooler-Cpu",websiteDef);
        List<WebsiteDto> trafficWebsiteRatings = websiteService.findAllWebsiteRatingTrafficBySection("Cooler-Cpu",websiteDef);
        List<WebsiteDto> coolerCpuWebsiteRatings = websiteService.findAllWebsiteRatingBySection("Cooler-Cpu",websiteDef);

        DeviceDto deviceDef = deviceService.addDefaultDevice();
        List<DeviceDto> officeCpuForCoolers = deviceService.findAllDevicesBySectionAndDestination("Cooler-Cpu","Офис",deviceDef);
        List<DeviceDto> gameCpuCoolers = deviceService.findAllDevicesBySectionAndDestination("Cooler-Cpu","Игры",deviceDef);
        List<DeviceDto> homeCpuCoolers = deviceService.findAllDevicesBySectionAndDestination("Cooler-Cpu","Дом",deviceDef);

        model.addAttribute("websiteCpuCoolers", websiteCpuCoolers);
        model.addAttribute("trafficWebsiteRatings",trafficWebsiteRatings);
        model.addAttribute("coolerCpuWebsiteRatings",coolerCpuWebsiteRatings);

        model.addAttribute("officeCpuForCoolers", officeCpuForCoolers);
        model.addAttribute("gameCpuCoolers",gameCpuCoolers);
        model.addAttribute("homeCpuCoolers",homeCpuCoolers);


        if(siteName2 != null){
            model.addAttribute("siteName2",siteName2);
        }
        if (month2 != null){
            model.addAttribute("month2", month2);
        }
        if (year2 != null ){
            model.addAttribute("year2",year2);
        }
        if(messageWeb2 != null){
            model.addAttribute("messageWeb2",messageWeb2);
        }

        return "equipments/cooler-cpu";
    }

    @PutMapping("/allForPC/cooler-cpu/id")
    public String updateWebsiteCpuCoolerById(@RequestParam("id") Long id,Model model){
        WebsiteDto website = websiteService.getWebsiteById(id);
        if(website != null){
            try{
                SiteAnalysisDto siteDto = siteAnalysisClient.getSiteAnalysis(website.getSiteName());
                if(siteDto != null){
                    InfoTrafficWebsite infoTrafficWebsite = websiteService.getInfoTrafficWebsiteDto(website.getId(),siteDto);
                    String siteName2 = siteDto.getSiteName();
                    String month2 = statisticDataConvector.monthConvector(siteDto.getEngagments().getMonth());
                    String year2 = siteDto.getEngagments().getYear();
                    websiteService.updateWebsite(infoTrafficWebsite);
                    model.addAttribute("siteName2",siteName2);
                    model.addAttribute("month2", month2);
                    model.addAttribute("year2",year2);
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode2 = e.getStatusCode().value();
                String messageWeb2 = "Данных нет, код ошибки - " + statusCode2;
                model.addAttribute("messageWeb2",messageWeb2);
                return "redirect:/allForPC/cooler-cpu";
            }
        }
        return "redirect:/allForPC/cooler-cpu";
    }

    @DeleteMapping("/allForPC/cooler-cpu/id")
    public String deleteCpuCoolerWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/cooler-cpu";
    }

    @PutMapping("/allForPC/cooler-cpu/number")
    public String updateCoolerCpuPrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/cooler-cpu";
    }

    @DeleteMapping("/allForPC/cooler-cpu/number")
    public String deleteCoolerCpu(@RequestParam("number") Long number){
        Device coolerForCpu = deviceService.getByIdDevice(number);
        if(coolerForCpu != null){
            String image1 = coolerForCpu.getImage();
            if(deviceService.deleteDevice(coolerForCpu.getId())){
                deviceService.deleteOldImage(image1);
            }
        }
        return "redirect:/allForPC/cooler-cpu";
    }
}
