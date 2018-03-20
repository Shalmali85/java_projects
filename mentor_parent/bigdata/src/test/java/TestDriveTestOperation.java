import com.teoco.mongo.database.DBConnection;
import com.teoco.mongo.database.DriveTestDB;
import com.teoco.mongo.services.DriveTestOperation;
import org.junit.Test;

/**
 * Created by roysha on 2/24/2016.
 */
public class TestDriveTestOperation {
    @Test
    public  void testMain() throws Exception {
        DriveTestOperation.main(new String[]{
                "-Dsource"+ "=/home/shalmali/china-mobile-mro-data/input",
                "-Doutput"+ "=/home/shalmali/china-mobile-mro-data/output",
                "-DthreadPoolSize"+ "=4",
                "-DmaxAllowedTime"+ "=4",
                "-DdatabaseType=noSql",
               "-DoptimizerClass=com.teoco.mongo.database.NoSqlBulkLoaderOptimizer",
               "-DmongoConnectionUsed=Mongo"

        });
    }
@Test
    public void testFetchDriveTestMetadata()
    {
        DBConnection.getInstance().getConnection("Mongo");
        DriveTestDB driveTestDB=new DriveTestDB();
        //driveTestDB.fetchDriveTestMetadata();
        driveTestDB.fetchDriveTest();
    }
}
