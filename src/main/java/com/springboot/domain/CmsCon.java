package com.springboot.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ibong-gi on 2016. 8. 25..
 */

@Entity
@Table(name = "VCMS_CONTENTS")
public class CmsCon implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    private String ID_CONTENTS;

    @OneToMany
    @JoinColumn(name = "ID_CONTENTS")
    private Collection<CmsIntgMap> intgMap;

    @Column
    private String AT_CONTENTS;

    @Column
    private String TITLE;

    @Column
    private String ORG_TITLE;

    @Column
    private String ID_SERIES;

    @Column
    private String ID_MASTER;

    private CmsCon() {}

    public CmsCon(String ID_CONTENTS, String AT_CONTENTS, String TITLE, String ORG_TITLE, String ID_SERIES, String ID_MASTER) {

        this.ID_CONTENTS = ID_CONTENTS;
        this.AT_CONTENTS = AT_CONTENTS;
        this.TITLE = TITLE;
        this.ORG_TITLE = ORG_TITLE;
        this.ID_SERIES = ID_SERIES;
        this.ID_MASTER = ID_MASTER;
    }

    public String getCID(){
        return ID_CONTENTS;
    }

    public String getTitle(){
        return TITLE != null ? TITLE : "";
    }

    public String getMID(){
        return ID_MASTER != null ? ID_MASTER : "";
    }

    public Collection<CmsIntgMap> getIntgMap(){
        return intgMap;
    }

    public void setIntgMap(List<CmsIntgMap> intgMap){
        this.intgMap = intgMap;
    }


    @Override
    public String toString() {
        return String.format(
                "VCMS_CONTENTS RELATION CONTENTS [cid=%s, mid=%s, title=%s, ID_SERIES=%s]", ID_CONTENTS , ID_MASTER, TITLE, ID_SERIES);
    }
}
