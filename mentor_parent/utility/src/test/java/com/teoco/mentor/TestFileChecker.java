package com.teoco.mentor;

import com.teoco.mentor.*;
import org.junit.Test;

/**
 * Created by roysha on 5/17/2016.
 */
public class TestFileChecker {
    @Test
    public  void tesFileWatchertMain() throws Exception {
        FileWatcher.main(new String[]{
                "-Dsource_folders"+ "=C:\\20160710,C:\\20160711",
                "-Dtime"+ "=15",

        });
    }
}
