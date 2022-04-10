package pl.lukasz.fd.restapi.Model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Accounts", schema = "repository")
@Data
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

    public String getExternalid() {
        return Externalid;
    }

    public void setExternalid(String externalid) {
        Externalid = externalid;
    }

    public Integer getAccountId() {
        return AccountId;
    }

    public void setAccountId(Integer accountId) {
        AccountId = accountId;
    }

    public Long getCountryId() {
        return CountryId;
    }

    public void setCountryId(Long countryId) {
        CountryId = countryId;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public Long getSystemId() {
        return SystemId;
    }

    public void setSystemId(Long systemId) {
        SystemId = systemId;
    }

    public Long getServerId() {
        return ServerId;
    }

    public void setServerId(Long serverId) {
        ServerId = serverId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public LocalDateTime getPasswordExpires() {
        return PasswordExpires;
    }

    public void setPasswordExpires(LocalDateTime passwordExpires) {
        PasswordExpires = passwordExpires;
    }

    public String getTofix() {
        return Tofix;
    }

    public void setTofix(String tofix) {
        Tofix = tofix;
    }

    public LocalDateTime getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        CreationDate = creationDate;
    }

    public LocalDateTime getEditDate() {
        return EditDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        EditDate = editDate;
    }

    public LocalDateTime getDeleteDate() {
        return DeleteDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        DeleteDate = deleteDate;
    }

    public Long getRecAccountId() {
        return RecAccountId;
    }

    public void setRecAccountId(Long recAccountId) {
        RecAccountId = recAccountId;
    }
}
