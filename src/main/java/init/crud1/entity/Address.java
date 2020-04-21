package init.crud1.entity;

import init.crud1.form.ActivityForm;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = true, nullable = false)
    private Long id;

    @Column(name = "number")
    private int number;

    @Column(name = "street", length = 60)
    private String street;

    @Column(name = "postalCode")
    private int postalCode;

    @Column(name = "city", length = 60)
    private String city;

    @Column(name = "country", length = 60)
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Address(){}

    public Address(ActivityForm activityForm) {
        this.number = activityForm.getNumber();
        this.street = activityForm.getStreet();
        this.postalCode = activityForm.getPostalCode();
        this.city = activityForm.getCity();
        this.country = activityForm.getCountry();
    }

    public void update(ActivityForm activityForm){
        this.number = activityForm.getNumber();
        this.street = activityForm.getStreet();
        this.postalCode = activityForm.getPostalCode();
        this.city = activityForm.getCity();
        this.country = activityForm.getCountry();
    }
}
