package pl.lukasz.fd.restapi.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "Accounts", schema = "recon")
@Data
@NoArgsConstructor
public class ReconAccount
{
    public ReconAccount(String accountExId,
                        String countryRegionCode,
                        String userExId,
                        String systemExId,
                        String serverExId,
                        String name,
                        String description,
                        String type,
                        LocalDateTime passwordExpires) {
        AccountExId = accountExId;
        CountryRegionCode = countryRegionCode;
        UserExId = userExId;
        SystemExId = systemExId;
        ServerExId = serverExId;
        Name = name;
        Description = description;
        Type = type;
        PasswordExpires = passwordExpires;
    }

    @Column(name ="accountexid")
    private String AccountExId;
    @Column(name ="countryregioncode")
    private String CountryRegionCode;
    @Column(name ="userexid")
    private String UserExId;
    @Column(name ="systemexid")
    private String SystemExId;
    @Column(name ="serverexid")
    private String ServerExId;
    @Column(name ="name")
    private String Name;
    @Column(name ="description")
    private String Description;
    @Column(name ="type")
    private String Type;
    @Column(name ="passwordexpires")
    private LocalDateTime PasswordExpires;

    public String getAccountExId() {
        return AccountExId;
    }

    public void setAccountExId(String accountExId) {
        AccountExId = accountExId;
    }

    public String getCountryRegionCode() {
        return CountryRegionCode;
    }

    public void setCountryRegionCode(String countryRegionCode) {
        CountryRegionCode = countryRegionCode;
    }

    public String getUserExId() {
        return UserExId;
    }

    public void setUserExId(String userExId) {
        UserExId = userExId;
    }

    public String getSystemExId() {
        return SystemExId;
    }

    public void setSystemExId(String systemExId) {
        SystemExId = systemExId;
    }

    public String getServerExId() {
        return ServerExId;
    }

    public void setServerExId(String serverExId) {
        ServerExId = serverExId;
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
}
