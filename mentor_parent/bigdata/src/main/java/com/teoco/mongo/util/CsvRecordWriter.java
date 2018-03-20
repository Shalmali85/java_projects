package com.teoco.mongo.util;

import com.teoco.mongo.entity.DriveTestMetadata;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by roysha on 4/10/2016.
 */
public class CsvRecordWriter<E> {

    private static CsvRecordWriter instance=null;
    private CsvRecordWriter(){

    }

    public static CsvRecordWriter getInstance(){
        if(instance==null)
            instance=new CsvRecordWriter();
        return  instance;
    }
    public  String toCSV(DriveTestMetadata driveTestMetadata) {



        StringBuilder driveTest = new StringBuilder();
        driveTest.append((driveTestMetadata.getDriveTestId() != null) ? driveTestMetadata.getDriveTestId() : "").append(",");
        driveTest.append((driveTestMetadata.getFileName() != null) ?driveTestMetadata.getFileName() : "").append(",");
        driveTest.append((driveTestMetadata.getCreatedDate() != null) ? driveTestMetadata.getCreatedDate() : "").append(",");
        driveTest.append((driveTestMetadata.getTimeStamp() != null) ? driveTestMetadata.getTimeStamp() : "").append(",");
        driveTest.append((driveTestMetadata.getLatitude() != null) ? driveTestMetadata.getLatitude():"").append(",");
        driveTest.append((driveTestMetadata.getLongitude() != null) ? driveTestMetadata.getLongitude(): "").append(",");
        driveTest.append((driveTestMetadata.getDeviceName() != null) ? driveTestMetadata.getDeviceName() : "").append(",");
        driveTest.append((driveTestMetadata.getImei() != null) ? driveTestMetadata.getImei() : "").append(",");
        driveTest.append((driveTestMetadata.getCellIdentity() != null) ? driveTestMetadata.getCellIdentity() : "").append(",");
        driveTest.append((driveTestMetadata.getPhysicalCellId() != null) ? driveTestMetadata.getPhysicalCellId() : "").append(",");
        driveTest.append((driveTestMetadata.getDlearfcn() != null) ? driveTestMetadata.getDlearfcn() : "").append(",");

        driveTest.append((driveTestMetadata.getServingNodeId() != null) ? driveTestMetadata.getServingNodeId() : "").append(",");
        driveTest.append((driveTestMetadata.getServingCellId() != null) ? driveTestMetadata.getServingCellId() : "").append(",");
        driveTest.append((driveTestMetadata.getServingRsrp() != null) ? driveTestMetadata.getServingRsrp() : "").append(",");
        driveTest.append((driveTestMetadata.getServingRsrq() != null) ? driveTestMetadata.getServingRsrq() : "").append(",");
        driveTest.append((driveTestMetadata.getServingRsrp0() != null) ? driveTestMetadata.getServingRsrp0() : "").append(",");
        driveTest.append((driveTestMetadata.getServingRsrp1() != null) ? driveTestMetadata.getServingRsrp1() : "").append(",");
        driveTest.append((driveTestMetadata.getServingRsrq0() != null) ? driveTestMetadata.getServingRsrq0() : "").append(",");
        driveTest.append((driveTestMetadata.getServingRsrq1() != null) ? driveTestMetadata.getServingRsrq1() : "").append(",");
        driveTest.append((driveTestMetadata.getServingSinr0() != null) ? driveTestMetadata.getServingSinr0() : "").append(",");
        driveTest.append((driveTestMetadata.getServingSinr1() != null) ? driveTestMetadata.getServingSinr1() : "").append(",");
        driveTest.append((driveTestMetadata.getServingEarfcn() != null) ? driveTestMetadata.getServingEarfcn() : "").append(",");
        driveTest.append((driveTestMetadata.getServingPci() != null) ? driveTestMetadata.getServingPci() : "").append(",");
        driveTest.append((driveTestMetadata.getServingMeasuredRsrp() != null) ? driveTestMetadata.getServingMeasuredRsrp() : "").append(",");

        driveTest.append((driveTestMetadata.getNeighborNodeId() != null) ? driveTestMetadata.getNeighborNodeId() : "").append(",");
        driveTest.append((driveTestMetadata.getNeighborCellId() != null) ? driveTestMetadata.getNeighborCellId() : "").append(",");

        driveTest.append((driveTestMetadata.getNeighborCellCount() != null) ? driveTestMetadata.getNeighborCellCount() : "").append(",");

        driveTest.append((driveTestMetadata.getNeighborRsrq() != null) ? driveTestMetadata.getNeighborRsrq() : "").append(",");

        driveTest.append((driveTestMetadata.getNeighborRsrp() != null) ? driveTestMetadata.getNeighborRsrp() : "").append(",");

        driveTest.append((driveTestMetadata.getNeighborEarfcn() != null) ? driveTestMetadata.getNeighborEarfcn()  : "").append(",");

        driveTest.append((driveTestMetadata.getNeighborPci() != null) ? driveTestMetadata.getNeighborPci()  : "").append(",");

        driveTest.append((driveTestMetadata.getPmchBler() != null) ? driveTestMetadata.getPmchBler() : "").append(",");
        driveTest.append((driveTestMetadata.getPmchAverageSinr() != null) ? driveTestMetadata.getPmchAverageSinr() : "").append(",");

        driveTest.append((driveTestMetadata.getChannelNo() != null) ? driveTestMetadata.getChannelNo() : "").append(",");

        driveTest.append((driveTestMetadata.getMacUplinkThroughput() != null) ?driveTestMetadata.getMacUplinkThroughput() : "").append(",");
        driveTest.append((driveTestMetadata.getMacDownlinkThroughput() != null) ? driveTestMetadata.getMacDownlinkThroughput()  : "").append(",");

        driveTest.append((driveTestMetadata.getTotalPdcpDlThroughput() != null) ? driveTestMetadata.getTotalPdcpDlThroughput() : "").append(",");
        driveTest.append((driveTestMetadata.getTotalPdcpUlThroughput() != null) ? driveTestMetadata.getTotalPdcpUlThroughput() : "").append(",");

        driveTest.append((driveTestMetadata.getTotalRlcDlThroughput() != null) ? driveTestMetadata.getTotalRlcDlThroughput() : "").append(",");
        driveTest.append((driveTestMetadata.getTotalRlcUlThroughput() != null) ? driveTestMetadata.getTotalRlcUlThroughput() : "").append(",");

        driveTest.append((driveTestMetadata.getTimingAdvance() != null) ? driveTestMetadata.getTimingAdvance() : "").append(",");
        driveTest.append((driveTestMetadata.getMcs() != null) ? driveTestMetadata.getMcs() : "").append(",");
        driveTest.append((driveTestMetadata.getAreaId() != null) ? driveTestMetadata.getAreaId() : "").append(",");
        driveTest.append((driveTestMetadata.getPosition() != null) ? driveTestMetadata.getPosition() : "").append(",");
        driveTest.append((driveTestMetadata.getSpeed() != null) ? driveTestMetadata.getSpeed() : "").append("\n");
        return driveTest.toString();

    }
}
