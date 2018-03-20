package com.teoco.rnto.util;

import org.junit.Test;

/**
 * Created by roysha on 2/4/2016.
 */
public class TestProcessFileOperation  {

@Test
    public  void testMain() throws Exception {
    ProcessFileOperation.main(new String[]{
            "-D"+ "=C:\\china-mobile-mro-data\\input",
            "-DfileType"+ "=MRO",
            "-Doutput"+ "=C:\\china-mobile-mro-data\\output",
            "-DtimeFrom"+ "=1600",
            "-DtimeTo"+ "=1645",
            "-DthreadPoolSize"+ "=4",
            "-DmaxAllowedTime"+ "=4"

    });
}
}
