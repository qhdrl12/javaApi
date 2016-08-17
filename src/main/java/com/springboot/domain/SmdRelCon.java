package com.springboot.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SMTD_CNTSCORE_MST")
public class SmdRelCon implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String ID_CONTENTS;
    private String REL_CNTS_ID;
    private String REL_FG_CD;
    private float REL_CNTS_SCORE;


    public SmdRelCon() {}

    public SmdRelCon(String ID_CONTENTS, String REL_CNTS_ID, String REL_FG_CD, float REL_CNTS_SCORE) {
        this.ID_CONTENTS = ID_CONTENTS;
        this.REL_CNTS_ID = REL_CNTS_ID;
        this.REL_FG_CD = REL_FG_CD;
        this.REL_CNTS_SCORE = REL_CNTS_SCORE;
    }

    @Override
    public String toString() {
        return String.format(
                "SMD RELATION CONTENTS [cid=%s, rid=%s, fg_cd=%s, r_score=%f]", ID_CONTENTS , REL_CNTS_ID, REL_FG_CD, REL_CNTS_SCORE);
    }

}
