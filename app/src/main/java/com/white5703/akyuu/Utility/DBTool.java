package com.white5703.akyuu.Utility;

import com.white5703.akyuu.Dao.Count;
import com.white5703.akyuu.Dao.CountDao;
import com.white5703.akyuu.Dao.Note;
import com.white5703.akyuu.Dao.NoteDao;
import com.white5703.akyuu.AkyuuApplication;

import java.util.List;

public class DBTool {

    private static NoteDao mNoteDao = AkyuuApplication.getInstance().getDaoSession().getNoteDao();
    private static CountDao mCountDao = AkyuuApplication.getInstance().getDaoSession().getCountDao();

    public static void insertNote(String text,int priority){
        Note note = new Note();
        note.setText(text);
        note.setPriority(priority);
        mNoteDao.insert(note);
        updateCount();
    }

    public static void deleteNote(long id){
        mNoteDao.deleteByKey(id);
    }

    public static void updateCount(){
        mCountDao.deleteAll();
        long nowCount = mNoteDao.count();
        long nowPrioritySum = getPrioritySum();
        Count count = new Count();
        count.setItemCount(nowCount);
        count.setPrioritySum(nowPrioritySum);
        mCountDao.insert(count);
    }

    public static void increasePriority(long id){
        Note note = mNoteDao.loadByRowId(id);
        note.setPriority(note.getPriority() + 1);
        mNoteDao.save(note);
    }

    public static void decreasePriority(long id){
        Note note = mNoteDao.loadByRowId(id);
        note.setPriority(note.getPriority() - 1);
        mNoteDao.save(note);
    }

    public static long getPrioritySum(){
        List<Note> listnote = getNoteList();
        long sum = 0;
        long count = listnote.size();
        for(int i = 0; i < count; i++){
            sum += listnote.get(i).getPriority();
        }
        return sum;
    }

    public static long getNoteCount(){
        return mNoteDao.count();
    }

    public static List<Note> getNoteList(){
        return mNoteDao.loadAll();
    }



    public static void clearTableNote(){
        mNoteDao.deleteAll();
    }


}
