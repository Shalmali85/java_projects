package com.teoco.mongo.entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FileTemplateConfiguredColumn implements Serializable {
    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    /**
     * It defines the target column. It is, basically, the column that Sigma understands.
     */
    private String tc = null;

    /**
     * It defines the source column. It is, basically, the column that Vendor sends.
     */
    private String sc = null;

    /**
     * It defines the source column index. It is, basically, the colummn no. in
     * TSV or CSV file that Vendor sends.
     */
    private int i;
}


