package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by lakjeewa on 8/1/18.
 */
@Entity
public class Contact extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactId;
    @NotNull
    private String contactName;
    @NotNull
    private String contactNumber;

    @ManyToOne
    private User user;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
