package com.white5703.akyuu;

import com.white5703.akyuu.Utility.AkyuuUtility;

import org.junit.Test;

public class AkyuuUtilityTest {

    @Test
    public void genRandomNumber() {
        for(int i = 0; i < 30; i++){
            System.out.println(AkyuuUtility.genRandomNumber(1,10));
        }


    }
}