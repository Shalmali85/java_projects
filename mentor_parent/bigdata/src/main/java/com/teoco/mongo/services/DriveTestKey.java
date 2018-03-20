package com.teoco.mongo.services;

import java.math.BigDecimal;

/**
 * Created by roysha on 12/23/2015.
 * This class represents a unique data row for a given file type
 * Example of key 1=  lat,lon, pdcp throughput
 */
public class DriveTestKey {
    private Double latitude;
    private Double longitude;
    private Long time;
    private Long dlEarfcn;
    private Long cellIdentity;
    private Long pci;
    private String fileName;
    private Double totalRlcUplinkThroughput;
    private Double totalRlcDownlinkThroughput;
    public Boolean isParent = false;
    private Double servingRsrp0;
    private Double servingRsrp1;
    private Double servingRsrq0;
    private Double servingRsrq1;
    private Double servingSinr1;
    private Double servingMeasuredRsrp;
    private Double servingSinr0;
    private Double speed;
    private Long servingEarfcn;
    private Long servingPci;
    private Long neighborNodeId;
    private Double pmchBler;
    private Double pmchAverageSinr;
    private Long channelNo;
    private Double servingRsrp;
    private Double servingRsrq;
    private Double totalPdcpDlThroughput;
    private Double totalRlcDlThroughput;
    private Double totalPdcpUlThroughput;
    private Double totalRlcUlThroughput;
    private Double macUplinkThroughput;
    private Double macDownlinkThroughput;
    private String areaId;
    private String neighborEarfcn;
    private String neighborRsrp;
    private String neighborRsrq;
    private String neighborCell;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriveTestKey that = (DriveTestKey) o;

        if (!latitude.equals(that.latitude)) return false;
        if (!longitude.equals(that.longitude)) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (dlEarfcn != null ? !dlEarfcn.equals(that.dlEarfcn) : that.dlEarfcn != null) return false;
        if (cellIdentity != null ? !cellIdentity.equals(that.cellIdentity) : that.cellIdentity != null) return false;
        if (pci != null ? !pci.equals(that.pci) : that.pci != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (totalRlcUplinkThroughput != null ? !totalRlcUplinkThroughput.equals(that.totalRlcUplinkThroughput) : that.totalRlcUplinkThroughput != null)
            return false;
        if (totalRlcDownlinkThroughput != null ? !totalRlcDownlinkThroughput.equals(that.totalRlcDownlinkThroughput) : that.totalRlcDownlinkThroughput != null)
            return false;
        if (isParent != null ? !isParent.equals(that.isParent) : that.isParent != null) return false;
        if (servingRsrp0 != null ? !servingRsrp0.equals(that.servingRsrp0) : that.servingRsrp0 != null) return false;
        if (servingRsrp1 != null ? !servingRsrp1.equals(that.servingRsrp1) : that.servingRsrp1 != null) return false;
        if (servingRsrq0 != null ? !servingRsrq0.equals(that.servingRsrq0) : that.servingRsrq0 != null) return false;
        if (servingRsrq1 != null ? !servingRsrq1.equals(that.servingRsrq1) : that.servingRsrq1 != null) return false;
        if (servingSinr1 != null ? !servingSinr1.equals(that.servingSinr1) : that.servingSinr1 != null) return false;
        if (servingMeasuredRsrp != null ? !servingMeasuredRsrp.equals(that.servingMeasuredRsrp) : that.servingMeasuredRsrp != null)
            return false;
        if (servingSinr0 != null ? !servingSinr0.equals(that.servingSinr0) : that.servingSinr0 != null) return false;
        if (speed != null ? !speed.equals(that.speed) : that.speed != null) return false;
        if (servingEarfcn != null ? !servingEarfcn.equals(that.servingEarfcn) : that.servingEarfcn != null)
            return false;
        if (servingPci != null ? !servingPci.equals(that.servingPci) : that.servingPci != null) return false;
        if (neighborNodeId != null ? !neighborNodeId.equals(that.neighborNodeId) : that.neighborNodeId != null)
            return false;
        if (pmchBler != null ? !pmchBler.equals(that.pmchBler) : that.pmchBler != null) return false;
        if (pmchAverageSinr != null ? !pmchAverageSinr.equals(that.pmchAverageSinr) : that.pmchAverageSinr != null)
            return false;
        if (channelNo != null ? !channelNo.equals(that.channelNo) : that.channelNo != null) return false;
        if (servingRsrp != null ? !servingRsrp.equals(that.servingRsrp) : that.servingRsrp != null) return false;
        if (servingRsrq != null ? !servingRsrq.equals(that.servingRsrq) : that.servingRsrq != null) return false;
        if (totalPdcpDlThroughput != null ? !totalPdcpDlThroughput.equals(that.totalPdcpDlThroughput) : that.totalPdcpDlThroughput != null)
            return false;
        if (totalRlcDlThroughput != null ? !totalRlcDlThroughput.equals(that.totalRlcDlThroughput) : that.totalRlcDlThroughput != null)
            return false;
        if (totalPdcpUlThroughput != null ? !totalPdcpUlThroughput.equals(that.totalPdcpUlThroughput) : that.totalPdcpUlThroughput != null)
            return false;
        if (totalRlcUlThroughput != null ? !totalRlcUlThroughput.equals(that.totalRlcUlThroughput) : that.totalRlcUlThroughput != null)
            return false;
        if (macUplinkThroughput != null ? !macUplinkThroughput.equals(that.macUplinkThroughput) : that.macUplinkThroughput != null)
            return false;
        if (macDownlinkThroughput != null ? !macDownlinkThroughput.equals(that.macDownlinkThroughput) : that.macDownlinkThroughput != null)
            return false;
        if (areaId != null ? !areaId.equals(that.areaId) : that.areaId != null) return false;
        if (neighborEarfcn != null ? !neighborEarfcn.equals(that.neighborEarfcn) : that.neighborEarfcn != null)
            return false;
        if (neighborRsrp != null ? !neighborRsrp.equals(that.neighborRsrp) : that.neighborRsrp != null) return false;
        if (neighborRsrq != null ? !neighborRsrq.equals(that.neighborRsrq) : that.neighborRsrq != null) return false;
        return neighborCell != null ? neighborCell.equals(that.neighborCell) : that.neighborCell == null;

    }

    @Override
    public int hashCode() {
        int result = latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (dlEarfcn != null ? dlEarfcn.hashCode() : 0);
        result = 31 * result + (cellIdentity != null ? cellIdentity.hashCode() : 0);
        result = 31 * result + (pci != null ? pci.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (totalRlcUplinkThroughput != null ? totalRlcUplinkThroughput.hashCode() : 0);
        result = 31 * result + (totalRlcDownlinkThroughput != null ? totalRlcDownlinkThroughput.hashCode() : 0);
        result = 31 * result + (isParent != null ? isParent.hashCode() : 0);
        result = 31 * result + (servingRsrp0 != null ? servingRsrp0.hashCode() : 0);
        result = 31 * result + (servingRsrp1 != null ? servingRsrp1.hashCode() : 0);
        result = 31 * result + (servingRsrq0 != null ? servingRsrq0.hashCode() : 0);
        result = 31 * result + (servingRsrq1 != null ? servingRsrq1.hashCode() : 0);
        result = 31 * result + (servingSinr1 != null ? servingSinr1.hashCode() : 0);
        result = 31 * result + (servingMeasuredRsrp != null ? servingMeasuredRsrp.hashCode() : 0);
        result = 31 * result + (servingSinr0 != null ? servingSinr0.hashCode() : 0);
        result = 31 * result + (speed != null ? speed.hashCode() : 0);
        result = 31 * result + (servingEarfcn != null ? servingEarfcn.hashCode() : 0);
        result = 31 * result + (servingPci != null ? servingPci.hashCode() : 0);
        result = 31 * result + (neighborNodeId != null ? neighborNodeId.hashCode() : 0);
        result = 31 * result + (pmchBler != null ? pmchBler.hashCode() : 0);
        result = 31 * result + (pmchAverageSinr != null ? pmchAverageSinr.hashCode() : 0);
        result = 31 * result + (channelNo != null ? channelNo.hashCode() : 0);
        result = 31 * result + (servingRsrp != null ? servingRsrp.hashCode() : 0);
        result = 31 * result + (servingRsrq != null ? servingRsrq.hashCode() : 0);
        result = 31 * result + (totalPdcpDlThroughput != null ? totalPdcpDlThroughput.hashCode() : 0);
        result = 31 * result + (totalRlcDlThroughput != null ? totalRlcDlThroughput.hashCode() : 0);
        result = 31 * result + (totalPdcpUlThroughput != null ? totalPdcpUlThroughput.hashCode() : 0);
        result = 31 * result + (totalRlcUlThroughput != null ? totalRlcUlThroughput.hashCode() : 0);
        result = 31 * result + (macUplinkThroughput != null ? macUplinkThroughput.hashCode() : 0);
        result = 31 * result + (macDownlinkThroughput != null ? macDownlinkThroughput.hashCode() : 0);
        result = 31 * result + (areaId != null ? areaId.hashCode() : 0);
        result = 31 * result + (neighborEarfcn != null ? neighborEarfcn.hashCode() : 0);
        result = 31 * result + (neighborRsrp != null ? neighborRsrp.hashCode() : 0);
        result = 31 * result + (neighborRsrq != null ? neighborRsrq.hashCode() : 0);
        result = 31 * result + (neighborCell != null ? neighborCell.hashCode() : 0);
        return result;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getDlEarfcn() {
        return dlEarfcn;
    }

    public void setDlEarfcn(Long dlEarfcn) {
        this.dlEarfcn = dlEarfcn;
    }

    public Long getCellIdentity() {
        return cellIdentity;
    }

    public void setCellIdentity(Long cellIdentity) {
        this.cellIdentity = cellIdentity;
    }

    public Long getPci() {
        return pci;
    }

    public void setPci(Long pci) {
        this.pci = pci;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Double getTotalRlcUplinkThroughput() {
        return totalRlcUplinkThroughput;
    }

    public void setTotalRlcUplinkThroughput(Double totalRlcUplinkThroughput) {
        this.totalRlcUplinkThroughput = totalRlcUplinkThroughput;
    }

    public Double getTotalRlcDownlinkThroughput() {
        return totalRlcDownlinkThroughput;
    }

    public void setTotalRlcDownlinkThroughput(Double totalRlcDownlinkThroughput) {
        this.totalRlcDownlinkThroughput = totalRlcDownlinkThroughput;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public Double getServingRsrp0() {
        return servingRsrp0;
    }

    public void setServingRsrp0(Double servingRsrp0) {
        this.servingRsrp0 = servingRsrp0;
    }

    public Double getServingRsrp1() {
        return servingRsrp1;
    }

    public void setServingRsrp1(Double servingRsrp1) {
        this.servingRsrp1 = servingRsrp1;
    }

    public Double getServingRsrq0() {
        return servingRsrq0;
    }

    public void setServingRsrq0(Double servingRsrq0) {
        this.servingRsrq0 = servingRsrq0;
    }

    public Double getServingRsrq1() {
        return servingRsrq1;
    }

    public void setServingRsrq1(Double servingRsrq1) {
        this.servingRsrq1 = servingRsrq1;
    }

    public Double getServingSinr1() {
        return servingSinr1;
    }

    public void setServingSinr1(Double servingSinr1) {
        this.servingSinr1 = servingSinr1;
    }

    public Double getServingMeasuredRsrp() {
        return servingMeasuredRsrp;
    }

    public void setServingMeasuredRsrp(Double servingMeasuredRsrp) {
        this.servingMeasuredRsrp = servingMeasuredRsrp;
    }

    public Double getServingSinr0() {
        return servingSinr0;
    }

    public void setServingSinr0(Double servingSinr0) {
        this.servingSinr0 = servingSinr0;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Long getServingEarfcn() {
        return servingEarfcn;
    }

    public void setServingEarfcn(Long servingEarfcn) {
        this.servingEarfcn = servingEarfcn;
    }

    public Long getServingPci() {
        return servingPci;
    }

    public void setServingPci(Long servingPci) {
        this.servingPci = servingPci;
    }

    public Long getNeighborNodeId() {
        return neighborNodeId;
    }

    public void setNeighborNodeId(Long neighborNodeId) {
        this.neighborNodeId = neighborNodeId;
    }

    public Double getPmchBler() {
        return pmchBler;
    }

    public void setPmchBler(Double pmchBler) {
        this.pmchBler = pmchBler;
    }

    public Double getPmchAverageSinr() {
        return pmchAverageSinr;
    }

    public void setPmchAverageSinr(Double pmchAverageSinr) {
        this.pmchAverageSinr = pmchAverageSinr;
    }

    public Long getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(Long channelNo) {
        this.channelNo = channelNo;
    }

    public Double getServingRsrp() {
        return servingRsrp;
    }

    public void setServingRsrp(Double servingRsrp) {
        this.servingRsrp = servingRsrp;
    }

    public Double getServingRsrq() {
        return servingRsrq;
    }

    public void setServingRsrq(Double servingRsrq) {
        this.servingRsrq = servingRsrq;
    }

    public Double getTotalPdcpDlThroughput() {
        return totalPdcpDlThroughput;
    }

    public void setTotalPdcpDlThroughput(Double totalPdcpDlThroughput) {
        this.totalPdcpDlThroughput = totalPdcpDlThroughput;
    }

    public Double getTotalRlcDlThroughput() {
        return totalRlcDlThroughput;
    }

    public void setTotalRlcDlThroughput(Double totalRlcDlThroughput) {
        this.totalRlcDlThroughput = totalRlcDlThroughput;
    }

    public Double getTotalPdcpUlThroughput() {
        return totalPdcpUlThroughput;
    }

    public void setTotalPdcpUlThroughput(Double totalPdcpUlThroughput) {
        this.totalPdcpUlThroughput = totalPdcpUlThroughput;
    }

    public Double getTotalRlcUlThroughput() {
        return totalRlcUlThroughput;
    }

    public void setTotalRlcUlThroughput(Double totalRlcUlThroughput) {
        this.totalRlcUlThroughput = totalRlcUlThroughput;
    }

    public Double getMacUplinkThroughput() {
        return macUplinkThroughput;
    }

    public void setMacUplinkThroughput(Double macUplinkThroughput) {
        this.macUplinkThroughput = macUplinkThroughput;
    }

    public Double getMacDownlinkThroughput() {
        return macDownlinkThroughput;
    }

    public void setMacDownlinkThroughput(Double macDownlinkThroughput) {
        this.macDownlinkThroughput = macDownlinkThroughput;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getNeighborEarfcn() {
        return neighborEarfcn;
    }

    public void setNeighborEarfcn(String neighborEarfcn) {
        this.neighborEarfcn = neighborEarfcn;
    }

    public String getNeighborRsrp() {
        return neighborRsrp;
    }

    public void setNeighborRsrp(String neighborRsrp) {
        this.neighborRsrp = neighborRsrp;
    }

    public String getNeighborRsrq() {
        return neighborRsrq;
    }

    public void setNeighborRsrq(String neighborRsrq) {
        this.neighborRsrq = neighborRsrq;
    }

    public String getNeighborCell() {
        return neighborCell;
    }

    public void setNeighborCell(String neighborCell) {
        this.neighborCell = neighborCell;
    }
}
