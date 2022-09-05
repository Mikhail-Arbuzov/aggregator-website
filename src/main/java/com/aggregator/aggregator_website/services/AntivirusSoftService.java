package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.config.MapperConfig;
import com.aggregator.aggregator_website.dto.AntivirusSoftDto;
import com.aggregator.aggregator_website.dto.PageDto;
import com.aggregator.aggregator_website.entities.AntivirusSoft;
import com.aggregator.aggregator_website.repository.AntivirusSoftRepository;
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
public class AntivirusSoftService {
    private final AntivirusSoftRepository antivirusSoftRepository;
    private final ParsingPage parsingPage;
    private final ModelMapper modelMapper;

    public List<AntivirusSoftDto> getAllAntivirusSoft(){
        List<AntivirusSoft> antivirusSoftList = antivirusSoftRepository.findAll();
        if(antivirusSoftList.size() > 0){
            return MapperConfig.convertList(antivirusSoftList,this::mapToDto);
        }
        else {
            List<AntivirusSoftDto> antivirusSoftDtoList = new ArrayList<>();
            AntivirusSoftDto softDto = getDefaultAntivirusSoft();
            antivirusSoftDtoList.add(softDto);
            return antivirusSoftDtoList;
        }
    }

    @Transactional
    public void addAntivirusSoft(String url) throws IOException,NullPointerException{
        AntivirusSoftDto antivirusSoftDto = new AntivirusSoftDto();
        PageDto pageDto = parsingPage.getParsePage(url);
        antivirusSoftDto.setImgURL(pageDto.getImageUrl());
        antivirusSoftDto.setTitle(pageDto.getTitle());
        antivirusSoftDto.setLink(pageDto.getLink());

        AntivirusSoft antivirusSoft = modelMapper.map(antivirusSoftDto,AntivirusSoft.class);
        antivirusSoftRepository.save(antivirusSoft);
    }

    @Transactional
    public void deleteAntivirusSoft(Long id){
        AntivirusSoft soft = antivirusSoftRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        antivirusSoftRepository.delete(soft);
    }

    private AntivirusSoftDto getDefaultAntivirusSoft(){
        AntivirusSoftDto antiSoft = new AntivirusSoftDto();
        antiSoft.setId(Long.valueOf(0));
        antiSoft.setImgURL("img/comp.jpeg");
        antiSoft.setTitle("Как выбрать антивирусную программу?");
        antiSoft.setLink("https://www.shkolazhizni.ru/computers/articles/52891/?");
        return antiSoft;
    }

    private AntivirusSoftDto mapToDto(AntivirusSoft antivirusSoft){
        AntivirusSoftDto softDto = modelMapper.map(antivirusSoft,AntivirusSoftDto.class);
        return softDto;
    }
}
