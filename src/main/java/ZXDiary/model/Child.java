package ZXDiary.model;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@DynamicUpdate
@Data
@RequiredArgsConstructor
// @AllArgsConstructor
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String school;

    //Cechy do opisu i stanu
    private Boolean hasCertificateOfDisability;
    private Boolean hasSpecialEducation;
    private Boolean hasWWR;
    private Boolean hasADHD;
    private Boolean hasEpilepsy;

    private String coexistingConditions;
    private String echolalia;
    private String schematicBehaviors;
    private String rituals;
    private String ticsHabitsStims;

    @Range(min=0, max=5)
    private Byte doesInitializeGames;
    @Range(min=0, max=5)
    private Byte apathy;
    @Range(min=0, max=5)
    private Byte firstContact;
    @Range(min=0, max=5)
    private Byte eyeContact;
    @Range(min=0, max=5)
    private Byte reactingToName;
    @Range(min=0, max=5)
    private Byte understandingVerbalCommands;
    @Range(min=0, max=5)
    private Byte speechDevelopment;
    @Range(min=0, max=5)
    private Byte reactionToStimuli;
    @Range(min=0, max=5)
    private Byte hyperactivity;
    @Range(min=0, max=5)
    private Byte emotionalHyperactivity;
    @Range(min=0, max=5)
    private Byte reactionToNewSituations;
    @Range(min=0, max=5)
    private Byte groupWork;
    @Range(min=0, max=5)
    private Byte cleanlinessTraining;



    public Child(String firstName, String lastName, String school) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.school = school;
    }
}
