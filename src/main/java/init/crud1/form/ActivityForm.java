package init.crud1.form;

import init.crud1.entity.*;

import javax.validation.constraints.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityForm {

    private Long id;

    @NotBlank(message = "This field cannot be empty!!")
    @Size(max=60, message="Maximum 60 characters")
    private String name;

    @NotBlank(message = "Give a short explanation")
    @Size(max=150, message="Maximum 150 characters")
    private String description;

    @NotBlank(message = "You have to give a date")
    private String plannedTo;

    @NotBlank(message = "You have to give an hour")
    private String hour;

    private ActivityType activity;

    private Level minimumLevel;
    private Level maximumLevel;

    @Max(120)
    @Min(30)
    private Integer duration;

    @Positive(message = "Get whole number")
    @Digits(integer=6,fraction=0)
    private int number;
    @NotBlank(message = "You should get a Street")
    @Size(max=60, message="Maximum 60 characters")
    private String street;
    @Positive(message = "Get whole number")
    @Digits(integer=6,fraction=0)
    private int postalCode;
    @NotBlank(message = "You should get a City")
    @Size(max=60, message="Maximum 60 characters")
    private String city;
    @NotBlank(message = "You should get a Country")
    @Size(max=80, message="Maximum 80 characters")
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivityType getActivity() {
        return activity;
    }

    public void setActivity(ActivityType activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlannedTo() {
        return plannedTo;
    }

    public void setPlannedTo(String plannedTo) {
        this.plannedTo = plannedTo;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Level getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(Level minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    public Level getMaximumLevel() {
        return maximumLevel;
    }

    public void setMaximumLevel(Level maximumLevel) {
        this.maximumLevel = maximumLevel;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ActivityForm() {
    }

    public ActivityForm(Activity activity, Address address) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.description = activity.getDescription();
        this.plannedTo = activity.getPlannedTo().toString();
        this.hour = activity.getHour().toString();
        this.activity = activity.getActivity();
        this.minimumLevel = activity.getMinimumLevel();
        this.maximumLevel = activity.getMaximumLevel();
        this.duration = activity.getDuration();
        this.number = address.getNumber();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.postalCode = address.getPostalCode();
        this.country = address.getCountry();
    }
}
