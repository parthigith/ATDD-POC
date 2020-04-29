package com.demo.springbatchattd.repository;

import com.demo.springbatchattd.Entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long> {

    Title getByAppId(long appId);
}
