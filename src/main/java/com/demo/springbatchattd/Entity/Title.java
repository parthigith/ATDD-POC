package com.demo.springbatchattd.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Title {

    @Id
    private long appId;
    private String loanCust;
    private String title;
    private String vin;
    private UUID documentId;

//    public Title() {
//    }
//
//    public Title(long appId, String loanCust, String title, String vin) {
//        this.appId = appId;
//        this.loanCust = loanCust;
//        this.title = title;
//        this.vin = vin;
//    }
//
//    public long getAppId() {
//        return appId;
//    }
//
//    public void setAppId(long appId) {
//        this.appId = appId;
//    }
//
//    public String getLoanCust() {
//        return loanCust;
//    }
//
//    public void setLoanCust(String loanCust) {
//        this.loanCust = loanCust;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getVin() {
//        return vin;
//    }
//
//    public void setVin(String vin) {
//        this.vin = vin;
//    }
//
//    public UUID getDocumentId() {
//        return documentId;
//    }
//
//    public void setDocumentId(UUID documentId) {
//        this.documentId = documentId;
//    }
}
