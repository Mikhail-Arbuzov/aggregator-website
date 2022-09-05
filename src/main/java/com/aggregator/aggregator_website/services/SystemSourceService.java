package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.config.MapperConfig;
import com.aggregator.aggregator_website.dto.PageDto;
import com.aggregator.aggregator_website.dto.SystemSourceDto;
import com.aggregator.aggregator_website.entities.SystemSource;
import com.aggregator.aggregator_website.repository.SystemSourceRepository;
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
public class SystemSourceService {
    private final SystemSourceRepository systemSourceRepository;
    private final ParsingPage parsingPage;
    private final ModelMapper modelMapper;

    public List<SystemSourceDto> getAllSystemSources(){
        List<SystemSource> systemSources = systemSourceRepository.findAll();
        if(systemSources.size() > 0){
            return MapperConfig.convertList(systemSources,this::mapToDto);
        }
        else{
            List<SystemSourceDto> systemSourceDtoList = new ArrayList<>();
            SystemSourceDto sourceDto = getDefaultSystemSource();
            systemSourceDtoList.add(sourceDto);
            return systemSourceDtoList;
        }
    }

    @Transactional
    public void addSystemSource(String domain, String url) throws IOException,NullPointerException {
        SystemSourceDto systemSourceDto = new SystemSourceDto();
        PageDto pageDto = parsingPage.getParsePage(url);
        String logotype = "http://favicon.yandex.net/favicon/" + domain;
        systemSourceDto.setLogotype(logotype);
        systemSourceDto.setDomain(domain);
        systemSourceDto.setImage(pageDto.getImageUrl());
        systemSourceDto.setTitle(pageDto.getTitle());
        systemSourceDto.setText(pageDto.getText());
        systemSourceDto.setLink(pageDto.getLink());

        SystemSource systemSource = modelMapper.map(systemSourceDto,SystemSource.class);
        systemSourceRepository.save(systemSource);

    }

    @Transactional
    public void deleteSystemSource(Long id){
        SystemSource source = systemSourceRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        systemSourceRepository.delete(source);
    }

    private SystemSourceDto getDefaultSystemSource(){
        SystemSourceDto operationSystemDto = new SystemSourceDto();
        operationSystemDto.setId(Long.valueOf(0));
        String icon = "http://favicon.yandex.net/favicon/ichip.ru";
        operationSystemDto.setLogotype(icon);
        operationSystemDto.setDomain("ichip.ru");
        operationSystemDto.setImage("img/comp.jpeg");
        operationSystemDto.setTitle("Какую операционную систему выбрать: Windows и альтернативы");
        operationSystemDto.setText("Даже если вам кажется, что выбор очевиден, не спешите с выводами.");
        operationSystemDto.setLink("https://ichip.ru/sovety/ekspluataciya/kakuyu-operacionnuyu-sistemu-vybrat-721542?");
        return operationSystemDto;
    }

    private SystemSourceDto mapToDto(SystemSource systemSource){
        SystemSourceDto sourceDto = modelMapper.map(systemSource,SystemSourceDto.class);
        return sourceDto;
    }
}
