package com.demo.springbatchattd.Service;

import com.demo.springbatchattd.Entity.Title;
import com.demo.springbatchattd.model.TitleResponse;
import com.demo.springbatchattd.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleService {

    private TitleRepository titleRepository;

    public TitleService(@Autowired TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public Title getTitle(long appId) {
        return titleRepository.getByAppId(appId);
    }

}
