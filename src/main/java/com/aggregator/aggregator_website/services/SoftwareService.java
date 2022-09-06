package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.config.MapperConfig;
import com.aggregator.aggregator_website.dto.PageDto;
import com.aggregator.aggregator_website.dto.SoftwareDto;
import com.aggregator.aggregator_website.entities.Software;
import com.aggregator.aggregator_website.repository.SoftwareRepository;
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
public class SoftwareService {
    private final SoftwareRepository softwareRepository;
    private final ParsingPage parsingPage;
    private final ModelMapper modelMapper;

    public List<SoftwareDto> getAllSoftware(){
        List<Software> softwareList = softwareRepository.findAll();
        if(softwareList.size() > 0){
            return MapperConfig.convertList(softwareList,this::mapToDto);
        }
        else{
            List<SoftwareDto> softwareDtoList = new ArrayList<>();
            SoftwareDto softW = getDefaultSoftware();
            softwareDtoList.add(softW);
            return softwareDtoList;
        }
    }

    @Transactional
    public void addSoftware(String domain,String url) throws IOException,NullPointerException {
        SoftwareDto softwareDto = new SoftwareDto();
        PageDto pageDto = parsingPage.getParsePage(url);
        softwareDto.setSite(domain);
        String ico = "http://favicon.yandex.net/favicon/" + domain;
        softwareDto.setIcon(ico);
        softwareDto.setImg(pageDto.getImageUrl());
        softwareDto.setTitle(pageDto.getTitle());
        softwareDto.setText(pageDto.getText());
        softwareDto.setLink(pageDto.getLink());

        Software software = modelMapper.map(softwareDto,Software.class);
        softwareRepository.save(software);
    }

    @Transactional
    public void deleteSoftware(Long id){
        Software soft = softwareRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        softwareRepository.delete(soft);
    }

    private SoftwareDto getDefaultSoftware(){
        SoftwareDto softDto = new SoftwareDto();
        softDto.setId(Long.valueOf(0));
        softDto.setIcon("http://favicon.yandex.net/favicon/theprogs.ru");
        softDto.setSite("theprogs.ru");
        softDto.setImg("img/comp.jpeg");
        softDto.setTitle("Какие самые необходимые программы для компьютера?");
        softDto.setText("В данной статье мы решили собрать только самые необходимые программы для Windows на ваш компьютер или ноутбук.");
        softDto.setLink("https://theprogs.ru/nuzhnye-programmy-dlya-kompyutera/");
        return softDto;
    }

    private SoftwareDto mapToDto(Software software){
        SoftwareDto dto = modelMapper.map(software,SoftwareDto.class);
        return dto;
    }
}
