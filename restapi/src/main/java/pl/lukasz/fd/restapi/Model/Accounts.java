package pl.lukasz.fd.restapi.Model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Accounts", schema = "repository")
@Data
public class Accounts
{
    @Column(name ="ExternalId")
    private String ExternalId;
    @Id
    @Column(name ="AccountId")
    private Long AccountId;
    @Column(name ="CountryId")
    private String CountryId;
    @Column(name ="UserId")
    private String UserId;
    @Column(name ="SystemId")
    private String SystemId;
    @Column(name ="ServerId")
    private String ServerId;
    @Column(name ="Name")
    private String Name;
    @Column(name ="Description")
    private String Description;
    @Column(name ="Type")
    private String Type;
    @Column(name ="PasswordExpires")
    private String PasswordExpires;
    @Column(name ="Tofix")
    private String Tofix;
    @Column(name ="CreationDate")
    private String CreationDate;
    @Column(name ="EditDate")
    private String EditDate;
    @Column(name ="DeleteDate")
    private String DeleteDate;
    @Column(name ="RecAccountId")
    private String RecAccountId;
}
