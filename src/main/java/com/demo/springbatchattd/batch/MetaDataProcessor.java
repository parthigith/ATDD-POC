package com.demo.springbatchattd.batch;

import com.demo.springbatchattd.helper.FSHelper;
import com.demo.springbatchattd.Entity.Title;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Component
public class MetaDataProcessor implements ItemProcessor<Title, Title> {

    private static String BASE_URL = "http://localhost:8081";
    private RestTemplate restTemplate;

    @Autowired
    public MetaDataProcessor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Title process(Title title) throws Exception {
        downloadFiles();
        Title titleResponse = makeApiRequest(title);
        title.setDocumentId(titleResponse.getDocumentId());
        return title;
    }

    private Title makeApiRequest(Title title) throws JSONException {
        String endPoint = "/api/image/upload";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appId", title.getAppId());

        HttpEntity<String> titleRequest = new HttpEntity<>(jsonObject.toString(), headers);
        return restTemplate.postForObject(
                String.format("%s%s", BASE_URL, endPoint), titleRequest, Title.class);
    }

    private void downloadFiles() throws IOException {
        FSHelper.copyFiles("files/source", "files/backup");
        FSHelper.copyFiles("files/source", "files/clean");
    }
}
