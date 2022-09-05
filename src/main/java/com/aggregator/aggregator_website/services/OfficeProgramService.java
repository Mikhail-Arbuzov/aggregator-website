package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.config.MapperConfig;
import com.aggregator.aggregator_website.dto.OfficeProgramDto;
import com.aggregator.aggregator_website.dto.PageDto;
import com.aggregator.aggregator_website.entities.OfficeProgram;
import com.aggregator.aggregator_website.repository.OfficeProgramRepository;
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
public class OfficeProgramService {
    private final OfficeProgramRepository officeProgramRepository;
    private final ParsingPage parsingPage;
    private final ModelMapper modelMapper;

    public List<OfficeProgramDto> getAllOfficePrograms(){
        List<OfficeProgram> officePrograms = officeProgramRepository.findAll();
        if(officePrograms.size() > 0){
            return MapperConfig.convertList(officePrograms,this::mapToDto);
        }
        else{
            List<OfficeProgramDto> officeProgramDtoList = new ArrayList<>();
            OfficeProgramDto officePo = getDefaultOfficeProgram();
            officeProgramDtoList.add(officePo);
            return officeProgramDtoList;
        }
    }

    @Transactional
    public void addOfficeProgram(String url) throws IOException,NullPointerException {
        OfficeProgramDto officeProgramDto = new OfficeProgramDto();
        PageDto page = parsingPage.getParsePage(url);
        officeProgramDto.setImageUrl(page.getImageUrl());
        officeProgramDto.setHeader(page.getTitle());
        officeProgramDto.setText(page.getText());
        officeProgramDto.setLink(page.getLink());

        OfficeProgram officeProgram = modelMapper.map(officeProgramDto,OfficeProgram.class);
        officeProgramRepository.save(officeProgram);
    }

    @Transactional
    public void deleteOfficeProgram(Long id){
        OfficeProgram program = officeProgramRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        officeProgramRepository.delete(program);
    }

    private OfficeProgramDto getDefaultOfficeProgram(){
        OfficeProgramDto programDto = new OfficeProgramDto();
        programDto.setId(Long.valueOf(0));
        programDto.setImageUrl("img/comp.jpeg");
        programDto.setHeader("Лучшие пакеты офисных программ в 2022 году");
        programDto.setText("Если вернуться в прошлое столетие, термин «office suite» означал комнаты в здании, где люди собирались в рабочие дни и печатали на машинке, проводили собрания, подсчитывание заработки, разрабатывали рекламу и занимались другими делами.");
        programDto.setLink("https://trashexpert.ru/software/ms-office/the-best-office-suites/?");
        return programDto;
    }

    private OfficeProgramDto mapToDto(OfficeProgram officeProgram){
        OfficeProgramDto officeProgramDto = modelMapper.map(officeProgram,OfficeProgramDto.class);
        return officeProgramDto;
    }
}
