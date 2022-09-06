package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.*;
import com.aggregator.aggregator_website.dto.siteanalysis.SiteAnalysisDto;
import com.aggregator.aggregator_website.exceptionhandlers.notifications.ErrorMessage;
import com.aggregator.aggregator_website.services.*;
import com.aggregator.aggregator_website.services.globalerrors.ClientError;
import com.aggregator.aggregator_website.services.globalerrors.ErrorIO;
import com.aggregator.aggregator_website.services.globalerrors.ErrorMalformedURL;
import com.aggregator.aggregator_website.services.globalerrors.ErrorUnknownHost;
import com.aggregator.aggregator_website.services.validation.CheckURLValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class AdminController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;
    private final DeviceService deviceService;
    private final ConfiguratorService configuratorService;
    private final AntivirusSoftService antivirusSoftService;
    private final SystemSourceService systemSourceService;
    private final OfficeProgramService officeProgramService;
    private final SoftwareService softwareService;
    private final ArticleService articleService;

    @GetMapping("/admin")
    public String getAdminPage(){
        return "admin";
    }

    @GetMapping("/admin/error")
    public  String getError(@ModelAttribute("errormessage") ErrorMessage errorMessage){
        return "error/error";
    }

    @GetMapping("/admin/section-equipment")
    public String getEquipmentSectionSite(Model model, Double bounceRatePercent,
                                          String emptyDate,Integer errCod,
                                          Integer errCod1,String errFile){
        DomainRequest domainRequest = new DomainRequest();
        model.addAttribute("domainRequest",domainRequest);
        TransferInfoWebsiteRequest infoWebsiteRequest = new TransferInfoWebsiteRequest();
        model.addAttribute("infoWebsiteRequest",infoWebsiteRequest);
        CreateDeviceDto createDeviceDto = new CreateDeviceDto();
        model.addAttribute("createDeviceDto",createDeviceDto);

        if(bounceRatePercent != null){
            model.addAttribute("bounceRatePercent",bounceRatePercent);
        }
        if(emptyDate !=null){
            model.addAttribute("emptyDate",emptyDate);
        }

        if(errCod != null){
            model.addAttribute("errCod",errCod);
        }

        if(errCod1 != null){
            model.addAttribute("errCod1",errCod1);
        }

        if(errFile != null){
            model.addAttribute("errFile",errFile);
        }

        return "sitesections/komplect";
    }

    @GetMapping("/admin/section-configurator")
    public String getConfiguratorSection(Model model){
        SourceRequest sourceConfigurator = new SourceRequest();
        model.addAttribute("sourceConfigurator",sourceConfigurator);
        return "sitesections/configurator-add";
    }

    @GetMapping("/admin/section-antivirus")
    public String getAntivirusSection(Model model){
        UrlRequest urlAntivirusRequest = new UrlRequest();
        model.addAttribute("urlAntivirusRequest", urlAntivirusRequest);
        return "sitesections/section-antivirus";
    }

    @GetMapping("/admin/section-operative")
    public String getOperativeSection(Model model){
        SourceRequest sourceOperative = new SourceRequest();
        model.addAttribute("sourceOperative",sourceOperative);
        return "sitesections/section-operative";
    }

    @GetMapping("/admin/section-office")
    public String getOfficeSection(Model model){
        UrlRequest urlOfficeProgram = new UrlRequest();
        model.addAttribute("urlOfficeProgram",urlOfficeProgram);
        return "sitesections/section-office";
    }

    @GetMapping("/admin/section-soft")
    public String getSoftSection(Model model){
        SourceRequest sourceSoft = new SourceRequest();
        model.addAttribute("sourceSoft",sourceSoft);
        return "sitesections/section-soft";
    }

    @GetMapping("/admin/section-article")
    public String getArticleSection(Model model){
        UrlRequest urlArticle = new UrlRequest();
        model.addAttribute("urlArticle",urlArticle);
        return "sitesections/section-article";
    }

    @PostMapping("/admin/result-bounceRate")
    public String getBounceRate(@Valid @ModelAttribute("domainRequest")DomainRequest domainRequest,
                                BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            TransferInfoWebsiteRequest infoWebsiteRequest = new TransferInfoWebsiteRequest();
            model.addAttribute("infoWebsiteRequest",infoWebsiteRequest);
            CreateDeviceDto createDeviceDto = new CreateDeviceDto();
            model.addAttribute("createDeviceDto",createDeviceDto);
            return "sitesections/komplect";
        }
        else {
            try{
                SiteAnalysisDto analysisDto = siteAnalysisClient.getSiteAnalysis(domainRequest.getValue());
                if(analysisDto != null){
                    String bounceRate = analysisDto.getEngagments().getBounceRate();
                    double percent = Double.parseDouble(bounceRate);
                    double scale = Math.pow(10,2);
                    Double bounceRatePercent = Math.ceil(percent*100*scale)/scale;
                    model.addAttribute("bounceRatePercent",bounceRatePercent);
                    return "redirect:/admin/section-equipment";
                }
                else{
                    String emptyDate ="not date";
                    model.addAttribute("emptyDate",emptyDate);
                    return "redirect:/admin/section-equipment";
                }
            }
            catch (HttpClientErrorException e){
                e.getMessage();
                int statusCode = e.getStatusCode().value();
                if(statusCode == 404){
                    Integer errCod = statusCode;
                    model.addAttribute("errCod",errCod);
                }
                else {
                    Integer errCod1 = statusCode;
                    model.addAttribute("errCod1",errCod1);
                }

                return "redirect:/admin/section-equipment";
            }
//            catch (RuntimeException ex){
//                ex.getMessage();
//                Integer errCod1 = 404;
//                model.addAttribute("errCod1",errCod1);
//                return "redirect:/admin/section-equipment";
//            }
        }

    }

    @PostMapping("/admin/addWebsite")
    public String addWebsite( @Valid @ModelAttribute("infoWebsiteRequest") TransferInfoWebsiteRequest infoWebsiteRequest,
                              BindingResult bindingResult,Model model) throws IOException,NullPointerException  {

        addGlobalErrorsValidURL(bindingResult,"infoWebsiteRequest");
        if(bindingResult.hasErrors()){
            DomainRequest domainRequest = new DomainRequest();
            model.addAttribute("domainRequest",domainRequest);
            CreateDeviceDto createDeviceDto = new CreateDeviceDto();
            model.addAttribute("createDeviceDto",createDeviceDto);
            Double bounceRatePercent = 30.3;
            model.addAttribute("bounceRatePercent",bounceRatePercent);
            return "sitesections/komplect";
        }
        else {
            try{
                SiteAnalysisDto analysisDto = siteAnalysisClient.getSiteAnalysis(infoWebsiteRequest.getDomainSite());
                if(analysisDto != null){
                    websiteService.addWebsite(analysisDto,infoWebsiteRequest.getDomainSite(),
                            infoWebsiteRequest.getUrlSite(), infoWebsiteRequest.getSection());
                }
            }
            catch (HttpClientErrorException ex){
                int statusCode = ex.getStatusCode().value();
                String er = "Не удалось передать данные. Код ошибки - " + statusCode;
                FieldError fieldDomainError = new FieldError("infoWebsiteRequest","domainSite",er);
                bindingResult.addError(fieldDomainError);
                CreateDeviceDto createDeviceDto = new CreateDeviceDto();
                model.addAttribute("createDeviceDto",createDeviceDto);
                DomainRequest domainRequest = new DomainRequest();
                model.addAttribute("domainRequest",domainRequest);
                Double bounceRatePercent = 30.3;
                model.addAttribute("bounceRatePercent",bounceRatePercent);
                return "sitesections/komplect";
            }
        }

        return "redirect:" + infoWebsiteRequest.getDataTransferPage();
    }

    @PostMapping("/admin/addDevice")
    public String addDevice(@Valid @ModelAttribute("createDeviceDto")CreateDeviceDto createDeviceDto,
                            BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            DomainRequest domainRequest = new DomainRequest();
            model.addAttribute("domainRequest",domainRequest);
            TransferInfoWebsiteRequest infoWebsiteRequest = new TransferInfoWebsiteRequest();
            model.addAttribute("infoWebsiteRequest",infoWebsiteRequest);
            return "sitesections/komplect";
        }

        if(createDeviceDto.getFile().isEmpty()){
            model.addAttribute("errFile","Изображение не было выбрано");
            DomainRequest domainRequest = new DomainRequest();
            model.addAttribute("domainRequest",domainRequest);
            TransferInfoWebsiteRequest infoWebsiteRequest = new TransferInfoWebsiteRequest();
            model.addAttribute("infoWebsiteRequest",infoWebsiteRequest);
            return "sitesections/komplect";
        }
        double megabyte = createDeviceDto.getFile().getSize() * 0.00000095367432;
        if(megabyte >= 1 || createDeviceDto.getFile().getSize() >= 1048576){
            model.addAttribute("errFile","Размер изображения должен быть не более 1 МБ");
            DomainRequest domainRequest = new DomainRequest();
            model.addAttribute("domainRequest",domainRequest);
            TransferInfoWebsiteRequest infoWebsiteRequest = new TransferInfoWebsiteRequest();
            model.addAttribute("infoWebsiteRequest",infoWebsiteRequest);
            return "sitesections/komplect";
        }

        String originalFileName = createDeviceDto.getFile().getOriginalFilename();
        String extension1 = originalFileName.substring(originalFileName.lastIndexOf("."));

        if (!extension1.equals(".jpg")){
            model.addAttribute("errFile","Изображение должно быть с расширением .jpg");
            DomainRequest domainRequest = new DomainRequest();
            model.addAttribute("domainRequest",domainRequest);
            TransferInfoWebsiteRequest infoWebsiteRequest = new TransferInfoWebsiteRequest();
            model.addAttribute("infoWebsiteRequest",infoWebsiteRequest);
            return "sitesections/komplect";
        }

        final String PATH_UPLOAD ="target\\classes\\static\\img\\components\\";
        String uuidFile = UUID.randomUUID().toString();
        DeviceDto deviceDto = new DeviceDto();

        try {
            byte[] arrBytes = createDeviceDto.getFile().getBytes();
            Path path = Paths.get(PATH_UPLOAD + uuidFile + "."+ createDeviceDto.getFile().getOriginalFilename());
            Files.write(path,arrBytes);
            deviceDto.setImage("img/components/"+ uuidFile + "."+ createDeviceDto.getFile().getOriginalFilename());

            deviceDto.setName(createDeviceDto.getName());
            deviceDto.setAveragePrice(createDeviceDto.getAveragePrice());
            deviceDto.setDescription(createDeviceDto.getDescription());
            deviceDto.setDestination(createDeviceDto.getDestination());
            deviceDto.setSection(createDeviceDto.getSection());
            deviceDto.setMonitoringPrices(new ArrayList<>());
            deviceService.addDevice(deviceDto,createDeviceDto.getCitilinkURL(),createDeviceDto.getRegardURL(),
                    createDeviceDto.getComputerMarketURL(),createDeviceDto.getQukeURL(),createDeviceDto.getKnsURL());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:" + createDeviceDto.getUrlPage();
    }

    @PostMapping("/admin/addConfigurator")
    public String addConfigurator(@Valid @ModelAttribute("sourceConfigurator") SourceRequest sourceConfigurator,
                                  BindingResult bindingResult){
        addGlobalErrorsValidURL(bindingResult,"sourceConfigurator");
        if(bindingResult.hasErrors()){
            return "sitesections/configurator-add";
        }
        else{
            try {
                SiteAnalysisDto siteConfAnalysis = siteAnalysisClient.getSiteAnalysis(sourceConfigurator.getDomain());
                if(siteConfAnalysis != null){
                    BigDecimal resultVisit = new BigDecimal(siteConfAnalysis.getEngagments().getVisits());
                    BigDecimal res = resultVisit.setScale(0, RoundingMode.HALF_UP);
                    long value = res.longValue();
                    if(value > 100000){
                        configuratorService.addConfigurator(siteConfAnalysis,sourceConfigurator.getDomain(),sourceConfigurator.getUrlSite());
                    }
                    else{
                        String err = "Число визитов на данном сайте меньше 100 тыс.";
                        FieldError fieldErrorDomain = new FieldError("sourceConfigurator","domain",err);
                        bindingResult.addError(fieldErrorDomain);
                        return "sitesections/configurator-add";
                    }

                }
            }
            catch (HttpClientErrorException ex){
                int statusCode1 = ex.getStatusCode().value();
                String er1 = "Не удалось передать данные. Код ошибки - " + statusCode1;
                FieldError fieldDomainError = new FieldError("sourceConfigurator","domain",er1);
                bindingResult.addError(fieldDomainError);
                return "sitesections/configurator-add";
            }
        }
        return "redirect:/allForPC/configurator";
    }

    @PostMapping("/admin/addAntivirus")
    public String addAntivirusSoft(@Valid @ModelAttribute("urlAntivirusRequest") UrlRequest urlAntivirusRequest,
                                   BindingResult bindingResult) throws IOException,NullPointerException {
        addGlobalErrorsValidURL(bindingResult,"urlAntivirusRequest");
        if (bindingResult.hasErrors()){
            return "sitesections/section-antivirus";
        }
        else{
            antivirusSoftService.addAntivirusSoft(urlAntivirusRequest.getUrlSite());
            return "redirect:/allForPC/antivirus-soft";
        }
    }
    @PostMapping("/admin/addSystemSource")
    public String addSystemSource(@Valid @ModelAttribute("sourceOperative") SourceRequest sourceOperative,
                                  BindingResult bindingResult) throws IOException,NullPointerException {
        addGlobalErrorsValidURL(bindingResult,"sourceOperative");
        if(bindingResult.hasErrors()){
            return "sitesections/section-operative";
        }
        else{
            systemSourceService.addSystemSource(sourceOperative.getDomain(),sourceOperative.getUrlSite());
            return "redirect:/allForPC/operative";
        }
    }

    @PostMapping("/admin/addOfficeProgram")
    public String addOfficeProgram(@Valid @ModelAttribute("urlOfficeProgram") UrlRequest urlOfficeProgram,
                                   BindingResult bindingResult) throws IOException,NullPointerException {
        addGlobalErrorsValidURL(bindingResult,"urlOfficeProgram");
        if(bindingResult.hasErrors()){
            return "sitesections/section-office";
        }
        else{
            officeProgramService.addOfficeProgram(urlOfficeProgram.getUrlSite());
            return "redirect:/allForPC/officeProgram";
        }
    }

    @PostMapping("/admin/addSoftware")
    public String addSoftware( @Valid @ModelAttribute("sourceSoft") SourceRequest sourceSoft,
                              BindingResult bindingResult) throws IOException,NullPointerException {
        addGlobalErrorsValidURL(bindingResult,"sourceSoft");
        if(bindingResult.hasErrors()){
            return "sitesections/section-soft";
        }
        else{
            softwareService.addSoftware(sourceSoft.getDomain(),sourceSoft.getUrlSite());
            return "redirect:/allForPC/soft";
        }
    }

    @PostMapping("/admin/addArticle")
    public String addArticle(@Valid @ModelAttribute("urlArticle") UrlRequest urlArticle,
                             BindingResult bindingResult) throws IOException,NullPointerException {
        addGlobalErrorsValidURL(bindingResult,"urlArticle");
        if(bindingResult.hasErrors()){
            return "sitesections/section-article";
        }
        else{
            articleService.addArticle(urlArticle.getUrlSite());
            return "redirect:/allForPC/articles";
        }
    }

    private  void addGlobalErrorsValidURL(BindingResult bindingResult,String objName){
        CheckURLValidator checkURL = new CheckURLValidator();
        ErrorMalformedURL errorMalformedURL = new ErrorMalformedURL();
        ErrorUnknownHost errorUnknownHost = new ErrorUnknownHost();
        ErrorIO errorIO = new ErrorIO();
        ClientError clientError = new ClientError();

        ObjectError objectErrorMalFormedURL = checkURL.isExceptionValidField(errorMalformedURL,objName);
        ObjectError objectErrorUnknownHost = checkURL.isExceptionValidField(errorUnknownHost,objName);
        ObjectError objectErrorIO = checkURL.isExceptionValidField(errorIO,objName);
        ObjectError objectClientError = checkURL.isValidateResponseCode(clientError,objName);

        if(!objectErrorMalFormedURL.getDefaultMessage().isEmpty()){
            bindingResult.addError(objectErrorMalFormedURL);
        }

        if(!objectErrorUnknownHost.getDefaultMessage().isEmpty()){
            bindingResult.addError(objectErrorUnknownHost);
        }

        if(!objectErrorIO.getDefaultMessage().isEmpty()){
            bindingResult.addError(objectErrorIO);
        }


        if(!objectClientError.getDefaultMessage().isEmpty()){
            bindingResult.addError(objectClientError);
        }
    }
}
