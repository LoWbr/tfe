package init.crud1.form;


public class LevelForm {

    private String name;
    private Long place;
    private Integer maximumThreshold;
    private Double ratioPoints;

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

}
