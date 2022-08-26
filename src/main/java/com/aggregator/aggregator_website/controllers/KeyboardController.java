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
public class KeyboardController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final StatisticDataConvector statisticDataConvector;

    @GetMapping("/allForPC/keyboard")
    public String getKeyboardPage(Model model, String messageWeb_keyB, String siteName_keyB,
                                  String month_keyB, String year_keyB){
        WebsiteDto webKeyB = websiteService.addDefaultWebsite();
        List<WebsiteDto> keyboardWebsites = websiteService.findAllWebsiteBySection("Клавиатура", webKeyB);
        List<WebsiteDto> ratingTrafficKeyboards = websiteService.findAllWebsiteRatingTrafficBySection("Клавиатура",webKeyB);
        List<WebsiteDto> ratingKeyboardWebsites = websiteService.findAllWebsiteRatingBySection("Клавиатура",webKeyB);

        DeviceDto deviceKeyB = deviceService.addDefaultDevice();
        List<DeviceDto> keyboardOffices = deviceService.findAllDevicesBySectionAndDestination("Клавиатура","Офис",deviceKeyB);
        List<DeviceDto> keyboardGames = deviceService.findAllDevicesBySectionAndDestination("Клавиатура","Игры", deviceKeyB);
        List<DeviceDto> keyboardHomes = deviceService.findAllDevicesBySectionAndDestination("Клавиатура","Дом",deviceKeyB);

        model.addAttribute("keyboardWebsites",keyboardWebsites);
        model.addAttribute("ratingTrafficKeyboards",ratingTrafficKeyboards);
        model.addAttribute("ratingKeyboardWebsites",ratingKeyboardWebsites);

        model.addAttribute("keyboardOffices",keyboardOffices);
        model.addAttribute("keyboardGames",keyboardGames);
        model.addAttribute("keyboardHomes",keyboardHomes);

        if(messageWeb_keyB != null){
            model.addAttribute("messageWeb_keyB",messageWeb_keyB);
        }

        if(siteName_keyB != null){
            model.addAttribute("siteName_keyB",siteName_keyB);
        }

        if(month_keyB != null){
            model.addAttribute("month_keyB",month_keyB);
        }

        if(year_keyB != null){
            model.addAttribute("year_keyB",year_keyB);
        }

        return "equipments/keyboard";
    }

    @PutMapping("/allForPC/keyboard/id")
    public String updateKeyboardWebsite(@RequestParam("id") Long id, Model model){
        WebsiteDto keyBWebsite = websiteService.getWebsiteById(id);
        if(keyBWebsite != null){
            try{
                SiteAnalysisDto siteKeyB = siteAnalysisClient.getSiteAnalysis(keyBWebsite.getSiteName());
                if(siteKeyB != null){
                    InfoTrafficWebsite trafficKeyBWebsite = websiteService.getInfoTrafficWebsiteDto(keyBWebsite.getId(), siteKeyB);
                    String siteName_keyB = siteKeyB.getSiteName();
                    String month_keyB = statisticDataConvector.monthConvector(siteKeyB.getEngagments().getMonth());
                    String year_keyB = siteKeyB.getEngagments().getYear();
                    websiteService.updateWebsite(trafficKeyBWebsite);

                    model.addAttribute("siteName_keyB",siteName_keyB);
                    model.addAttribute("month_keyB",month_keyB);
                    model.addAttribute("year_keyB",year_keyB);
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode11 = e.getStatusCode().value();
                String messageWeb_keyB = "Данных нет, код ошибки - " + statusCode11;
                model.addAttribute("messageWeb_keyB",messageWeb_keyB);
                return "redirect:/allForPC/keyboard";
            }
        }
        return "redirect:/allForPC/keyboard";
    }

    @DeleteMapping("/allForPC/keyboard/id")
    public String deleteKeyboardWebsite(@RequestParam("id") Long id){
        websiteService.deleteWebsite(id);
        return "redirect:/allForPC/keyboard";
    }

    @PutMapping("/allForPC/keyboard/number")
    public String updateKeyboardPrice(@RequestParam("number") Long number){
        try {
            deviceService.updateDevicePrice(number);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/allForPC/keyboard";
    }

    @DeleteMapping("/allForPC/keyboard/number")
    public String deleteKeyboard(@RequestParam("number") Long number){
        Device keyboard = deviceService.getByIdDevice(number);
        if(keyboard != null){
            String imageKeyB = keyboard.getImage();
            if (deviceService.deleteDevice(keyboard.getId())){
                deviceService.deleteOldImage(imageKeyB);
            }
        }
        return "redirect:/allForPC/keyboard";
    }
}
