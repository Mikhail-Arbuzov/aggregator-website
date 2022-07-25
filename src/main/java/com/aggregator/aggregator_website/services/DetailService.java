package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.entities.Detail;
import com.aggregator.aggregator_website.repository.DetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class DetailService {
    private final DetailRepository detailRepository;

    public void updateAvatar(Detail detail){
        detailRepository.save(detail);
    }
}
