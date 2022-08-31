package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.config.MapperConfig;
import com.aggregator.aggregator_website.dto.ConfiguratorDto;
import com.aggregator.aggregator_website.dto.siteanalysis.SiteAnalysisDto;
import com.aggregator.aggregator_website.entities.Configurator;
import com.aggregator.aggregator_website.repository.ConfiguratorRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ConfiguratorService {
    private final ConfiguratorRepository configuratorRepository;
    private final ModelMapper modelMapper;
    private final StatisticDataConvector statisticDataConvector;
    private final SiteAnalysisClient siteAnalysisClient;

    public List<ConfiguratorDto> getConfigurators(){
        List<Configurator> configuratorList = configuratorRepository.findAll();
        if (configuratorList.size() > 0){
             return MapperConfig.convertList(configuratorList,this::mapToDto);
        }
        else{
            List<ConfiguratorDto> confDtos = new ArrayList<>();
            ConfiguratorDto configuratorDto1 = getDefaultConfigurator();
            confDtos.add(configuratorDto1);
            return confDtos;
        }
    }

    public ConfiguratorDto getDefaultConfigurator(){
        ConfiguratorDto configuratorDto = new ConfiguratorDto();
        configuratorDto.setId(Long.valueOf(1));
        configuratorDto.setSiteName("неуказан домен");
        configuratorDto.setLogotype("img/iconSite.jpg");
        configuratorDto.setVisits(BigDecimal.valueOf(1000));
        configuratorDto.setBounceRate(0);
        String strTimes = "00:00:00";
        LocalTime time = LocalTime.parse(strTimes,DateTimeFormatter.ofPattern("HH:mm:ss"));
        configuratorDto.setTimeOnSite(time);
        configuratorDto.setMonth("Июль");
        configuratorDto.setYear("2022");
        configuratorDto.setLink("#");
        return configuratorDto;
    }

    @Transactional
    public void addConfigurator(SiteAnalysisDto analysisDto, String domain, String url){
        ConfiguratorDto configuratorDto = new ConfiguratorDto();
        String logotype = "http://favicon.yandex.net/favicon/" + domain;
        configuratorDto.setLogotype(logotype);
        configuratorDto.setSiteName(analysisDto.getSiteName());
        String statisticByVisits = analysisDto.getEngagments().getVisits();
        BigDecimal visits = new BigDecimal(statisticByVisits);
        BigDecimal res = visits.setScale(0, RoundingMode.HALF_UP);
        configuratorDto.setVisits(res);
        double bounceR = statisticDataConvector.bounceRateConvector(analysisDto.getEngagments().getBounceRate());
        configuratorDto.setBounceRate(bounceR);
        LocalTime time = statisticDataConvector.timeConvector(analysisDto.getEngagments().getTimeOnSite());
        configuratorDto.setTimeOnSite(time);
        String month = statisticDataConvector.monthConvector(analysisDto.getEngagments().getMonth());
        configuratorDto.setMonth(month);
        String year = analysisDto.getEngagments().getYear();
        configuratorDto.setYear(year);
        configuratorDto.setLink(url);

        Configurator configurator = modelMapper.map(configuratorDto,Configurator.class);
        configuratorRepository.save(configurator);
    }

    @Transactional
    public void updateConfigurator(Long id){
        Configurator config = configuratorRepository.findById(id).orElse(null);
        if (config != null){
            try{
                SiteAnalysisDto analysisDto = siteAnalysisClient.getSiteAnalysis(config.getSiteName());
                if(analysisDto != null){
                    double bounce = statisticDataConvector.bounceRateConvector(analysisDto.getEngagments().getBounceRate());
                    config.setBounceRate(bounce);
                    BigDecimal result = new BigDecimal(analysisDto.getEngagments().getVisits());
                    BigDecimal resVisit = result.setScale(0, RoundingMode.HALF_UP);
                    config.setVisits(resVisit);
                    LocalTime timeOnSite = statisticDataConvector.timeConvector(analysisDto.getEngagments().getTimeOnSite());
                    config.setTimeOnSite(timeOnSite);
                    String m = statisticDataConvector.monthConvector(analysisDto.getEngagments().getMonth());
                    config.setMonth(m);
                    config.setYear(analysisDto.getEngagments().getYear());
                    configuratorRepository.save(config);
                }
            }
            catch (HttpClientErrorException ex){
                ex.printStackTrace();
            }
        }
    }

    public Object[][] getStatisticByVisits(){
        int rowCount = (int) configuratorRepository.count();
        if(rowCount > 0){
            Object[][] result = new Object[rowCount + 1][2];
            int i = 1;
            result[0][0] ="Сайт";
            result[0][1] = "визитов";
            for(Configurator conf : configuratorRepository.findAll()){
                result[i][0] = conf.getSiteName();
                result[i][1] = conf.getVisits();
                i++;
            }
            return result;
        }
        else{
            Object[][] res = new Object[4][2];
            res[0][0] = "Сайт";
            res[0][1] = "визитов";
            res[1][0] = "www.citilink.ru";
            res[1][1] = 26500000;
            res[2][0] = "xcom-shop.ru";
            res[2][1] = 977500;
            res[3][0] = "hyperpe.ru";
            res[3][1] = 657500;
            return res;
        }
    }

    public Object[][] getStatisticByBounceRate(){
        int rowCount1 = (int) configuratorRepository.count();
        if(rowCount1 > 0){
            Object[][] arrayBounceRate = new Object[rowCount1 + 1][2];
            arrayBounceRate[0][0] ="Сайт";
            arrayBounceRate[0][1] ="процент отказов";
            int j = 1;
            for (Configurator configurator : configuratorRepository.findAll()){
                arrayBounceRate[j][0] = configurator.getSiteName();
                arrayBounceRate[j][1] = configurator.getBounceRate();
                j++;
            }
            return arrayBounceRate;
        }
        else{
            Object[][] arrBounceRate = new Object[4][2];
            arrBounceRate[0][0] = "Сайт";
            arrBounceRate[0][1] = "процент отказов";
            arrBounceRate[1][0] = "www.citilink.ru";
            arrBounceRate[1][1] = 35.71;
            arrBounceRate[2][0] = "xcom-shop.ru";
            arrBounceRate[2][1] = 56.44;
            arrBounceRate[3][0] = "hyperpe.ru";
            arrBounceRate[3][1] = 58.67;
            return arrBounceRate;
        }
    }

    public List<ConfiguratorDto> getStatisticByTimeOnSite(){
        List<Configurator> configurators = configuratorRepository.getAllConfiguratorsByLastTimeOnSite();
        if(configurators.size() > 0){
            return MapperConfig.convertList(configurators,this::mapToDto);
        }
        else{
            List<ConfiguratorDto> configuratorDtos = new ArrayList<>();
            ConfiguratorDto confDto = new ConfiguratorDto();
            confDto.setId(Long.valueOf(1));
            confDto.setSiteName("www.citilink.ru");
            confDto.setLogotype("img/iconSite.jpg");
            confDto.setVisits(BigDecimal.valueOf(26500000));
            confDto.setBounceRate(35.71);
            String strf ="00:05:56";
            LocalTime resultTime = LocalTime.parse(strf, DateTimeFormatter.ofPattern("HH:mm:ss"));
            confDto.setTimeOnSite(resultTime);
            confDto.setMonth("Июль");
            confDto.setYear("2022");
            confDto.setLink("#");

            ConfiguratorDto confDto1 = new ConfiguratorDto();
            confDto1.setId(Long.valueOf(2));
            confDto1.setSiteName("xcom-shop.ru");
            confDto1.setLogotype("img/iconSite.jpg");
            confDto1.setVisits(BigDecimal.valueOf(977500));
            confDto1.setBounceRate(56.44);
            String timeStr = "00:02:54";
            LocalTime resTime = LocalTime.parse(timeStr,DateTimeFormatter.ofPattern("HH:mm:ss"));
            confDto1.setTimeOnSite(resTime);
            confDto1.setMonth("Июль");
            confDto1.setYear("2022");
            confDto1.setLink("#");

            ConfiguratorDto confDto2 = new ConfiguratorDto();
            confDto2.setId(Long.valueOf(3));
            confDto2.setSiteName("hyperpe.ru");
            confDto2.setLogotype("img/iconSite.jpg");
            confDto2.setVisits(BigDecimal.valueOf(657500));
            confDto2.setBounceRate(58.67);
            String timeStr1 = "00:02:25";
            LocalTime resTime1 = LocalTime.parse(timeStr1,DateTimeFormatter.ofPattern("HH:mm:ss"));
            confDto2.setTimeOnSite(resTime1);
            confDto2.setMonth("Июль");
            confDto2.setYear("2022");
            confDto2.setLink("#");

            configuratorDtos.add(confDto);
            configuratorDtos.add(confDto1);
            configuratorDtos.add(confDto2);
            return configuratorDtos;
        }
    }

    private ConfiguratorDto mapToDto(Configurator configurator){
        ConfiguratorDto configuratorDto = modelMapper.map(configurator,ConfiguratorDto.class);
        return configuratorDto;
    }

}
