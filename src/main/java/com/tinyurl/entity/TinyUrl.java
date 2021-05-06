package com.tinyurl.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tiny_url")
public class TinyUrl {

    @Id
    private long id;
    private String url;
    private java.sql.Timestamp createDate;
    private java.sql.Timestamp expireDate;
    private java.sql.Timestamp lastUpdate;
    private String createIp;
    private long state;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public java.sql.Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.sql.Timestamp createDate) {
        this.createDate = createDate;
    }


    public java.sql.Timestamp getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(java.sql.Timestamp expireDate) {
        this.expireDate = expireDate;
    }


    public java.sql.Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(java.sql.Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }


    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TinyUrl{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", createDate=" + createDate +
                ", expireDate=" + expireDate +
                ", lastUpdate=" + lastUpdate +
                ", createIp='" + createIp + '\'' +
                ", state=" + state +
                '}';
    }
}
