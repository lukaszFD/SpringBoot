package pl.lukasz.fd.restapi.Model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Accounts", schema = "repository")
@Setter
@Getter
@ToString
public class Accounts
{
    @Column(name ="externalid")
    private String Externalid;
    @Id
    @Column(name ="accountid")
    private Integer AccountId;
    @Column(name ="countryid")
    private Long CountryId;
    @Column(name ="userid")
    private Long UserId;
    @Column(name ="systemid")
    private Long SystemId;
    @Column(name ="serverid")
    private Long ServerId;
    @Column(name ="name")
    private String Name;
    @Column(name ="description")
    private String Description;
    @Column(name ="type")
    private String Type;
    @Column(name ="passwordexpires")
    private LocalDateTime PasswordExpires;
    @Column(name ="tofix")
    private String Tofix;
    @Column(name ="creationdate")
    private LocalDateTime CreationDate;
    @Column(name ="editdate")
    private LocalDateTime EditDate;
    @Column(name ="deletedate")
    private LocalDateTime DeleteDate;
    @Column(name ="recaccountid")
    private Long RecAccountId;
}
