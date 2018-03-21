package com.zhh.repository.entity;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    private String addBy;

    private LocalDateTime addTime;

    private String upBy;

    private LocalDateTime upTime;

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public String getUpBy() {
        return upBy;
    }

    public void setUpBy(String upBy) {
        this.upBy = upBy;
    }

    public LocalDateTime getUpTime() {
        return upTime;
    }

    public void setUpTime(LocalDateTime upTime) {
        this.upTime = upTime;
    }
}
