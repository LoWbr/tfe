package init.crud1.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class PromotionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name="candidate", referencedColumnName = "id", nullable = false)
    private SportsMan candidate;

    @OneToOne
    @JoinColumn(name = "inDemand", referencedColumnName = "id", nullable = false)
    private Role inDemand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SportsMan getCandidate() {
        return candidate;
    }

    public void setCandidate(SportsMan candidate) {
        this.candidate = candidate;
    }

    public Role getInDemand() {
        return inDemand;
    }

    public void setInDemand(Role inDemand) {
        this.inDemand = inDemand;
    }

    public PromotionRequest() {
    }

    public PromotionRequest(SportsMan candidate, Role inDemand) {
        this.candidate = candidate;
        this.inDemand = inDemand;
    }
}
