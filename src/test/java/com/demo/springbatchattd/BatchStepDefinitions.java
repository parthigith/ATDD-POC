package com.demo.springbatchattd;

import com.demo.springbatchattd.helper.FSHelper;
import com.demo.springbatchattd.Entity.Title;
import com.demo.springbatchattd.repository.TitleRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8081")
public class BatchStepDefinitions {

    private JobLauncher jobLauncher;
    private Job job;
    private TitleRepository titleRepository;
    private BatchStatus batchStatus;
    private Response response;
    private static long appId;

    @Autowired
    public BatchStepDefinitions(JobLauncher jobLauncher, Job job, TitleRepository titleRepository) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.titleRepository = titleRepository;
    }

    @Given("^that required vendor files are exist at the source location$")
    public void checkFilesExist() throws IOException {
        if (!FSHelper.checkAllFilesExist("files/source")) {
            throw new FileNotFoundException();
        }
    }

    @When("^batch job gets invoked$")
    public void invokeBatchJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {

        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);

        JobExecution jobExecution = jobLauncher.run(job, parameters);
        batchStatus = jobExecution.getStatus();
    }

    @Then("^job must be started$")
    public void jobStatus() {
        assertThat(batchStatus).isEqualTo(BatchStatus.COMPLETED);
    }

    @Then("job should not get started")
    public void jobShouldNotGetStarted() {
        assertThat(batchStatus).isEqualTo(BatchStatus.FAILED);
    }

    @Then("all files must be copied to the backup location")
    public void allFilesMustBeBackedUpInS3Bucket() throws FileNotFoundException {
        if (!FSHelper.checkAllFilesExist("files/backup")) {
            throw new FileNotFoundException();
        }
    }

    @And("all files must get downloaded to the clean folder")
    public void filesMustGetDownloadedToFileShareServer() throws FileNotFoundException {
        if (!FSHelper.checkAllFilesExist("files/clean")) {
            throw new FileNotFoundException();
        }
    }

    @And("api is invoked to get the response")
    public void invokeApi() throws IOException {
        RestAssured.baseURI = "http://localhost:8081";
        RequestSpecification request = RestAssured.given();
        appId = FSHelper.readAppId();
        response = request.get(String.format("/api/image/upload/%s", appId));
    }

    @Then("api status code must be {int}")
    public void responseMustBe(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @And("verify api response")
    public void verifyApiResponse() {
        assertEquals(appId, Long.parseLong(response.jsonPath().get("appId")));
        assertNotNull(response.jsonPath().get("documentId"));
    }

    @Then("verify metadata were updated in database")
    public void metadataGetUpdatedInDatabase() throws IOException {
        Title title = titleRepository.getByAppId(appId);
        assertNotNull(title);
        assertEquals(appId, title.getAppId());
    }
}
