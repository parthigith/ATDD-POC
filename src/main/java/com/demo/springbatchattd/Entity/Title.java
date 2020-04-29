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
}
