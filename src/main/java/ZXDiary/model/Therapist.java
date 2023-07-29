package ZXDiary.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Entity
@NoArgsConstructor
@Data
public class Therapist extends AppUser{

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "therapist_id", referencedColumnName = "id")
    private Set<Child> children;

    public void addChild(Child child) { this.children.add(child); }
    public void removeChild(Child child) { this.children.remove(child); }
    public boolean childIsSigned(Child child) { return this.children.contains(child); }
    public Set<Child> getChildSet() { return this.children; }
}
