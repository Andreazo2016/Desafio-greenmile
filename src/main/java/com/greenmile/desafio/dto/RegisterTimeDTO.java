package com.greenmile.desafio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.greenmile.desafio.domain.RegisterTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RegisterTimeDTO {

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date initTime;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date endTime;


    public Date getInitTime() {
        return initTime;
    }

    public void setInitTime(Date initTime) {
        this.initTime = initTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public RegisterTime toRegisteTime(){
        return new RegisterTime( initTime, endTime );
    }


}
