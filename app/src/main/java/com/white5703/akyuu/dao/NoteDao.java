package com.white5703.akyuu.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.white5703.akyuu.dao.converter.StringListConverter;
import com.white5703.akyuu.entity.Note;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NOTE".
*/
public class NoteDao extends AbstractDao<Note, Long> {

    public static final String TABLENAME = "NOTE";

    private final StringListConverter latexsConverter = new StringListConverter();
    private DaoSession daoSession;

    public NoteDao(DaoConfig config) {
        super(config);
    }

    public NoteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }
    
    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOTE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
            "\"BRIEF\" TEXT," + // 1: brief
            "\"DETAIL\" TEXT," + // 2: detail
                "\"TAG\" TEXT," + // 3: tag
            "\"PRIORITY\" INTEGER NOT NULL ," + // 4: priority
            "\"UPDATETIME\" INTEGER," + // 5: updatetime
            "\"REFERENCE\" TEXT," + // 6: reference
            "\"BRIEFGRAVITY\" INTEGER NOT NULL ," + // 7: briefgravity
            "\"BRIEFFONTSIZE\" INTEGER NOT NULL ," + // 8: brieffontsize
            "\"DETAILGRAVITY\" INTEGER NOT NULL ," + // 9: detailgravity
            "\"DETAILFONTSIZE\" INTEGER NOT NULL ," + // 10: detailfontsize
            "\"LATEXS\" TEXT);"); // 11: latexs
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOTE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Note entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String brief = entity.getBrief();
        if (brief != null) {
            stmt.bindString(2, brief);
        }

        String detail = entity.getDetail();
        if (detail != null) {
            stmt.bindString(3, detail);
        }

        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(4, tag);
        }
        stmt.bindLong(5, entity.getPriority());

        java.util.Date updatetime = entity.getUpdatetime();
        if (updatetime != null) {
            stmt.bindLong(6, updatetime.getTime());
        }

        String reference = entity.getReference();
        if (reference != null) {
            stmt.bindString(7, reference);
        }
        stmt.bindLong(8, entity.getBriefgravity());
        stmt.bindLong(9, entity.getBrieffontsize());
        stmt.bindLong(10, entity.getDetailgravity());
        stmt.bindLong(11, entity.getDetailfontsize());

        List latexs = entity.getLatexs();
        if (latexs != null) {
            stmt.bindString(12, latexsConverter.convertToDatabaseValue(latexs));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Note entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String brief = entity.getBrief();
        if (brief != null) {
            stmt.bindString(2, brief);
        }

        String detail = entity.getDetail();
        if (detail != null) {
            stmt.bindString(3, detail);
        }

        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(4, tag);
        }
        stmt.bindLong(5, entity.getPriority());

        java.util.Date updatetime = entity.getUpdatetime();
        if (updatetime != null) {
            stmt.bindLong(6, updatetime.getTime());
        }

        String reference = entity.getReference();
        if (reference != null) {
            stmt.bindString(7, reference);
        }
        stmt.bindLong(8, entity.getBriefgravity());
        stmt.bindLong(9, entity.getBrieffontsize());
        stmt.bindLong(10, entity.getDetailgravity());
        stmt.bindLong(11, entity.getDetailfontsize());

        List latexs = entity.getLatexs();
        if (latexs != null) {
            stmt.bindString(12, latexsConverter.convertToDatabaseValue(latexs));
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    protected final void attachEntity(Note entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Note readEntity(Cursor cursor, int offset) {
        Note entity = new Note( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // brief
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // detail
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // tag
            cursor.getInt(offset + 4), // priority
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)),
            // updatetime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // reference
            cursor.getInt(offset + 7), // briefgravity
            cursor.getInt(offset + 8), // brieffontsize
            cursor.getInt(offset + 9), // detailgravity
            cursor.getInt(offset + 10), // detailfontsize
            cursor.isNull(offset + 11) ? null
                : latexsConverter.convertToEntityProperty(cursor.getString(offset + 11)) // latexs
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, Note entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBrief(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDetail(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTag(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPriority(cursor.getInt(offset + 4));
        entity.setUpdatetime(
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
        entity.setReference(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setBriefgravity(cursor.getInt(offset + 7));
        entity.setBrieffontsize(cursor.getInt(offset + 8));
        entity.setDetailgravity(cursor.getInt(offset + 9));
        entity.setDetailfontsize(cursor.getInt(offset + 10));
        entity.setLatexs(cursor.isNull(offset + 11) ? null
            : latexsConverter.convertToEntityProperty(cursor.getString(offset + 11)));
    }
     
    /**
     * Properties of entity Note.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Brief = new Property(1, String.class, "brief", false, "BRIEF");
        public final static Property Detail =
            new Property(2, String.class, "detail", false, "DETAIL");
        public final static Property Tag = new Property(3, String.class, "tag", false, "TAG");
        public final static Property Priority =
            new Property(4, int.class, "priority", false, "PRIORITY");
        public final static Property Updatetime =
            new Property(5, java.util.Date.class, "updatetime", false, "UPDATETIME");
        public final static Property Reference =
            new Property(6, String.class, "reference", false, "REFERENCE");
        public final static Property Briefgravity =
            new Property(7, int.class, "briefgravity", false, "BRIEFGRAVITY");
        public final static Property Brieffontsize =
            new Property(8, int.class, "brieffontsize", false, "BRIEFFONTSIZE");
        public final static Property Detailgravity =
            new Property(9, int.class, "detailgravity", false, "DETAILGRAVITY");
        public final static Property Detailfontsize =
            new Property(10, int.class, "detailfontsize", false, "DETAILFONTSIZE");
        public final static Property Latexs =
            new Property(11, String.class, "latexs", false, "LATEXS");
    }
    
    @Override
    protected final Long updateKeyAfterInsert(Note entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Note entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Note entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
