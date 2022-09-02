package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.CompilationRequest;
import com.aggregator.aggregator_website.dto.DeviceDto;
import com.aggregator.aggregator_website.dto.ProductCreateDto;
import com.aggregator.aggregator_website.entities.Device;
import com.aggregator.aggregator_website.entities.User;
import com.aggregator.aggregator_website.repository.UserRepository;
import com.aggregator.aggregator_website.services.CompilationService;
import com.aggregator.aggregator_website.services.DeviceService;
import com.aggregator.aggregator_website.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;
    private final DeviceService deviceService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/allForPC/compilation")
    public String getCompilationPage(Model model){
        DeviceDto deviceDto = deviceService.addDefaultDevice();
        User actualUser = userService.getCurrentUser();
        if(actualUser != null){
            model.addAttribute("actualUser",actualUser);
        }
        CompilationRequest compilationRequest = new CompilationRequest();
        ProductCreateDto productCreateDto = new ProductCreateDto();
        model.addAttribute("compilationRequest",compilationRequest);
        model.addAttribute("productCreateDto",productCreateDto);

        List<DeviceDto> processors = deviceService.getAllDevicesBySection("Процессор",deviceDto);
        model.addAttribute("processors",processors);

        List<DeviceDto> motherBs = deviceService.getAllDevicesBySection("Материнка",deviceDto);
        model.addAttribute("motherBs",motherBs);

        List<DeviceDto> cpuCoolers = deviceService.getAllDevicesBySection("Cooler-Cpu",deviceDto);
        model.addAttribute("cpuCoolers",cpuCoolers);

        List<DeviceDto> rams = deviceService.getAllDevicesBySection("ОЗУ",deviceDto);
        model.addAttribute("rams",rams);

        List<DeviceDto> ssdDevices = deviceService.getAllDevicesBySection("SSD",deviceDto);
        model.addAttribute("ssdDevices",ssdDevices);

        List<DeviceDto> hddDevices = deviceService.getAllDevicesBySection("HDD",deviceDto);
        model.addAttribute("hddDevices",hddDevices);

        List<DeviceDto> powerSDevices = deviceService.getAllDevicesBySection("БП",deviceDto);
        model.addAttribute("powerSDevices",powerSDevices);

        List<DeviceDto> videoCards = deviceService.getAllDevicesBySection("Видеокарта",deviceDto);
        model.addAttribute("videoCards",videoCards);

        List<DeviceDto> coolers = deviceService.getAllDevicesBySection("Cooler-Corpus",deviceDto);
        model.addAttribute("coolers", coolers);

        List<DeviceDto> corpusDevices = deviceService.getAllDevicesBySection("Корпус",deviceDto);
        model.addAttribute("corpusDevices",corpusDevices);

        List<DeviceDto> monitors = deviceService.getAllDevicesBySection("Монитор",deviceDto);
        model.addAttribute("monitors",monitors);

        List<DeviceDto> mouses = deviceService.getAllDevicesBySection("Мышка",deviceDto);
        model.addAttribute("mouses",mouses);

        List<DeviceDto> keyBDevices = deviceService.getAllDevicesBySection("Клавиатура",deviceDto);
        model.addAttribute("keyBDevices",keyBDevices);

        List<DeviceDto> mfdDevices = deviceService.getAllDevicesBySection("МФУ",deviceDto);
        model.addAttribute("mfdDevices",mfdDevices);
        
        return "compilation-form";
    }

    @PostMapping("/allForPC/compilation/id")
    public String addCompilation(@ModelAttribute("compilationRequest") CompilationRequest compilationRequest,
                                 @RequestParam("id") Long id){
        if (id != null && id > 0){
            User user1 = userRepository.getById(id);
            if (user1 != null){
                compilationService.addCompilation(compilationRequest,user1.getDetail().getAvatarka(),
                        user1.getDetail().getFirstName(), user1.getDetail().getSecondName());
            }
            else{
                return "redirect:/allForPC/compilation";
            }
        }
        else{
            return "redirect:/allForPC/compilation";
        }

        return "redirect:/allForPC/configurator";
    }

    @PostMapping("/allForPC/compilation/number")
    public String createCompilation(@ModelAttribute("productCreateDto") ProductCreateDto productCreateDto,
                                    @RequestParam("number") Long number){
        if(number != null && number > 0){
            User user = userRepository.getById(number);
            if(user != null){
                compilationService.createCompilation(productCreateDto,user.getDetail().getAvatarka(),
                        user.getDetail().getFirstName(),user.getDetail().getSecondName());
            }
            else{
                return "redirect:/allForPC/compilation";
            }
        }
        else{
            return "redirect:/allForPC/compilation";
        }

        return "redirect:/allForPC/configurator";
    }

}
