package com.teoco.mongo.entity;


import java.math.BigDecimal;

/**
 * Created by roysha on 12/10/2015.
 */

public class DriveTestMetadata {

    private Integer driveTestId;
    private String fileName;
    private Long Imei;
    private Long timeStamp;
    private Long createdDate;
    private Double position;
    private Double latitude;
    private Double longitude;
    private String deviceName;
    private Long cellIdentity;
    private Long dlearfcn;
    private Long servingNodeId;
    private Long servingCellId;
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
    private String neighborEarfcn;
    private Long neighborCellCount;
    private String neighborCellId;
    private String neighborPci;
    private Long physicalCellId;
    private Long timingAdvance;
    private String mcs;
    private String areaId;
    private Double pmchBler;
    private Double pmchAverageSinr;
    private Long channelNo;
    private Double servingRsrp;
    private Double servingRsrq;
    private String neighborRsrp;
    private String neighborRsrq;
    private Double totalPdcpDlThroughput;
    private Double totalRlcDlThroughput;
    private Double totalPdcpUlThroughput;
    private Double totalRlcUlThroughput;
    private Double macUplinkThroughput;
    private Double macDownlinkThroughput;

    public Integer getDriveTestId() {
        return driveTestId;
    }

    public void setDriveTestId(Integer driveTestId) {
        this.driveTestId = driveTestId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getImei() {
        return Imei;
    }

    public void setImei(Long imei) {
        Imei = imei;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Long getCellIdentity() {
        return cellIdentity;
    }

    public void setCellIdentity(Long cellIdentity) {
        this.cellIdentity = cellIdentity;
    }

    public Long getDlearfcn() {
        return dlearfcn;
    }

    public void setDlearfcn(Long dlearfcn) {
        this.dlearfcn = dlearfcn;
    }

    public Long getServingNodeId() {
        return servingNodeId;
    }

    public void setServingNodeId(Long servingNodeId) {
        this.servingNodeId = servingNodeId;
    }

    public Long getServingCellId() {
        return servingCellId;
    }

    public void setServingCellId(Long servingCellId) {
        this.servingCellId = servingCellId;
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

    public String getNeighborEarfcn() {
        return neighborEarfcn;
    }

    public void setNeighborEarfcn(String neighborEarfcn) {
        this.neighborEarfcn = neighborEarfcn;
    }

    public Long getNeighborCellCount() {
        return neighborCellCount;
    }

    public void setNeighborCellCount(Long neighborCellCount) {
        this.neighborCellCount = neighborCellCount;
    }

    public String getNeighborPci() {
        return neighborPci;
    }

    public void setNeighborPci(String neighborPci) {
        this.neighborPci = neighborPci;
    }

    public Long getPhysicalCellId() {
        return physicalCellId;
    }

    public void setPhysicalCellId(Long physicalCellId) {
        this.physicalCellId = physicalCellId;
    }

    public Long getTimingAdvance() {
        return timingAdvance;
    }

    public void setTimingAdvance(Long timingAdvance) {
        this.timingAdvance = timingAdvance;
    }

    public String getMcs() {
        return mcs;
    }

    public void setMcs(String mcs) {
        this.mcs = mcs;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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

    public String getNeighborCellId() {
        return neighborCellId;
    }

    public void setNeighborCellId(String neighborCellId) {
        this.neighborCellId = neighborCellId;
    }
}
