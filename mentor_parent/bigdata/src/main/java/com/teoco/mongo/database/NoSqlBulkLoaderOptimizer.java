package com.teoco.mongo.database;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;
import com.teoco.mongo.entity.DriveTestMetadata;
import com.teoco.mongo.rop.RopPeriod;
import com.teoco.mongo.services.DriveTestKey;
import com.teoco.mongo.services.DriveTestParser;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by shalmali on 8/8/16.
 */
public class NoSqlBulkLoaderOptimizer implements IBulkLoaderMapOptimizer {
    public static String deviceName=null;

    @Override
    public HashMap<DriveTestKey, List<DriveTestMetadata>> optimizeMap(HashMap<DriveTestKey, List<DriveTestMetadata>> hashMapDriveTestData,RopPeriod rop) throws IOException {
            List<DriveTestMetadata> list;
            HashMap<DriveTestKey, List<DriveTestMetadata>> hashMapDriveTestTempData = new HashMap<DriveTestKey, List<DriveTestMetadata>>();
            DriveTestKey driveTestKey;
            for (Map.Entry<DriveTestKey, List<DriveTestMetadata>> entry : hashMapDriveTestData.entrySet()) {
                driveTestKey=new DriveTestKey();
                driveTestKey.setLatitude(entry.getKey().getLatitude());
                driveTestKey.setLongitude(entry.getKey().getLongitude());
                driveTestKey.setTime(entry.getKey().getTime());
                for (DriveTestMetadata driveTestMetadata : entry.getValue()) {
                    if(deviceName!=null)
                        driveTestMetadata.setDeviceName(deviceName);
                    if (!hashMapDriveTestTempData.isEmpty()&& hashMapDriveTestTempData.containsKey(driveTestKey)) {
                        list=hashMapDriveTestTempData.get(driveTestKey);
                        list.add(driveTestMetadata);
                        hashMapDriveTestTempData.put(driveTestKey, list);
                    } else {
                        list = new ArrayList();
                        list.add(driveTestMetadata);
                        hashMapDriveTestTempData.put(driveTestKey, list);
                    }
                }
            }

            // reinitialized after every Rop period
        return hashMapDriveTestTempData;

    }

   @Override
    public void writeToDocumentorFile(HashMap<DriveTestKey, List<DriveTestMetadata>> hashMapDriveTestTempData,int fileId,RopPeriod rop) throws IOException {
        {
            if(DBConnection.getInstance().getCollectionName("drive_test_metadata")==null){
            writeToDocumentorFileFromMongoClient(hashMapDriveTestTempData, fileId,rop);
        }
            else {
                BulkWriteOperation bulkWriteOperation = DBConnection.getInstance().getCollectionName("drive_test_metadata").initializeUnorderedBulkOperation();

                hashMapDriveTestTempData.entrySet().forEach(driveTestKey-> {
                    Map<String, Object> documentMap = new HashMap<String, Object>();
                    documentMap.put(DriveTestParser.DRIVE_TEST_ID, fileId);
                    documentMap.put(DriveTestParser.FILE_NAME, driveTestKey.getKey().getFileName());
                    documentMap.put(DriveTestParser.TIMESTAMP, driveTestKey.getKey().getTime());
                    documentMap.put(DriveTestParser.LATITUDE, driveTestKey.getKey().getLatitude());
                    documentMap.put(DriveTestParser.LONGITUDE, driveTestKey.getKey().getLongitude());
                    final ArrayList[] list = new ArrayList[1];
                    DBObject basicDBObjects = new BasicDBObject();
                    driveTestKey.getValue().forEach(driveTestMetadata->{

                        if (driveTestMetadata.getServingNodeId() != null)
                            documentMap.put(DriveTestParser.SERVING_NODE_ID, driveTestMetadata.getServingNodeId());
                        if (driveTestMetadata.getServingPci() != null)
                            documentMap.put(DriveTestParser.SERVING_PCI, driveTestMetadata.getServingPci());
                        if (driveTestMetadata.getServingCellId() != null)
                            documentMap.put(DriveTestParser.SERVING_CELL_ID, driveTestMetadata.getServingCellId());
                        if (driveTestMetadata.getCellIdentity() != null)
                            documentMap.put(DriveTestParser.CELL_IDENTITY, driveTestMetadata.getCellIdentity());
                        if (driveTestMetadata.getPhysicalCellId() != null)
                            documentMap.put(DriveTestParser.PCI, driveTestMetadata.getPhysicalCellId());
                        if (driveTestMetadata.getDlearfcn() != null)
                            documentMap.put(DriveTestParser.DL_E_ARFCN, driveTestMetadata.getDlearfcn());
                        if (driveTestMetadata.getServingEarfcn() != null)
                            documentMap.put(DriveTestParser.SERVING_EARFCN, driveTestMetadata.getServingEarfcn());

                        if (driveTestMetadata.getTotalRlcUlThroughput() != null) {
                            if (documentMap.get(DriveTestParser.TOTAL_RLC_UL_THROUGHPUT) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.TOTAL_RLC_UL_THROUGHPUT);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getTotalRlcUlThroughput());
                            documentMap.put(DriveTestParser.TOTAL_RLC_UL_THROUGHPUT, list[0]);
                        }

                        if (driveTestMetadata.getTotalRlcDlThroughput() != null) {
                            if (documentMap.get(DriveTestParser.TOTAL_RLC_DL_THROUGHPUT) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.TOTAL_RLC_DL_THROUGHPUT);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getTotalRlcDlThroughput());
                            documentMap.put(DriveTestParser.TOTAL_RLC_DL_THROUGHPUT, list[0]);
                        }
                        if (driveTestMetadata.getServingMeasuredRsrp() != null) {
                            if (documentMap.get(DriveTestParser.SERVING_MEASURED_RSRP_0) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.SERVING_MEASURED_RSRP_0);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getServingMeasuredRsrp());
                            documentMap.put(DriveTestParser.SERVING_MEASURED_RSRP_0, list[0]);
                        }
                        if (driveTestMetadata.getServingRsrp() != null) {
                            if (documentMap.get(DriveTestParser.SERVING_RSRP) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.SERVING_RSRP);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getServingRsrp());
                            documentMap.put(DriveTestParser.SERVING_RSRP, list[0]);
                        }
                        if (driveTestMetadata.getServingRsrq() != null) {
                            if (documentMap.get(DriveTestParser.SERVING_RSRQ) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.SERVING_RSRQ);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getServingRsrq());
                            documentMap.put(DriveTestParser.SERVING_RSRQ, list[0]);
                        }
                        if (driveTestMetadata.getServingRsrp0() != null) {
                            if (documentMap.get(DriveTestParser.SERVING_RSRP_0) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.SERVING_RSRP_0);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getServingRsrp0());
                            documentMap.put(DriveTestParser.SERVING_RSRP_0, list[0]);
                        }
                        if (driveTestMetadata.getServingRsrp1() != null) {
                            if (documentMap.get(DriveTestParser.SERVING_RSRP_1) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.SERVING_RSRP_1);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getServingRsrp1());
                            documentMap.put(DriveTestParser.SERVING_RSRP_1, list[0]);
                        }
                        if (driveTestMetadata.getServingRsrq0() != null) {
                            if (documentMap.get(DriveTestParser.SERVING_RSRQ_0) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.SERVING_RSRQ_0);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getServingRsrq0());
                            documentMap.put(DriveTestParser.SERVING_RSRQ_0, list[0]);
                        }
                        if (driveTestMetadata.getServingRsrq1() != null) {
                            if (documentMap.get(DriveTestParser.SERVING_RSRQ_1) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.SERVING_RSRQ_1);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getServingRsrq1());
                            documentMap.put(DriveTestParser.SERVING_RSRQ_1, list[0]);
                        }
                        if (driveTestMetadata.getServingSinr0() != null) {
                            if (documentMap.get(DriveTestParser.SERVING_SINR_0) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.SERVING_SINR_0);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getServingSinr0());
                            documentMap.put(DriveTestParser.SERVING_SINR_0, list[0]);
                        }
                        if (driveTestMetadata.getServingSinr1() != null) {
                            if (documentMap.get(DriveTestParser.SERVING_SINR_1) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.SERVING_SINR_1);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getServingSinr0());
                            documentMap.put(DriveTestParser.SERVING_SINR_1, list[0]);
                        }
                        if (driveTestMetadata.getNeighborRsrp() != null) {
                            if (documentMap.get(DriveTestParser.NEIGHBOR_RSRP) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.NEIGHBOR_RSRP);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getNeighborRsrp());
                            documentMap.put(DriveTestParser.NEIGHBOR_RSRP, list[0]);
                        }
                        if (driveTestMetadata.getNeighborRsrq() != null) {
                            if (documentMap.get(DriveTestParser.NEIGHBOR_RSRQ) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.NEIGHBOR_RSRQ);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getNeighborRsrq());
                            documentMap.put(DriveTestParser.NEIGHBOR_RSRQ, list[0]);
                        }
                        if (driveTestMetadata.getNeighborCellId() != null) {
                            if (documentMap.get(DriveTestParser.NEIGHBOR_CELL_ID) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.NEIGHBOR_CELL_ID);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getNeighborCellId());
                            documentMap.put(DriveTestParser.NEIGHBOR_CELL_ID, list[0]);
                        }
                        if (driveTestMetadata.getNeighborPci() != null) {
                            if (documentMap.get(DriveTestParser.NEIGHBOR_PCI) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.NEIGHBOR_PCI);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getNeighborPci());
                            documentMap.put(DriveTestParser.NEIGHBOR_PCI, list[0]);
                        }
                        if (driveTestMetadata.getNeighborEarfcn() != null) {
                            if (documentMap.get(DriveTestParser.NEIGHBOR_EARFCN) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.NEIGHBOR_EARFCN);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getNeighborEarfcn());
                            documentMap.put(DriveTestParser.NEIGHBOR_EARFCN, list[0]);
                        }
                        if (driveTestMetadata.getNeighborNodeId() != null) {
                            if (documentMap.get(DriveTestParser.NEIGHBOR_NODE_ID) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.NEIGHBOR_NODE_ID);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getNeighborNodeId());
                            documentMap.put(DriveTestParser.NEIGHBOR_NODE_ID, list[0]);
                        }
                        if (driveTestMetadata.getTotalPdcpDlThroughput() != null) {
                            if (documentMap.get(DriveTestParser.TOTAL_PDCP_DL_THROUGHPUT) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.TOTAL_PDCP_DL_THROUGHPUT);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getTotalPdcpDlThroughput());
                            documentMap.put(DriveTestParser.TOTAL_PDCP_DL_THROUGHPUT, list[0]);
                        }
                        if (driveTestMetadata.getTotalPdcpUlThroughput() != null) {
                            if (documentMap.get(DriveTestParser.TOTAL_PDCP_UL_THROUGHPUT) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.TOTAL_PDCP_UL_THROUGHPUT);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getTotalPdcpUlThroughput());
                            documentMap.put(DriveTestParser.TOTAL_PDCP_UL_THROUGHPUT, list[0]);
                        }
                        if (driveTestMetadata.getPmchAverageSinr() != null) {
                            if (documentMap.get(DriveTestParser.PMCH_AVERAGE_SinR) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.PMCH_AVERAGE_SinR);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getPmchAverageSinr());
                            documentMap.put(DriveTestParser.PMCH_AVERAGE_SinR, list[0]);
                        }
                        if (driveTestMetadata.getPmchBler() != null) {
                            if (documentMap.get(DriveTestParser.PMCH_BLER) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.PMCH_BLER);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getPmchBler());
                            documentMap.put(DriveTestParser.PMCH_BLER, list[0]);
                        }
                        if (driveTestMetadata.getMacDownlinkThroughput() != null) {
                            if (documentMap.get(DriveTestParser.MAC_DOWNLINK_THROUGHPUT) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.MAC_DOWNLINK_THROUGHPUT);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getMacDownlinkThroughput());
                            documentMap.put(DriveTestParser.MAC_DOWNLINK_THROUGHPUT, list[0]);
                        }
                        if (driveTestMetadata.getMacUplinkThroughput() != null) {
                            if (documentMap.get(DriveTestParser.MAC_UPLINK_THROUGHPUT) != null) {
                                list[0] = (ArrayList) documentMap.get(DriveTestParser.MAC_UPLINK_THROUGHPUT);
                            } else {
                                list[0] = new ArrayList();
                            }
                            list[0].add(driveTestMetadata.getMacUplinkThroughput());
                            documentMap.put(DriveTestParser.MAC_UPLINK_THROUGHPUT, list[0]);
                        }


                        basicDBObjects.putAll(documentMap);
                        bulkWriteOperation.insert(basicDBObjects);


                    });

                });
                bulkWriteOperation.execute();
            }
        }
    }


    public void writeToDocumentorFileFromMongoClient(HashMap<DriveTestKey, List<DriveTestMetadata>> hashMapDriveTestTempData,int fileId,RopPeriod rop) throws IOException {
            List<WriteModel<Document>> docList=new ArrayList();
        hashMapDriveTestTempData.entrySet().forEach(driveTestKey->{
                Map<String, Object> documentMap = new HashMap<>();
                documentMap.put(DriveTestParser.DRIVE_TEST_ID, fileId);
                documentMap.put(DriveTestParser.FILE_NAME, driveTestKey.getKey().getFileName());
                documentMap.put(DriveTestParser.TIMESTAMP, driveTestKey.getKey().getTime());
                documentMap.put(DriveTestParser.LATITUDE, driveTestKey.getKey().getLatitude());
                documentMap.put(DriveTestParser.LONGITUDE, driveTestKey.getKey().getLongitude());
            final ArrayList[] list = new ArrayList[1];
            driveTestKey.getValue().forEach(driveTestMetadata->{
                    if(driveTestMetadata.getServingNodeId()!=null)
                        documentMap.put(DriveTestParser.SERVING_NODE_ID,driveTestMetadata.getServingNodeId());
                    if(driveTestMetadata.getServingPci()!=null)
                        documentMap.put(DriveTestParser.SERVING_PCI,driveTestMetadata.getServingPci());
                    if(driveTestMetadata.getServingCellId()!=null)
                        documentMap.put(DriveTestParser.SERVING_CELL_ID,driveTestMetadata.getServingCellId());
                    if(driveTestMetadata.getCellIdentity()!=null)
                        documentMap.put(DriveTestParser.CELL_IDENTITY,driveTestMetadata.getCellIdentity());
                    if(driveTestMetadata.getPhysicalCellId()!=null)
                        documentMap.put(DriveTestParser.PCI,driveTestMetadata.getPhysicalCellId());
                    if(driveTestMetadata.getDlearfcn()!=null)
                        documentMap.put(DriveTestParser.DL_E_ARFCN,driveTestMetadata.getDlearfcn());
                    if(driveTestMetadata.getServingEarfcn()!=null)
                        documentMap.put(DriveTestParser.SERVING_EARFCN,driveTestMetadata.getServingEarfcn());

                    if(driveTestMetadata.getTotalRlcUlThroughput()!=null)
                    {
                        if(documentMap.get(DriveTestParser.TOTAL_RLC_UL_THROUGHPUT)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.TOTAL_RLC_UL_THROUGHPUT);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getTotalRlcUlThroughput());
                        documentMap.put(DriveTestParser.TOTAL_RLC_UL_THROUGHPUT, list[0]);
                    }

                    if(driveTestMetadata.getTotalRlcDlThroughput()!=null)
                    {
                        if(documentMap.get(DriveTestParser.TOTAL_RLC_DL_THROUGHPUT)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.TOTAL_RLC_DL_THROUGHPUT);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getTotalRlcDlThroughput());
                        documentMap.put(DriveTestParser.TOTAL_RLC_DL_THROUGHPUT, list[0]);
                    }
                    if(driveTestMetadata.getServingMeasuredRsrp()!=null)
                    {
                        if(documentMap.get(DriveTestParser.SERVING_MEASURED_RSRP_0)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.SERVING_MEASURED_RSRP_0);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getServingMeasuredRsrp());
                        documentMap.put(DriveTestParser.SERVING_MEASURED_RSRP_0, list[0]);
                    }
                    if(driveTestMetadata.getServingRsrp()!=null)
                    {
                        if(documentMap.get(DriveTestParser.SERVING_RSRP)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.SERVING_RSRP);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getServingRsrp());
                        documentMap.put(DriveTestParser.SERVING_RSRP, list[0]);
                    }
                    if(driveTestMetadata.getServingRsrq()!=null)
                    {
                        if(documentMap.get(DriveTestParser.SERVING_RSRQ)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.SERVING_RSRQ);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getServingRsrq());
                        documentMap.put(DriveTestParser.SERVING_RSRQ, list[0]);
                    }
                    if(driveTestMetadata.getServingRsrp0()!=null)
                    {
                        if(documentMap.get(DriveTestParser.SERVING_RSRP_0)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.SERVING_RSRP_0);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getServingRsrp0());
                        documentMap.put(DriveTestParser.SERVING_RSRP_0, list[0]);
                    }
                    if(driveTestMetadata.getServingRsrp1()!=null)
                    {
                        if(documentMap.get(DriveTestParser.SERVING_RSRP_1)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.SERVING_RSRP_1);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getServingRsrp1());
                        documentMap.put(DriveTestParser.SERVING_RSRP_1, list[0]);
                    }
                    if(driveTestMetadata.getServingRsrq0()!=null)
                    {
                        if(documentMap.get(DriveTestParser.SERVING_RSRQ_0)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.SERVING_RSRQ_0);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getServingRsrq0());
                        documentMap.put(DriveTestParser.SERVING_RSRQ_0, list[0]);
                    }
                    if(driveTestMetadata.getServingRsrq1()!=null)
                    {
                        if(documentMap.get(DriveTestParser.SERVING_RSRQ_1)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.SERVING_RSRQ_1);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getServingRsrq1());
                        documentMap.put(DriveTestParser.SERVING_RSRQ_1, list[0]);
                    }
                    if(driveTestMetadata.getServingSinr0()!=null)
                    {
                        if(documentMap.get(DriveTestParser.SERVING_SINR_0)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.SERVING_SINR_0);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getServingSinr0());
                        documentMap.put(DriveTestParser.SERVING_SINR_0, list[0]);
                    }
                    if(driveTestMetadata.getServingSinr1()!=null)
                    {
                        if(documentMap.get(DriveTestParser.SERVING_SINR_1)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.SERVING_SINR_1);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getServingSinr0());
                        documentMap.put(DriveTestParser.SERVING_SINR_1, list[0]);
                    }
                    if(driveTestMetadata.getNeighborRsrp()!=null)
                    {
                        if(documentMap.get(DriveTestParser.NEIGHBOR_RSRP)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.NEIGHBOR_RSRP);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getNeighborRsrp());
                        documentMap.put(DriveTestParser.NEIGHBOR_RSRP, list[0]);
                    }
                    if(driveTestMetadata.getNeighborRsrq()!=null)
                    {
                        if(documentMap.get(DriveTestParser.NEIGHBOR_RSRQ)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.NEIGHBOR_RSRQ);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getNeighborRsrq());
                        documentMap.put(DriveTestParser.NEIGHBOR_RSRQ, list[0]);
                    }
                    if(driveTestMetadata.getNeighborCellId()!=null)
                    {
                        if(documentMap.get(DriveTestParser.NEIGHBOR_CELL_ID)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.NEIGHBOR_CELL_ID);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getNeighborCellId());
                        documentMap.put(DriveTestParser.NEIGHBOR_CELL_ID, list[0]);
                    }
                    if(driveTestMetadata.getNeighborPci()!=null)
                    {
                        if(documentMap.get(DriveTestParser.NEIGHBOR_PCI)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.NEIGHBOR_PCI);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getNeighborPci());
                        documentMap.put(DriveTestParser.NEIGHBOR_PCI, list[0]);
                    }
                    if(driveTestMetadata.getNeighborEarfcn()!=null)
                    {
                        if(documentMap.get(DriveTestParser.NEIGHBOR_EARFCN)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.NEIGHBOR_EARFCN);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getNeighborEarfcn());
                        documentMap.put(DriveTestParser.NEIGHBOR_EARFCN, list[0]);
                    }
                    if(driveTestMetadata.getNeighborNodeId()!=null)
                    {
                        if(documentMap.get(DriveTestParser.NEIGHBOR_NODE_ID)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.NEIGHBOR_NODE_ID);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getNeighborNodeId());
                        documentMap.put(DriveTestParser.NEIGHBOR_NODE_ID, list[0]);
                    }
                    if(driveTestMetadata.getTotalPdcpDlThroughput()!=null)
                    {
                        if(documentMap.get(DriveTestParser.TOTAL_PDCP_DL_THROUGHPUT)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.TOTAL_PDCP_DL_THROUGHPUT);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getTotalPdcpDlThroughput());
                        documentMap.put(DriveTestParser.TOTAL_PDCP_DL_THROUGHPUT, list[0]);
                    }
                    if(driveTestMetadata.getTotalPdcpUlThroughput()!=null)
                    {
                        if(documentMap.get(DriveTestParser.TOTAL_PDCP_UL_THROUGHPUT)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.TOTAL_PDCP_UL_THROUGHPUT);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getTotalPdcpUlThroughput());
                        documentMap.put(DriveTestParser.TOTAL_PDCP_UL_THROUGHPUT, list[0]);
                    }
                    if(driveTestMetadata.getPmchAverageSinr()!=null)
                    {
                        if(documentMap.get(DriveTestParser.PMCH_AVERAGE_SinR)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.PMCH_AVERAGE_SinR);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getPmchAverageSinr());
                        documentMap.put(DriveTestParser.PMCH_AVERAGE_SinR, list[0]);
                    }
                    if(driveTestMetadata.getPmchBler()!=null)
                    {
                        if(documentMap.get(DriveTestParser.PMCH_BLER)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.PMCH_BLER);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getPmchBler());
                        documentMap.put(DriveTestParser.PMCH_BLER, list[0]);
                    }
                    if(driveTestMetadata.getMacDownlinkThroughput()!=null)
                    {
                        if(documentMap.get(DriveTestParser.MAC_DOWNLINK_THROUGHPUT)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.MAC_DOWNLINK_THROUGHPUT);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getMacDownlinkThroughput());
                        documentMap.put(DriveTestParser.MAC_DOWNLINK_THROUGHPUT, list[0]);
                    }
                    if(driveTestMetadata.getMacUplinkThroughput()!=null)
                    {
                        if(documentMap.get(DriveTestParser.MAC_UPLINK_THROUGHPUT)!=null)
                        {
                            list[0] =(ArrayList)documentMap.get(DriveTestParser.MAC_UPLINK_THROUGHPUT);
                        }
                        else
                        {
                            list[0] = new ArrayList();
                        }
                        list[0].add(driveTestMetadata.getMacUplinkThroughput());
                        documentMap.put(DriveTestParser.MAC_UPLINK_THROUGHPUT, list[0]);
                    }





                });
                Document document=new Document(documentMap);
                docList.add(new InsertOneModel<Document>(document));

            });

            DBConnection.getInstance().getCollectionNameFromMongoClient("drive_test_data").bulkWrite(docList, new BulkWriteOptions().ordered(false));


    }
}
