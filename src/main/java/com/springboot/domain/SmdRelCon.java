package com.springboot.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SMTD_CNTSCORE_MST")
public class SmdRelCon implements Serializable{

    @EmbeddedId
    protected ContentKey conKey;

    @Embeddable
    public static class ContentKey implements Serializable {
        @Column
        private String ID_CONTENTS;
        @Column
        private String REL_CNTS_ID;
    }

//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private String ID_CONTENTS;
//    private String REL_CNTS_ID;
    @Column
    private String REL_FG_CD;
    @Column
    private float REL_CNTS_SCORE;


    private SmdRelCon() {}

    public SmdRelCon(String ID_CONTENTS, String REL_CNTS_ID, String REL_FG_CD, float REL_CNTS_SCORE) {

        this.conKey.ID_CONTENTS = ID_CONTENTS;
        this.conKey.REL_CNTS_ID = REL_CNTS_ID;
        this.REL_FG_CD = REL_FG_CD;
        this.REL_CNTS_SCORE = REL_CNTS_SCORE;
    }

    public String getCID(){
        return conKey.ID_CONTENTS;
    }

    public String getRID(){
        return conKey.REL_CNTS_ID;
    }

    public String getFgCd(){
        return REL_FG_CD;
    }

    public float getRelScore(){
        return REL_CNTS_SCORE;
    }

    @Override
    public String toString() {
        return String.format(
                "SMD RELATION CONTENTS [cid=%s, rid=%s, fg_cd=%s, r_score=%f]", conKey.ID_CONTENTS , conKey.REL_CNTS_ID, REL_FG_CD, REL_CNTS_SCORE);
    }

}
