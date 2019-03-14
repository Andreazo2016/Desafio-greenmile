package com.greenmile.desafio.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RegisterTime {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date initTime;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date endTime;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    public RegisterTime() {
    }

    public RegisterTime( Date initTime, Date endTime) {
        this.initTime = initTime;
        this.endTime = endTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
