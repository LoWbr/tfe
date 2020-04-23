package init.crud1.entity;

import com.sun.istack.Nullable;

import javax.persistence.*;

@Entity
public class News{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name="fk_target", referencedColumnName = "id")
    private SportsMan target;

    @OneToOne
    @JoinColumn(name="fk_source", referencedColumnName = "id")
    private SportsMan source;

    @OneToOne
    @JoinColumn(name="fk_activity", referencedColumnName = "id")
    private Activity activity;

    @Enumerated(EnumType.STRING)
    private NewsType type;

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

    public News(){}

    public News(SportsMan target, SportsMan source, Activity activity, NewsType type, boolean seen) {
        this.target = target;
        this.source = source;
        this.activity = activity;
        this.type = type;
        this.seen = seen;
    }
}
