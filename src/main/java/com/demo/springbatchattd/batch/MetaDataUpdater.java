package com.demo.springbatchattd.batch;

import com.demo.springbatchattd.Entity.Title;
import com.demo.springbatchattd.repository.TitleRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MetaDataUpdater implements ItemWriter<Title> {

    private TitleRepository titleRepository;

    @Autowired
    public MetaDataUpdater(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @Override
    public void write(List<? extends Title> titles) throws Exception {
        titleRepository.saveAll(titles);
    }
}
