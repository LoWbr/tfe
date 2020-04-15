package init.crud1.entity;

import javax.persistence.*;

@Entity
public class News{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name="sportsManTarget", referencedColumnName = "id", nullable = false)
    private SportsMan target;

    @OneToOne
    @JoinColumn(name="sportsManSource", referencedColumnName = "id", nullable = false)
    private SportsMan source;

    @Enumerated(EnumType.STRING)
    private NewsType type;

    @Column(name="seen")
    private boolean seen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SportsMan getTarget() {
        return target;
    }

    public void setTarget(SportsMan target) {
        this.target = target;
    }

    public SportsMan getSource() {
        return source;
    }

    public void setSource(SportsMan source) {
        this.source = source;
    }

    public NewsType getType() {
        return type;
    }

    public void setType(NewsType type) {
        this.type = type;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
