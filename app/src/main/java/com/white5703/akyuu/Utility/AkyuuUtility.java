package com.white5703.akyuu.Utility;

import com.white5703.akyuu.Dao.Note;

import java.util.List;
import java.util.Random;

public class AkyuuUtility {
    public static long genRandomNumber(long min,long max){
        Random random = new Random();
        long num = random.nextInt((int)max) % (max-min+1) + min;
        return num;
    }

    public static int getMiniumPriority(List<Note> list){
        int cnt = list.size();
        int res = 9;
        for(int i = 0; i < cnt; i++){
            if(list.get(i).getPriority() < res){
                res = list.get(i).getPriority();
            }
        }
        return res;
    }
}
