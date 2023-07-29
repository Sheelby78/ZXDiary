package ZXDiary.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
public class ChildDTO {
    private String firstName;
    private String lastName;
    private String school;

    //Cechy do opisu i stanu
    private boolean hasCertificateOfDisability;
    private boolean hasSpecialEducation;
    private boolean hasWWR;
    private boolean hasADHD;
    private boolean hasEpilepsy;


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
}
