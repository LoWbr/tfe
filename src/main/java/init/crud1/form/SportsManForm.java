package init.crud1.form;

import init.crud1.entity.SportsMan;

import javax.validation.constraints.*;
import java.sql.Date;
import java.time.LocalDate;


/*
@SamePassword
*/
public class SportsManForm {

    private Long id;

    @NotBlank(message = "Enter a firstname")
    private String firstname;

    @NotBlank(message = "Enter a lastname")
    private String lastname;

    @Size(max=100, message="Maximum 100 characters")
    private String description;

    @NotBlank(message = "Enter a mail")
    @Email
    private String mail;

    @NotBlank
    @Size(min=8, max=60, message="Between 8 and 15")
    private String password;

    private String confirmPassword;

    @NotBlank
  /* @Past*/
    private String dateofBirth;

    @Positive
    private Double weight;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public SportsManForm() {
    }

    public SportsManForm(SportsMan sportsMan) {
        this.firstname = sportsMan.getFirstName();
        this.lastname = sportsMan.getLastName();
        this.description = sportsMan.getDescription();
        this.mail = sportsMan.getEmail();
        this.weight = sportsMan.getWeight();
        this.dateofBirth = sportsMan.getDateOfBirth().toString();
        this.password = sportsMan.getPassword();
    }

    ;
}
