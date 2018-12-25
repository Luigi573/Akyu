package com.white5703.akyuu;

import com.white5703.akyuu.Dao.Note;
import com.white5703.akyuu.Utility.DBTool;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBToolTest {
    Note mNOTE;
    @Before
    public void setUp(){
        DBTool.clearTableNote();
        DBTool.insertNote("Test999",9);
        DBTool.insertNote("Test111",1);


    }



    @Test
    public void insertTest(){
        System.out.println(DBTool.getNoteCount());
        assertSame(1L,DBTool.getNoteCount());
    }
    @Test
    public void getPrioritySum(){
        long prioritysum = DBTool.getPrioritySum();
        System.out.println(prioritysum);
        assertSame(5L,prioritysum);

    }
}