package init.crud1.entity;

import init.crud1.form.LevelForm;

import javax.persistence.*;

@Entity
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "place")
    private Long place;

    @Column(name = "maximum_threshold")
    private Integer maximumThreshold;

    @Column(name = "ratio_points")
    private Double ratioPoints;

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

    public Long getPlace() {
        return place;
    }

    public void setPlace(Long place) {
        this.place = place;
    }

    public Integer getMaximumThreshold() {
        return maximumThreshold;
    }

    public void setMaximumThreshold(Integer maximumThreshold) {
        this.maximumThreshold = maximumThreshold;
    }

    public Double getRatioPoints() {
        return ratioPoints;
    }

    public void setRatioPoints(Double ratioPoints) {
        this.ratioPoints = ratioPoints;
    }

    public Level(){}

    public void update(LevelForm levelForm){
        this.name = levelForm.getName();
        this.maximumThreshold = levelForm.getMaximumThreshold();
        this.ratioPoints = levelForm.getRatioPoints();
    }

}
