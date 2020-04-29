package com.demo.springbatchattd.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String appId;
    private UUID documentId;

    public TitleResponse(UUID documentId) {
        this.documentId = documentId;
    }
}
