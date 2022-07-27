package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.entities.Detail;
import com.aggregator.aggregator_website.repository.DetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class DetailService {
    private final DetailRepository detailRepository;

    public void updateAvatar(Detail detail){
        detailRepository.save(detail);
    }

    @Transactional
    public void updateFullNameUser(Detail detail){
        Detail detail1User = detailRepository.findById(detail.getId()).get();
        detail1User.setFirstName(detail.getFirstName());
        detail1User.setSecondName(detail.getSecondName());
        detailRepository.save(detail1User);
    }

    @Transactional
    public void updateDescriptionUser(Detail detail) {
        Detail detail1User1 = detailRepository.findById(detail.getId()).get();
        detail1User1.setDescription(detail.getDescription());
        detailRepository.save(detail1User1);
    }

    @Transactional
    public void updateEmailUser(Detail detail) {
        Detail detail1User2 = detailRepository.findById(detail.getId()).get();
        detail1User2.setEmail(detail.getEmail());
        detailRepository.save(detail1User2);
    }

    @Transactional
    public void updateSocNetworkUser(Detail detail) {
        Detail detail1User3 = detailRepository.findById(detail.getId()).get();
        detail1User3.setVkNetwork(detail.getVkNetwork());
        detail1User3.setClassmatesNetwork(detail.getClassmatesNetwork());
        detail1User3.setTelegramNetwork(detail.getTelegramNetwork());
        detailRepository.save(detail1User3);
    }
}
