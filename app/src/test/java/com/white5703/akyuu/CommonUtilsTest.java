package com.white5703.akyuu;

import com.white5703.akyuu.util.CommonUtils;

import org.junit.Test;

public class CommonUtilsTest {

    @Test
    public void genRandomNumber() {
        for(int i = 0; i < 30; i++){
            System.out.println(CommonUtils.genRandomNumber(1,10));
        }


    }
}