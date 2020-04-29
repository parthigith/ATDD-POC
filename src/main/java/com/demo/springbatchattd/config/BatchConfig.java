package com.demo.springbatchattd.config;

import com.demo.springbatchattd.helper.FSHelper;
import com.demo.springbatchattd.Entity.Title;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final static String JOB_NAME = "TitleImageProcessor";

    @Bean(name = JOB_NAME)
    public Job createJob(JobBuilderFactory titleJobBuilderFactory,
                         StepBuilderFactory titleStepBuilderFactory,
                         ItemReader<Title> metaDataReader,
                         ItemProcessor<Title, Title> metaDataProcessor,
                         ItemWriter<Title> metaDataWriter) {

        Step step = titleStepBuilderFactory.get(JOB_NAME)
                .<Title, Title>chunk(10)
                .reader(metaDataReader)
                .processor(metaDataProcessor)
                .writer(metaDataWriter)
                .build();

        return titleJobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean(name = "DatFileReader")
    public FlatFileItemReader<Title> datFileReader() {
        FlatFileItemReader<Title> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("files/source/title.dat"));
        flatFileItemReader.setName("DatReader");
        flatFileItemReader.setLineMapper(FSHelper.mapLines());
        return flatFileItemReader;
    }
}
