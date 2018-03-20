package com.teoco.mongo.services;


import com.csvreader.CsvReader;
import com.teoco.mongo.database.BulkLoaderMapOptimizerFactory;
import com.teoco.mongo.database.IFileTemplateConfig;
import com.teoco.mongo.database.SuperFactory;
import com.teoco.mongo.entity.DriveTestMetadata;
import com.teoco.mongo.entity.FileTemplateConfiguredColumn;
import com.teoco.mongo.rop.RopPeriod;
import com.teoco.mongo.rop.RopPeriodCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by roysha on 11/23/2015.
 * This is a generic parser for all drive test exports
 */
public class DriveTestParser implements Runnable {
    public static final String COMMA = ",";
    public static final String TAB = "\t";

    private static HashMap<DriveTestKey, List<DriveTestMetadata>> hashMapDriveTestData = new HashMap<DriveTestKey, List<DriveTestMetadata>>();

    public static String deviceName = null;
    public List<DriveTestMetadata> metadataList = new ArrayList<DriveTestMetadata>();
    private String source;
    private String destination;
    private String fileName;
    private Integer driveTestId;
    private String databaseType;


    public static final String LATITUDE = "Latitude";
    public static final String DRIVE_TEST_ID = "Drive Test Id";
    public static final String FILE_NAME = "File Name";
    public static final String LONGITUDE = "Longitude";
    public static final String SERVING_PCI = "Serving Pci";
    public static final String PCI = "PCI";
    public static final String CELL_IDENTITY = "Cell Identity";
    public static final String SERVING_EARFCN = "Serving Earfcn";
    public static final String SERVING_RSRP_0 = "Serving Rsrp_0";
    public static final String SERVING_RSRP_1 = "Serving Rsrp_1";
    public static final String SERVING_SINR_0 = "Serving SinR_0";
    public static final String SERVING_SINR_1 = "Serving SinR_1";
    public static final String SERVING_RSRQ_0 = "Serving Rsrq_0";
    public static final String SERVING_RSRQ_1 = "Serving Rsrq_1";
    public static final String SERVING_MEASURED_RSRP_0 = "Serving Measured Rsrp";
    public static final String MCS = "MCS";
    public static final String DL_E_ARFCN = "DL E-ARFCN";
    public static final String SPEED = "Speed";
    public static final String AREA_ID = "Area Id";
    public static final String PMCH_BLER = "Pmch Bler";
    public static final String PMCH_AVERAGE_SinR = "Pmch Average SinR";
    public static final String TA = "TA";
    public static final String NEIGHBOR_PCI = "Neighbor Pci";
    public static final String NEIGHBOR_NODE_ID = "Neighbor Node Id";
    public static final String SERVING_NODE_ID = "Serving Node Id";
    public static final String SERVING_CELL_ID = "Serving Cell Id";
    public static final String NEIGHBOR_CELL_ID = "Neighbor Cell Id";
    public static final String NEIGHBOR_CELL_COUNT = "Neighbor Cell Count";
    public static final String NEIGHBOR_EARFCN = "Neighbor Earfcn";
    public static final String CHANNEL_NO = "Channel";
    public static final String TIMESTAMP = "TimeStamp";
    public static final String SERVING_RSRP = "Serving Rsrp";
    public static final String SERVING_RSRQ = "Serving Rsrq";
    public static final String NEIGHBOR_RSRP = "Neighbor Rsrp";
    public static final String NEIGHBOR_RSRQ = "Neighbor Rsrq";
    public static final String TOTAL_PDCP_UL_THROUGHPUT = "Total Pdcp Uplink Throughput";
    public static final String TOTAL_PDCP_DL_THROUGHPUT = "Total Pdcp Downlink Throughput";
    public static final String TOTAL_RLC_DL_THROUGHPUT = "Total Rlc Downlink Throughput";
    public static final String TOTAL_RLC_UL_THROUGHPUT = "Total Rlc Uplink Throughput";
    public static final String MAC_UPLINK_THROUGHPUT = "Mac Uplink Throughput";
    public static final String MAC_DOWNLINK_THROUGHPUT = "Mac Downlink Throughput";
    public static final String DEVICE_NAME = "Device Name";
    private static final Logger logger = LogManager.getLogger(DriveTestParser.class);
    //    private String fileName;
    private boolean startPersistence = false;
    public static String DEVICE = "Device";
    private static  Map<String, Map<String, FileTemplateConfiguredColumn>> fileTemplateMap=null;






    public static HashMap<DriveTestKey, List<DriveTestMetadata>> getHashMapDriveTestData() {
        return hashMapDriveTestData;
    }



    public DriveTestParser(String source,String destination,String fileName,String databaseType) throws Exception {
        this.source=source;
        this.destination=destination;
        this.fileName=fileName;
        this.databaseType=databaseType;

    }
    public DriveTestParser()
    {

    }

    /**
     * Extracts Rop period (bascially time only) from file name
     * @param fileName
     * @return
     */
    public static RopPeriod getRopPeriodFromFilename(String fileName) {
        //todo : if possible extract utc timestamp from files where they exist, and then convert them to ROP
        String[] split = fileName.split("_");
        RopPeriod x1 = new RopPeriod();
        x1.yyyyMmdd = "20" + split[3] + split[4] + split[5];
        if (split[6].length() == 1)
            split[6] = "0" + split[6];
        if (split[7].length() == 1)
            split[7] = split[7] + "0";
        x1.startHhMm = split[6] + split[7];
        return x1;
    }

    /** Get file per ROP period
     * @param Files
     * @return
     * @throws Exception
     */
    public static RopPeriodCollection getFilesPerRopPeriods(List<File> Files)  {
        RopPeriodCollection rv = new RopPeriodCollection();

        for (File x : Files) {
            RopPeriod y = getRopPeriodFromFilename(x.getName());
            if (y != null) {
                if (!rv.mapRop.containsKey(y)) {
                    rv.orderRop.add(y);
                    ArrayList<File> filesPerRop = new ArrayList<File>();
                    filesPerRop.add(x);
                    rv.mapRop.put(y, filesPerRop);

                } else {
                    rv.mapRop.get(y).add(x);
                }
            }
        }

        return rv;
    }

    /**
     * Parse column data by using a CSVReader. Parse each frive test file and store entire data into metadataList collection
     * @param columnMap Maps the 0 based CVS column index to a column
     * @param fileName
     * @throws Exception
     */
    private void parseDriveTestFile(Map<String, FileTemplateConfiguredColumn> columnMap, String fileName) throws Exception {

        //Create a csv reader for a file
        if (fileName.toUpperCase().endsWith("CSV")) {
            CsvReader csvReader = null;
            try {
                csvReader = new CsvReader(fileName);
                DriveTestMetadata driveTestMetadata = new DriveTestMetadata();
                csvReader.readHeaders();
                DriveTestKey driveTestKey;
                // read the records
                while (csvReader.readRecord()) {
                    driveTestKey = new DriveTestKey();
                    if (!columnMap.isEmpty()) {
                        if (fileName.contains(DEVICE)) {
                            mapDriveTestColumn(driveTestMetadata, columnMap,csvReader);
                        } else {
                            //This will reduce the number of iterative record
                            driveTestKey = checkUniqueIdentity(driveTestKey, csvReader, columnMap);
                            synchronized(hashMapDriveTestData) {
                                if (!hashMapDriveTestData.isEmpty() && !hashMapDriveTestData.containsKey(driveTestKey)) {
                                    driveTestMetadata = new DriveTestMetadata();
                                    metadataList = new ArrayList<DriveTestMetadata>();
                                }
                                if (!hashMapDriveTestData.isEmpty() && hashMapDriveTestData.containsKey(driveTestKey)) {
                                    metadataList = hashMapDriveTestData.get(driveTestKey);
                                }
                                if (hashMapDriveTestData.isEmpty() || !hashMapDriveTestData.containsKey(driveTestKey)) {
                                    driveTestMetadata = mapDriveTestColumn(driveTestMetadata, columnMap, csvReader);
                                    metadataList.add(driveTestMetadata);
                                }

                                hashMapDriveTestData.put(driveTestKey, metadataList);
                            }
                        }
                    }
                }
                System.out.println("File parsed succesfully"+fileName);
            } catch (IOException e) {
                System.out.println("Could not parse file");
            } finally {
                if(csvReader != null) {
                    csvReader.close();
                }
            }


        } else {
            throw new UnsupportedClassVersionError("The format of file is not csv");
        }

    }


    /**
     * Fetch column map details from file_template_config table.
     * This map contains all the column mapping details corresponding to a particular type of csv drive_test file header
     * @return
     */
    private void  fetchMappingDetails(String fileName) throws Exception {
        SuperFactory superFactory=new SuperFactory();
        IFileTemplateConfig iFileTemplateConfig=superFactory.getFileTemplateType(databaseType).getFileTemplateConfig(databaseType);
        if(fileTemplateMap==null) {
            fileTemplateMap = iFileTemplateConfig.fetchFileTemplates();
        }
        Map<String, FileTemplateConfiguredColumn> columnMap= iFileTemplateConfig.fetchFileType(fileName,fileTemplateMap);
        if(columnMap!=null)
            parseDriveTestFile(columnMap,fileName);
    }

     public void optimizeMap(HashMap hashMap){
         SuperFactory superFactory=new SuperFactory();
         BulkLoaderMapOptimizerFactory bulkLoaderMapOptimizerFactory=superFactory.getBulkLoaderType(databaseType,hashMap);
         try {
             HashMap<DriveTestKey, List<DriveTestMetadata>> hashMapDriveTestTempData =bulkLoaderMapOptimizerFactory.getBulkLoader(databaseType).optimizeMap(hashMapDriveTestData,getRopPeriod());
             bulkLoaderMapOptimizerFactory.getBulkLoader(databaseType).writeToDocumentorFile(hashMapDriveTestTempData,driveTestId,getRopPeriod());
             hashMapDriveTestData=new HashMap<DriveTestKey, List<DriveTestMetadata>>();
         } catch (IOException e) {
             e.printStackTrace();
         }

     }


    /**
     * map column values from HashMap and store in drivetestmatadata entity which will be used to persist into database
     *
     * @param driveTestMetadata
     * @param columnMap
     * @param csvReader
     * @return
     * @throws IOException
     */
    private DriveTestMetadata mapDriveTestColumn(DriveTestMetadata driveTestMetadata, Map<String, FileTemplateConfiguredColumn> columnMap,  CsvReader csvReader) throws IOException {

        if (columnMap.containsKey(LATITUDE) && !(csvReader.get(columnMap.get(LONGITUDE).getI()).isEmpty()))
            driveTestMetadata.setLatitude(Double.parseDouble(csvReader.get(columnMap.get(LATITUDE).getI()).trim()));
        if (columnMap.containsKey(LONGITUDE) && !(csvReader.get(columnMap.get(LONGITUDE).getI()).isEmpty()))
            driveTestMetadata.setLongitude(Double.parseDouble(csvReader.get(columnMap.get(LONGITUDE).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRP_0) && !(csvReader.get(columnMap.get(SERVING_RSRP_0).getI()).isEmpty()))
            driveTestMetadata.setServingRsrp0(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRP_0).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRQ_0) && !(csvReader.get(columnMap.get(SERVING_RSRQ_0).getI()).isEmpty()))
            driveTestMetadata.setServingRsrq0(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRQ_0).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRP_1) && !(csvReader.get(columnMap.get(SERVING_RSRP_1).getI()).isEmpty()))
            driveTestMetadata.setServingRsrp1(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRP_1).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRQ_1) && !(csvReader.get(columnMap.get(SERVING_RSRQ_1).getI()).isEmpty()))
            driveTestMetadata.setServingRsrq1(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRQ_1).getI()).trim()));
        if (columnMap.containsKey(SERVING_MEASURED_RSRP_0) && !(csvReader.get(columnMap.get(SERVING_MEASURED_RSRP_0).getI()).isEmpty()))
            driveTestMetadata.setServingMeasuredRsrp(Double.parseDouble(csvReader.get(columnMap.get(SERVING_MEASURED_RSRP_0).getI()).trim()));
        if (columnMap.containsKey(SERVING_SINR_0) && !(csvReader.get(columnMap.get(SERVING_SINR_0).getI()).isEmpty()))
            driveTestMetadata.setServingSinr0(Double.parseDouble(csvReader.get(columnMap.get(SERVING_SINR_0).getI()).trim()));
        if (columnMap.containsKey(SERVING_SINR_1) && !(csvReader.get(columnMap.get(SERVING_SINR_1).getI()).isEmpty()))
            driveTestMetadata.setServingSinr1(Double.parseDouble(csvReader.get(columnMap.get(SERVING_SINR_1).getI()).trim()));
        if (columnMap.containsKey(SERVING_PCI) && !(csvReader.get(columnMap.get(SERVING_PCI).getI()).isEmpty()))
            driveTestMetadata.setServingPci(Long.parseLong(csvReader.get(columnMap.get(SERVING_PCI).getI()).trim()));
        if (columnMap.containsKey(MCS) && !(csvReader.get(columnMap.get(MCS).getI()).isEmpty())){
            if (csvReader.get(columnMap.get(MCS).getI()).trim().contains(",") && "sql".equals(databaseType))
                driveTestMetadata.setMcs(csvReader.get(columnMap.get(MCS).getI()).trim().replace(",", ":"));
            else
                driveTestMetadata.setMcs(csvReader.get(columnMap.get(MCS).getI()).trim());
        }
        if (columnMap.containsKey(DL_E_ARFCN) && !(csvReader.get(columnMap.get(DL_E_ARFCN).getI()).isEmpty()))
            driveTestMetadata.setDlearfcn(Long.parseLong(csvReader.get(columnMap.get(DL_E_ARFCN).getI()).trim()));
        if (columnMap.containsKey(TA) && !(csvReader.get(columnMap.get(TA).getI()).isEmpty()))
            driveTestMetadata.setTimingAdvance(Long.parseLong(csvReader.get(columnMap.get(TA).getI()).trim()));
        if (columnMap.containsKey(PMCH_BLER) && !(csvReader.get(columnMap.get(PMCH_BLER).getI()).isEmpty()))
            driveTestMetadata.setPmchBler(Double.parseDouble(csvReader.get(columnMap.get(PMCH_BLER).getI()).trim()));
        if (columnMap.containsKey(PMCH_AVERAGE_SinR) && !(csvReader.get(columnMap.get(PMCH_AVERAGE_SinR).getI()).isEmpty()))
            driveTestMetadata.setPmchAverageSinr(Double.parseDouble(csvReader.get(columnMap.get(PMCH_AVERAGE_SinR).getI()).trim()));
        if (columnMap.containsKey(SERVING_CELL_ID) && !(csvReader.get(columnMap.get(SERVING_CELL_ID).getI()).isEmpty()))
            driveTestMetadata.setServingCellId(Long.parseLong(csvReader.get(columnMap.get(SERVING_CELL_ID).getI()).trim()));
        if (columnMap.containsKey(SERVING_NODE_ID) && !(csvReader.get(columnMap.get(SERVING_NODE_ID).getI()).isEmpty()))
            driveTestMetadata.setServingNodeId(Long.parseLong(csvReader.get(columnMap.get(SERVING_NODE_ID).getI()).trim()));
        if (columnMap.containsKey(CELL_IDENTITY) && !(csvReader.get(columnMap.get(CELL_IDENTITY).getI()).isEmpty()))
            driveTestMetadata.setCellIdentity(Long.parseLong(csvReader.get(columnMap.get(CELL_IDENTITY).getI()).trim()));
        if (columnMap.containsKey(PCI) && !(csvReader.get(columnMap.get(PCI).getI()).isEmpty()))
            driveTestMetadata.setPhysicalCellId(Long.parseLong(csvReader.get(columnMap.get(PCI).getI()).trim()));
        if (columnMap.containsKey(NEIGHBOR_NODE_ID) && !(csvReader.get(columnMap.get(NEIGHBOR_NODE_ID).getI()).isEmpty()))
            driveTestMetadata.setNeighborNodeId(Long.parseLong(csvReader.get(columnMap.get(NEIGHBOR_NODE_ID).getI()).trim()));
        if (columnMap.containsKey(NEIGHBOR_CELL_ID) && !(csvReader.get(columnMap.get(NEIGHBOR_CELL_ID).getI()).isEmpty())) {
            if (csvReader.get(columnMap.get(NEIGHBOR_CELL_ID).getI()).trim().contains(",") && "sql".equals(databaseType))
                driveTestMetadata.setNeighborCellId(csvReader.get(columnMap.get(NEIGHBOR_CELL_ID).getI()).trim().replace(",", ":"));
            else
                driveTestMetadata.setNeighborCellId(csvReader.get(columnMap.get(NEIGHBOR_CELL_ID).getI()).trim());
        }
        if (columnMap.containsKey(CHANNEL_NO) && !(csvReader.get(columnMap.get(CHANNEL_NO).getI()).isEmpty()))
            driveTestMetadata.setChannelNo(Long.parseLong(csvReader.get(columnMap.get(CHANNEL_NO).getI()).trim()));
        if (columnMap.containsKey(TIMESTAMP) && !(csvReader.get(columnMap.get(TIMESTAMP).getI()).isEmpty()))
            driveTestMetadata.setTimeStamp(Long.parseLong(csvReader.get(columnMap.get(TIMESTAMP).getI()).trim()));
        if (columnMap.containsKey(NEIGHBOR_PCI) && !(csvReader.get(columnMap.get(NEIGHBOR_PCI).getI()).isEmpty())) {
            if (csvReader.get(columnMap.get(NEIGHBOR_PCI).getI()).trim().contains(",") && "sql".equals(databaseType))
                driveTestMetadata.setNeighborCellId(csvReader.get(columnMap.get(NEIGHBOR_PCI).getI()).trim().replace(",", ":"));
            else
                driveTestMetadata.setNeighborPci(csvReader.get(columnMap.get(NEIGHBOR_PCI).getI()).trim());
        }
        if (columnMap.containsKey(NEIGHBOR_EARFCN) && !(csvReader.get(columnMap.get(NEIGHBOR_EARFCN).getI()).isEmpty())) {
            if (csvReader.get(columnMap.get(NEIGHBOR_EARFCN).getI()).trim().contains(",") && "sql".equals(databaseType))
                driveTestMetadata.setNeighborCellId(csvReader.get(columnMap.get(NEIGHBOR_EARFCN).getI()).trim().replace(",", ":"));
            else
                driveTestMetadata.setNeighborEarfcn(csvReader.get(columnMap.get(NEIGHBOR_EARFCN).getI()).trim());
        }
        if (columnMap.containsKey(SERVING_EARFCN) && !(csvReader.get(columnMap.get(SERVING_EARFCN).getI()).isEmpty()))
            driveTestMetadata.setServingEarfcn(Long.parseLong(csvReader.get(columnMap.get(SERVING_EARFCN).getI()).trim()));
        if (columnMap.containsKey(AREA_ID) && !(csvReader.get(columnMap.get(AREA_ID).getI()).isEmpty())){
            if (csvReader.get(columnMap.get(AREA_ID).getI()).trim().contains(",") && "sql".equals(databaseType))
                driveTestMetadata.setAreaId(csvReader.get(columnMap.get(AREA_ID).getI()).trim().replace(",", ":"));
            else
                driveTestMetadata.setAreaId(csvReader.get(columnMap.get(AREA_ID).getI()).trim());
        }
        if (columnMap.containsKey(SPEED) && !(csvReader.get(columnMap.get(SPEED).getI()).isEmpty()))
            driveTestMetadata.setSpeed(Double.parseDouble(csvReader.get(columnMap.get(SPEED).getI()).trim()));
        if (columnMap.containsKey(SERVING_EARFCN) && !(csvReader.get(columnMap.get(SERVING_EARFCN).getI()).isEmpty()))
            driveTestMetadata.setServingEarfcn(Long.parseLong(csvReader.get(columnMap.get(SERVING_EARFCN).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRP) && !(csvReader.get(columnMap.get(SERVING_RSRP).getI()).isEmpty()))
            driveTestMetadata.setServingRsrp(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRP).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRQ) && !(csvReader.get(columnMap.get(SERVING_RSRQ).getI()).isEmpty()))
            driveTestMetadata.setServingRsrq(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRQ).getI()).trim()));
        if (columnMap.containsKey(NEIGHBOR_RSRP) && !(csvReader.get(columnMap.get(NEIGHBOR_RSRP).getI()).isEmpty())) {
            if (csvReader.get(columnMap.get(NEIGHBOR_RSRP).getI()).trim().contains(",")&& "sql".equals(databaseType))
                driveTestMetadata.setNeighborCellId(csvReader.get(columnMap.get(NEIGHBOR_RSRP).getI()).trim().replace(",", ":"));
            else
                driveTestMetadata.setNeighborRsrp(csvReader.get(columnMap.get(NEIGHBOR_RSRP).getI()).trim());
        }
        if (columnMap.containsKey(NEIGHBOR_RSRQ) && !(csvReader.get(columnMap.get(NEIGHBOR_RSRQ).getI()).isEmpty())) {
            if (csvReader.get(columnMap.get(NEIGHBOR_RSRQ).getI()).trim().contains(",") && "sql".equals(databaseType))
                driveTestMetadata.setNeighborCellId(csvReader.get(columnMap.get(NEIGHBOR_RSRQ).getI()).trim().replace(",", ":"));
            else
                driveTestMetadata.setNeighborRsrq(csvReader.get(columnMap.get(NEIGHBOR_RSRQ).getI()).trim());
        }
        if (columnMap.containsKey(TOTAL_PDCP_DL_THROUGHPUT) && !(csvReader.get(columnMap.get(TOTAL_PDCP_DL_THROUGHPUT).getI()).isEmpty()))
            driveTestMetadata.setTotalPdcpDlThroughput(Double.parseDouble(csvReader.get(columnMap.get(TOTAL_PDCP_DL_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(TOTAL_RLC_DL_THROUGHPUT) && !(csvReader.get(columnMap.get(TOTAL_RLC_DL_THROUGHPUT).getI()).isEmpty()))
            driveTestMetadata.setTotalRlcDlThroughput(Double.parseDouble(csvReader.get(columnMap.get(TOTAL_RLC_DL_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(TOTAL_PDCP_UL_THROUGHPUT) && !(csvReader.get(columnMap.get(TOTAL_PDCP_UL_THROUGHPUT).getI()).isEmpty()))
            driveTestMetadata.setTotalPdcpUlThroughput(Double.parseDouble(csvReader.get(columnMap.get(TOTAL_PDCP_UL_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(TOTAL_RLC_UL_THROUGHPUT) && !(csvReader.get(columnMap.get(TOTAL_RLC_UL_THROUGHPUT).getI()).isEmpty()))
            driveTestMetadata.setTotalRlcUlThroughput(Double.parseDouble(csvReader.get(columnMap.get(TOTAL_RLC_UL_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(MAC_DOWNLINK_THROUGHPUT) && !(csvReader.get(columnMap.get(MAC_DOWNLINK_THROUGHPUT).getI()).isEmpty()))
            driveTestMetadata.setMacDownlinkThroughput(Double.parseDouble(csvReader.get(columnMap.get(MAC_DOWNLINK_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(MAC_UPLINK_THROUGHPUT) && !(csvReader.get(columnMap.get(MAC_UPLINK_THROUGHPUT).getI()).isEmpty()))
            driveTestMetadata.setMacUplinkThroughput(Double.parseDouble(csvReader.get(columnMap.get(MAC_UPLINK_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(NEIGHBOR_CELL_COUNT) && !(csvReader.get(columnMap.get(NEIGHBOR_CELL_COUNT).getI()).isEmpty()))
            driveTestMetadata.setNeighborCellCount(Long.parseLong(csvReader.get(columnMap.get(NEIGHBOR_CELL_COUNT).getI()).trim()));
        if (columnMap.containsKey(DEVICE_NAME) && !(csvReader.get(columnMap.get(DEVICE_NAME).getI()).isEmpty())) {
            driveTestMetadata.setDeviceName(csvReader.get(columnMap.get(DEVICE_NAME).getI()).trim());
            deviceName = driveTestMetadata.getDeviceName();
        }
        Random rand = new Random();
        String id=String.valueOf(rand.nextLong());
        this.driveTestId=Integer.parseInt(id.substring(1,6));
        driveTestMetadata.setDriveTestId(this.driveTestId);
        driveTestMetadata.setFileName(this.fileName);
        driveTestMetadata.setTimeStamp(getRopPeriod().getRopStartTimeInMs());
        //System.out.println(driveTestMetadata.getTimeStamp());
        driveTestMetadata.setCreatedDate(System.currentTimeMillis());
        String[] split = fileName.split("_");
        driveTestMetadata.setImei(Long.parseLong(split[8]));
        return driveTestMetadata;
    }

    public RopPeriod getRopPeriod() {
        return getRopPeriodFromFilename(this.fileName);
    }



    /**
     * Check uniqueness of each record
     *
     * @param driveTestKey
     * @param csvReader
     * @param columnMap
     * @return
     * @throws IOException
     */
    private DriveTestKey checkUniqueIdentity(DriveTestKey driveTestKey, CsvReader csvReader, Map<String, FileTemplateConfiguredColumn> columnMap) throws IOException {
        driveTestKey.setFileName(fileName);
        if (columnMap.containsKey(LATITUDE) && !(csvReader.get(columnMap.get(LONGITUDE).getI()).isEmpty()))
            driveTestKey.setLatitude(Double.parseDouble(csvReader.get(columnMap.get(LATITUDE).getI()).trim()));
        if (columnMap.containsKey(LONGITUDE) && !(csvReader.get(columnMap.get(LONGITUDE).getI()).isEmpty()))
            driveTestKey.setLongitude(Double.parseDouble(csvReader.get(columnMap.get(LONGITUDE).getI()).trim()));
        driveTestKey.setTime(getRopPeriod().getRopStartTimeInMs());
        if (columnMap.containsKey(CELL_IDENTITY) && !(csvReader.get(columnMap.get(CELL_IDENTITY).getI()).isEmpty()))
            driveTestKey.setCellIdentity(Long.parseLong(csvReader.get(columnMap.get(CELL_IDENTITY).getI()).trim()));
        if (columnMap.containsKey(PCI) && !(csvReader.get(columnMap.get(PCI).getI()).isEmpty()))
            driveTestKey.setPci(Long.parseLong(csvReader.get(columnMap.get(PCI).getI()).trim()));
        if (columnMap.containsKey(DL_E_ARFCN) && !(csvReader.get(columnMap.get(DL_E_ARFCN).getI()).isEmpty()))
            driveTestKey.setDlEarfcn(Long.parseLong(csvReader.get(columnMap.get(DL_E_ARFCN).getI()).trim()));
        if (columnMap.containsKey(TOTAL_RLC_DL_THROUGHPUT) && !(csvReader.get(columnMap.get(TOTAL_RLC_DL_THROUGHPUT).getI()).isEmpty()))
            driveTestKey.setTotalRlcDownlinkThroughput(Double.parseDouble(csvReader.get(columnMap.get(TOTAL_RLC_DL_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(TOTAL_RLC_UL_THROUGHPUT) && !(csvReader.get(columnMap.get(TOTAL_RLC_UL_THROUGHPUT).getI()).isEmpty()))
            driveTestKey.setTotalRlcUplinkThroughput(Double.parseDouble(csvReader.get(columnMap.get(TOTAL_RLC_UL_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRP_0) && !(csvReader.get(columnMap.get(SERVING_RSRP_0).getI()).isEmpty()))
            driveTestKey.setServingRsrp0(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRP_0).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRQ_0) && !(csvReader.get(columnMap.get(SERVING_RSRQ_0).getI()).isEmpty()))
            driveTestKey.setServingRsrq0(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRQ_0).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRP_1) && !(csvReader.get(columnMap.get(SERVING_RSRP_1).getI()).isEmpty()))
            driveTestKey.setServingRsrp1(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRP_1).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRQ_1) && !(csvReader.get(columnMap.get(SERVING_RSRQ_1).getI()).isEmpty()))
            driveTestKey.setServingRsrq1(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRQ_1).getI()).trim()));
        if (columnMap.containsKey(SERVING_MEASURED_RSRP_0) && !(csvReader.get(columnMap.get(SERVING_MEASURED_RSRP_0).getI()).isEmpty()))
            driveTestKey.setServingMeasuredRsrp(Double.parseDouble(csvReader.get(columnMap.get(SERVING_MEASURED_RSRP_0).getI()).trim()));
        if (columnMap.containsKey(SERVING_SINR_0) && !(csvReader.get(columnMap.get(SERVING_SINR_0).getI()).isEmpty()))
            driveTestKey.setServingSinr0(Double.parseDouble(csvReader.get(columnMap.get(SERVING_SINR_0).getI()).trim()));
        if (columnMap.containsKey(SERVING_SINR_1) && !(csvReader.get(columnMap.get(SERVING_SINR_1).getI()).isEmpty()))
            driveTestKey.setServingSinr1(Double.parseDouble(csvReader.get(columnMap.get(SERVING_SINR_1).getI()).trim()));
        if (columnMap.containsKey(SERVING_PCI) && !(csvReader.get(columnMap.get(SERVING_PCI).getI()).isEmpty()))
            driveTestKey.setServingPci(Long.parseLong(csvReader.get(columnMap.get(SERVING_PCI).getI()).trim()));
        if (columnMap.containsKey(PMCH_BLER) && !(csvReader.get(columnMap.get(PMCH_BLER).getI()).isEmpty()))
            driveTestKey.setPmchBler(Double.parseDouble(csvReader.get(columnMap.get(PMCH_BLER).getI()).trim()));
        if (columnMap.containsKey(PMCH_AVERAGE_SinR) && !(csvReader.get(columnMap.get(PMCH_AVERAGE_SinR).getI()).isEmpty()))
            driveTestKey.setPmchAverageSinr(Double.parseDouble(csvReader.get(columnMap.get(PMCH_AVERAGE_SinR).getI()).trim()));
        if (columnMap.containsKey(NEIGHBOR_NODE_ID) && !(csvReader.get(columnMap.get(NEIGHBOR_NODE_ID).getI()).isEmpty()))
            driveTestKey.setNeighborNodeId(Long.parseLong(csvReader.get(columnMap.get(NEIGHBOR_NODE_ID).getI()).trim()));
        if (columnMap.containsKey(SERVING_EARFCN) && !(csvReader.get(columnMap.get(SERVING_EARFCN).getI()).isEmpty()))
            driveTestKey.setServingEarfcn(Long.parseLong(csvReader.get(columnMap.get(SERVING_EARFCN).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRP) && !(csvReader.get(columnMap.get(SERVING_RSRP).getI()).isEmpty()))
            driveTestKey.setServingRsrp(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRP).getI()).trim()));
        if (columnMap.containsKey(SERVING_RSRQ) && !(csvReader.get(columnMap.get(SERVING_RSRQ).getI()).isEmpty()))
            driveTestKey.setServingRsrq(Double.parseDouble(csvReader.get(columnMap.get(SERVING_RSRQ).getI()).trim()));
        if (columnMap.containsKey(TOTAL_PDCP_DL_THROUGHPUT) && !(csvReader.get(columnMap.get(TOTAL_PDCP_DL_THROUGHPUT).getI()).isEmpty()))
            driveTestKey.setTotalPdcpDlThroughput(Double.parseDouble(csvReader.get(columnMap.get(TOTAL_PDCP_DL_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(TOTAL_PDCP_UL_THROUGHPUT) && !(csvReader.get(columnMap.get(TOTAL_PDCP_UL_THROUGHPUT).getI()).isEmpty()))
            driveTestKey.setTotalPdcpUlThroughput(Double.parseDouble(csvReader.get(columnMap.get(TOTAL_PDCP_UL_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(MAC_DOWNLINK_THROUGHPUT) && !(csvReader.get(columnMap.get(MAC_DOWNLINK_THROUGHPUT).getI()).isEmpty()))
            driveTestKey.setMacDownlinkThroughput(Double.parseDouble(csvReader.get(columnMap.get(MAC_DOWNLINK_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(MAC_UPLINK_THROUGHPUT) && !(csvReader.get(columnMap.get(MAC_UPLINK_THROUGHPUT).getI()).isEmpty()))
            driveTestKey.setMacUplinkThroughput(Double.parseDouble(csvReader.get(columnMap.get(MAC_UPLINK_THROUGHPUT).getI()).trim()));
        if (columnMap.containsKey(NEIGHBOR_EARFCN) && !(csvReader.get(columnMap.get(NEIGHBOR_EARFCN).getI()).isEmpty()))
            driveTestKey.setNeighborEarfcn(csvReader.get(columnMap.get(NEIGHBOR_EARFCN).getI()).trim());
        if (columnMap.containsKey(NEIGHBOR_RSRP) && !(csvReader.get(columnMap.get(NEIGHBOR_RSRP).getI()).isEmpty()))
            driveTestKey.setNeighborRsrp(csvReader.get(columnMap.get(NEIGHBOR_RSRP).getI()).trim());
        if (columnMap.containsKey(NEIGHBOR_RSRQ) && !(csvReader.get(columnMap.get(NEIGHBOR_RSRQ).getI()).isEmpty()))
            driveTestKey.setNeighborRsrq(csvReader.get(columnMap.get(NEIGHBOR_RSRQ).getI()).trim());
        if (columnMap.containsKey(NEIGHBOR_CELL_ID) && !(csvReader.get(columnMap.get(NEIGHBOR_CELL_ID).getI()).isEmpty()))
            driveTestKey.setNeighborCell(csvReader.get(columnMap.get(NEIGHBOR_CELL_ID).getI()).trim());

        return driveTestKey;
    }



    @Override
    public void run() {
        try {
            fetchMappingDetails(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
