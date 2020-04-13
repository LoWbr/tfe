package init.crud1.entity;

import init.crud1.form.SportsManForm;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class SportsMan  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = true, nullable = false)
    private Long id;

    @Column(name="firstName",length = 30)
    private String firstName;

    @Column(name="lastName",length = 30)
    private String lastName;

    @Column(name="description", columnDefinition="TEXT")
    private String description;

    @Column(name = "dateOfBirth", columnDefinition = "DATE")
    private LocalDate dateOfBirth;

    @Column(name="email",length = 40)
    private String email;

    @Column(name="password", length = 80)
    private String password;

    @OneToOne
    @JoinColumn(name="level", referencedColumnName = "id", nullable = false)
    Level level;

    @Column(name="weight")
    private Double weight;

    @Column(name="points", columnDefinition = "integer default 0")
    private Integer points;

    @Column(name="blocked", nullable = true, columnDefinition = "boolean default false")
    private Boolean blocked;

    @OneToMany(mappedBy = "creator")
    private List<Activity> createdActivities;

    @ManyToMany(mappedBy = "registered")
    private List<Activity> registeredActivities;

    @ManyToMany
    private List<SportsMan> contacts;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role")
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer energyExpenditure) {
        Integer earnedPoints = energyExpenditure / 10;
        this.points += earnedPoints;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public List<Activity> getCreatedActivities() {
        return createdActivities;
    }

    public void setCreatedActivities(List<Activity> createdActivities) {
        this.createdActivities = createdActivities;
    }

    public List<Activity> getRegisteredActivities() {
        return registeredActivities;
    }

    public void setRegisteredActivities(List<Activity> registeredActivities) {
        this.registeredActivities = registeredActivities;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<SportsMan> getContacts() {
        return contacts;
    }

    public void setContacts(List<SportsMan> contacts) {
        this.contacts = contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addContact(SportsMan sportsMan) {
        this.contacts.add(sportsMan);
    }

    public void removeContact(SportsMan sportsMan) {
        this.contacts.remove(sportsMan);
    }

    public SportsMan() {
    }

    public SportsMan(SportsManForm sportsManForm) {
        this.firstName = sportsManForm.getFirstname();
        this.lastName = sportsManForm.getLastname();
        this.email = sportsManForm.getMail();
        this.description = sportsManForm.getDescription();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dateOfBirth = LocalDate.parse(sportsManForm.getDateofBirth(),formatter).plusDays(1);
        this.weight = sportsManForm.getWeight();
        this.points = 0;
        this.blocked = false;
    }

    public void updateSportsMan(SportsManForm sportsManForm){
        this.firstName = sportsManForm.getFirstname();
        this.lastName = sportsManForm.getLastname();
        this.email = sportsManForm.getMail();
        this.description = sportsManForm.getDescription();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dateOfBirth = LocalDate.parse(sportsManForm.getDateofBirth(),formatter).plusDays(1);
        this.weight = sportsManForm.getWeight();
    }

    public boolean checkLevelStatus() {
        if(this.getPoints()>this.getLevel().getMaximumThreshold()){
            return true;
        }
        return false;
    }

}
