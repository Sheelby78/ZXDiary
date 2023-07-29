package ZXDiary.model;


import ZXDiary.exception.CustomException;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@Data
public class Parent extends AppUser{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "child_id", referencedColumnName = "id")
    private Child child = null;

    public void addChild(Child child) {
            this.child = child;
    }

    public Boolean hasChild() {
        return !(this.child == null);
    }

}
