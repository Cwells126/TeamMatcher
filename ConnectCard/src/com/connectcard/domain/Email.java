
package com.connectcard.domain;

import java.util.Date;

/**
 *
 * @author admin
 */
public class Email {
    private Integer idEmaillist;
    private String emailAddress;
    private Date dtCreated;

    public Integer getIdEmaillist() {
        return idEmaillist;
    }

    public void setIdEmaillist(Integer idEmaillist) {
        this.idEmaillist = idEmaillist;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getDtCreated() {
        return dtCreated;
    }

    public void setDtCreated(Date dtCreated) {
        this.dtCreated = dtCreated;
    }
}
