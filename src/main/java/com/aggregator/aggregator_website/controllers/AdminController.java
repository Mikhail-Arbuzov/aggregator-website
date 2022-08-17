package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.DomainRequest;
import com.aggregator.aggregator_website.dto.TransferInfoWebsiteRequest;
import com.aggregator.aggregator_website.dto.siteanalysis.SiteAnalysisDto;
import com.aggregator.aggregator_website.exceptionhandlers.notifications.ErrorMessage;
import com.aggregator.aggregator_website.services.SiteAnalysisClient;
import com.aggregator.aggregator_website.services.WebsiteService;
import com.aggregator.aggregator_website.services.globalerrors.ClientError;
import com.aggregator.aggregator_website.services.globalerrors.ErrorIO;
import com.aggregator.aggregator_website.services.globalerrors.ErrorMalformedURL;
import com.aggregator.aggregator_website.services.globalerrors.ErrorUnknownHost;
import com.aggregator.aggregator_website.services.validation.CheckURLValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class AdminController {
    private final SiteAnalysisClient siteAnalysisClient;
    private final WebsiteService websiteService;

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
                                          String emptyDate,Integer errCod,Integer errCod1){
        DomainRequest domainRequest = new DomainRequest();
        model.addAttribute("domainRequest",domainRequest);
        TransferInfoWebsiteRequest infoWebsiteRequest = new TransferInfoWebsiteRequest();
        model.addAttribute("infoWebsiteRequest",infoWebsiteRequest);

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

        return "sitesections/komplect";
    }


    @PostMapping("/admin/result-bounceRate")
    public String getBounceRate(@Valid @ModelAttribute("domainRequest")DomainRequest domainRequest,
                                BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
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

        addGlobalErrorsValidURL(bindingResult);
        if(bindingResult.hasErrors()){
            DomainRequest domainRequest = new DomainRequest();
            model.addAttribute("domainRequest",domainRequest);
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
                DomainRequest domainRequest = new DomainRequest();
                model.addAttribute("domainRequest",domainRequest);
                Double bounceRatePercent = 30.3;
                model.addAttribute("bounceRatePercent",bounceRatePercent);
                return "sitesections/komplect";
            }
        }

        return "redirect:" + infoWebsiteRequest.getDataTransferPage();
    }

    private  void addGlobalErrorsValidURL(BindingResult bindingResult){
        CheckURLValidator checkURL = new CheckURLValidator();
        ErrorMalformedURL errorMalformedURL = new ErrorMalformedURL();
        ErrorUnknownHost errorUnknownHost = new ErrorUnknownHost();
        ErrorIO errorIO = new ErrorIO();
        ClientError clientError = new ClientError();

        ObjectError objectErrorMalFormedURL = checkURL.isExceptionValidField(errorMalformedURL,"infoWebsiteRequest");
        ObjectError objectErrorUnknownHost = checkURL.isExceptionValidField(errorUnknownHost,"infoWebsiteRequest");
        ObjectError objectErrorIO = checkURL.isExceptionValidField(errorIO,"infoWebsiteRequest");
        ObjectError objectClientError = checkURL.isValidateResponseCode(clientError,"infoWebsiteRequest");

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
