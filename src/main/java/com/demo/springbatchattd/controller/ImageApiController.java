package com.demo.springbatchattd.controller;

import com.demo.springbatchattd.Entity.Title;
import com.demo.springbatchattd.Service.TitleService;
import com.demo.springbatchattd.model.TitleRequest;
import com.demo.springbatchattd.model.TitleResponse;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/api")
public class ImageApiController {

    private JobLauncher jobLauncher;

    @Qualifier("TitleImageProcessor")
    private Job job;

    private TitleService titleService;

    @Autowired
    public ImageApiController(JobLauncher jobLauncher, Job job, TitleService titleService) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.titleService = titleService;
    }

    @GetMapping(value = "/image/upload/{appId}")
    public ResponseEntity<TitleResponse> getImageMetaDataById(@PathVariable("appId") final String appId) {
        Title title = titleService.getTitle(parseLong(appId));
        return new ResponseEntity<>(
                new TitleResponse(Long.toString(title.getAppId()), title.getDocumentId()), HttpStatus.OK);
    }

    @PostMapping(value = "/image/upload")
    public ResponseEntity<TitleResponse> uploadImages(@RequestBody TitleRequest titleRequest) {
        return new ResponseEntity<>(new TitleResponse(UUID.randomUUID()), HttpStatus.OK);
    }

    @GetMapping(value = "/batch/start")
    public BatchStatus startBatchJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(job, parameters);
        return jobExecution.getStatus();
    }
}
