package com.greenmile.desafio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.greenmile.desafio.domain.RegisterTime;

import java.util.Date;

public class RegisteTimeResponseDTO {


    private RegisteTimeResponseDTO( RegisterTime registerTime ){
        this.initialTime = registerTime.getInitTime();
        this.endTime = registerTime.getEndTime();
    }
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date initialTime;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date endTime;


    public Date getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(Date initialTime) {
        this.initialTime = initialTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public static RegisteTimeResponseDTO toRegisteTimeResponse( RegisterTime registerTime ){
        return new RegisteTimeResponseDTO( registerTime );
    }

}
