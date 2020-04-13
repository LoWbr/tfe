package init.crud1.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = true, nullable = false)
    private Long id;

    @Column(name="name",nullable = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<SportsMan> users;

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

    public List<SportsMan> getUsers() {
        return users;
    }

    public void setUsers(List<SportsMan> users) {
        this.users = users;
    }
}
