package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.config.MapperConfig;
import com.aggregator.aggregator_website.dto.InfoTrafficWebsite;
import com.aggregator.aggregator_website.dto.PageDto;
import com.aggregator.aggregator_website.dto.WebsiteDto;
import com.aggregator.aggregator_website.dto.siteanalysis.SiteAnalysisDto;
import com.aggregator.aggregator_website.entities.Page;
import com.aggregator.aggregator_website.entities.Website;
import com.aggregator.aggregator_website.repository.WebsiteRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WebsiteService {
    private final ParsingPage parsingPage;
    private final ModelMapper modelMapper;
    private final StatisticDataConvector statisticDataConvector;
    private final WebsiteRepository websiteRepository;


//    public List<WebsiteDto> getAllWebsiteByProcessor(){
//        WebsiteDto websiteDto = addDefaultWebsite();
//       return findAllWebsiteBySection("Процессор",websiteDto);
//    }
//
//    public List<WebsiteDto> getAllWebsiteRatingTrafficByProcessor(){
//        WebsiteDto websiteDto1 = addDefaultWebsite();
//        return findAllWebsiteRatingTrafficBySection("Процессор",websiteDto1);
//    }
//
//    public List<WebsiteDto> getAllWebsiteRatingByProcessor(){
//        WebsiteDto websiteDto2 = addDefaultWebsite();
//        return findAllWebsiteRatingBySection("Процессор",websiteDto2);
//    }

    public WebsiteDto getWebsiteById(Long id){
        Website website = websiteRepository.findById(id).orElse(null);
        if (website != null){
            return mapToDto(website);
        }

        return null;
    }

    public InfoTrafficWebsite getInfoTrafficWebsiteDto(Long id,SiteAnalysisDto siteAnalysis){
        InfoTrafficWebsite trafficWebsite = new InfoTrafficWebsite();
        trafficWebsite.setId(id);
        String month = statisticDataConvector.monthConvector(siteAnalysis.getEngagments().getMonth());
        trafficWebsite.setMonth(month);
        trafficWebsite.setYear(siteAnalysis.getEngagments().getYear());
        String time = statisticDataConvector.timeOnSiteConvector(siteAnalysis.getEngagments().getTimeOnSite());
        trafficWebsite.setTimeOnSite(time);
        double bounceRate1 = statisticDataConvector.bounceRateConvector(siteAnalysis.getEngagments().getBounceRate());
        trafficWebsite.setBounceRate(bounceRate1);
        String visits1 = statisticDataConvector.visitsConvectorInStr(siteAnalysis.getEngagments().getVisits());
        trafficWebsite.setVisits(visits1);
        trafficWebsite.setRatingInWorld(siteAnalysis.getGlobalRank().getRank());
        trafficWebsite.setRatingInCountry(siteAnalysis.getCountryRank().getRank());
        return trafficWebsite;
    }

    @Transactional
    public void addWebsite(SiteAnalysisDto analysisDto, String domain,String url, String section) throws IOException,NullPointerException {
        WebsiteDto websiteDto = new WebsiteDto();

        String iconSite = "http://favicon.yandex.net/favicon/" + domain;
        websiteDto.setIconSite(iconSite);

        websiteDto.setSiteName(analysisDto.getSiteName());

        String visits = statisticDataConvector.visitsConvectorInStr(analysisDto.getEngagments().getVisits());
        websiteDto.setVisits(visits);

        String timeOnSite = statisticDataConvector.timeOnSiteConvector(analysisDto.getEngagments().getTimeOnSite());
        websiteDto.setTimeOnSite(timeOnSite);

        double bounceRate = statisticDataConvector.bounceRateConvector(analysisDto.getEngagments().getBounceRate());
        websiteDto.setBounceRate(bounceRate);

        websiteDto.setRatingInWorld(analysisDto.getGlobalRank().getRank());
        websiteDto.setRatingInCountry(analysisDto.getCountryRank().getRank());

        String month = statisticDataConvector.monthConvector(analysisDto.getEngagments().getMonth());
        websiteDto.setMonth(month);

        websiteDto.setYear(analysisDto.getEngagments().getYear());

        PageDto pageDto = parsingPage.getParsePage(url);
        Page page = modelMapper.map(pageDto,Page.class);
        websiteDto.setPage(page);

        websiteDto.setSection(section);

        Website website = modelMapper.map(websiteDto,Website.class);

        websiteRepository.save(website);
    }

    @Transactional
    public void updateWebsite(InfoTrafficWebsite infoTrafficWebsite){
        Website website = websiteRepository.findById(infoTrafficWebsite.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        website.setVisits(infoTrafficWebsite.getVisits());
        website.setBounceRate(infoTrafficWebsite.getBounceRate());
        website.setRatingInWorld(infoTrafficWebsite.getRatingInWorld());
        website.setRatingInCountry(infoTrafficWebsite.getRatingInCountry());
        website.setTimeOnSite(infoTrafficWebsite.getTimeOnSite());
        website.setMonth(infoTrafficWebsite.getMonth());
        website.setYear(infoTrafficWebsite.getYear());

        websiteRepository.save(website);
    }


    @Transactional
    public void deleteWebsite(Long id){
        Website website = websiteRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        websiteRepository.delete(website);
    }

    public WebsiteDto addDefaultWebsite() {
        WebsiteDto websiteDto = new WebsiteDto();
        websiteDto.setId(Long.valueOf(1));
        websiteDto.setIconSite("img/iconSite.jpg");
        websiteDto.setSiteName("не указано");
        websiteDto.setVisits("не известно");
        websiteDto.setTimeOnSite("00:00:00");
        websiteDto.setBounceRate(0);
        websiteDto.setRatingInWorld(0);
        websiteDto.setRatingInCountry(1);
        websiteDto.setMonth("Июль");
        websiteDto.setYear("2022");
        Page page = new Page();
        page.setId(Long.valueOf(1));
        page.setImageUrl("img/comp.jpeg");
        page.setTitle("Заголовок не указан");
        page.setText("текст отсутствует");
        page.setLink("#");
        websiteDto.setPage(page);
        websiteDto.setSection("Процессор");
        return websiteDto;
    }

    public List<WebsiteDto> findAllWebsiteBySection(String section,WebsiteDto websiteDto){
        List<Website> webs = websiteRepository.findAllBySection(section);

        if(webs.size() > 0){
            return MapperConfig.convertList(webs,this::mapToDto);
        }
        else {
            List<WebsiteDto> websites = new ArrayList<>();
            websites.add(websiteDto);
            return websites;
        }
    }

    public List<WebsiteDto> findAllWebsiteRatingBySection(String section,WebsiteDto websiteDto2){
        List<Website> websites = websiteRepository.getByWebsitesSection(section);

        if (websites.size() > 0){
            return MapperConfig.convertList(websites,this::mapToDto);
        }
        else {
            List<WebsiteDto> websites2 = new ArrayList<>();
            websites2.add(websiteDto2);
            return websites2;
        }
    }

    public List<WebsiteDto> findAllWebsiteRatingTrafficBySection(String section, WebsiteDto websiteDto3){
        List<Website> websites3 = websiteRepository.getByWebsitesRating(section);
        if (websites3.size() > 0){
            return MapperConfig.convertList(websites3,this::mapToDto);
        }
        else {
            List<WebsiteDto> websiteDtos = new ArrayList<>();
            websiteDtos.add(websiteDto3);
            return websiteDtos;
        }
    }

    private WebsiteDto mapToDto(Website website){
        WebsiteDto websiteDto = modelMapper.map(website,WebsiteDto.class);
        return websiteDto;
    }
}
