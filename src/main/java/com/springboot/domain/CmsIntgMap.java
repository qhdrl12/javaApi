package com.springboot.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ibong-gi on 2016. 8. 25..
 */

@Entity
@Table(name = "VCMS_INTGMETA_MAP")
public class CmsIntgMap {

    @EmbeddedId
    protected CmsIntgMap.PrimaryKey pKey;

    @Embeddable
    public static class PrimaryKey implements Serializable {
        @Column
        private String ID_MASTER;
        @Column
        private String ID_CONTENTS;
        @Column
        private String PRIMARY_YN;
    }

//    @ManyToOne
//    private CmsCon content;
//    public CmsCon getContent(){
//        return content;
//    }
//
//    public void setContent(CmsCon content){
//        this.content = content;
//    }

    private CmsIntgMap() {}

    public CmsIntgMap(String ID_MASTER, String ID_CONTENTS, String PRIMARY_YN) {
        this.pKey.ID_MASTER = ID_MASTER;
        this.pKey.ID_CONTENTS = ID_CONTENTS;
        this.pKey.PRIMARY_YN = PRIMARY_YN;
    }

    public String getCID(){
        return pKey.ID_CONTENTS;
    }

    public String getMID(){
        return pKey.ID_MASTER;
    }

    public String getPYN(){
        return pKey.PRIMARY_YN;
    }
}
