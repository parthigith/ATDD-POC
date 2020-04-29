package com.demo.springbatchattd.helper;

import com.demo.springbatchattd.Entity.Title;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FSHelper {

    public static void copyFiles(String source, String destination) throws IOException {
        File srcDir = new File(source);
        File destDir = new File(destination);
        FileUtils.copyDirectory(srcDir, destDir, true);
    }

    public static boolean checkAllFilesExist(String folder) {
        return Files.exists(Paths.get(folder + "/title.dat")) &&
                Files.exists(Paths.get(folder + "/title.tif")) &&
                Files.exists(Paths.get(folder + "/title.trl"));
    }

    public static long readAppId() throws IOException {
        String[] lines = Files.readAllLines(
                new File("files/source/title.dat").toPath()).toArray(new String[0]);
        return Long.parseLong(lines[0].substring(0, 4));
    }

    public static LineMapper<Title> mapLines() {
        DefaultLineMapper<Title> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("appId", "loanCust", "title", "vin");

        BeanWrapperFieldSetMapper<Title> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Title.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
