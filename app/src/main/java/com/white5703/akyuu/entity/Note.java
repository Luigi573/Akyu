package com.white5703.akyuu.entity;

import com.white5703.akyuu.dao.DaoSession;
import com.white5703.akyuu.dao.NoteDao;
import com.white5703.akyuu.dao.converter.StringListConverter;
import com.white5703.akyuu.util.CommonUtils;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity(active = true)
public class Note {
    @Id(autoincrement = true)
    private Long id;

    private String brief;

    private String detail;

    private String tag;

    @NotNull
    private int priority;

    private Date updatetime;

    private String reference;

    private int briefgravity;

    private int brieffontsize;

    private int detailgravity;

    private int detailfontsize;

    @Convert(columnType = String.class, converter = StringListConverter.class)
    private List<String> latexs;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 363862535)
    private transient NoteDao myDao;

    @Generated(hash = 1964308962)
    public Note(Long id, String brief, String detail, String tag, int priority,
        Date updatetime, String reference, int briefgravity, int brieffontsize,
        int detailgravity, int detailfontsize, List<String> latexs) {
        this.id = id;
        this.brief = brief;
        this.detail = detail;
        this.tag = tag;
        this.priority = priority;
        this.updatetime = updatetime;
        this.reference = reference;
        this.briefgravity = briefgravity;
        this.brieffontsize = brieffontsize;
        this.detailgravity = detailgravity;
        this.detailfontsize = detailfontsize;
        this.latexs = latexs;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 799086675)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNoteDao() : null;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getBrief() {
        return this.brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getBrieffontsize() {
        return this.brieffontsize;
    }

    public void setBrieffontsize(int brieffontsize) {
        this.brieffontsize = brieffontsize;
    }

    public int getDetailfontsize() {
        return this.detailfontsize;
    }

    public void setDetailfontsize(int detailfontsize) {
        this.detailfontsize = detailfontsize;
    }

    public List<String> getLatexs() {
        return this.latexs;
    }

    public void setLatexs(List<String> latexs) {
        this.latexs = latexs;
    }

    public int getBriefgravity() {
        return this.briefgravity;
    }

    public void setBriefgravity(int briefgravity) {
        this.briefgravity = briefgravity;
    }

    public int getDetailgravity() {
        return this.detailgravity;
    }

    public void setDetailgravity(int detailgravity) {
        this.detailgravity = detailgravity;
    }

    public boolean equal(Note note) {
        if (!brief.equals(note.brief)) return false;
        if (!(brieffontsize == note.brieffontsize)) return false;
        if (!(briefgravity == note.briefgravity)) return false;
        if (!detail.equals(note.detail)) return false;
        if (!(detailfontsize == note.detailfontsize)) return false;
        if (!(detailgravity == note.detailgravity)) return false;
        if (!tag.equals(note.tag)) return false;
        if (!reference.equals(note.reference)) return false;
        if (!(priority == note.priority)) return false;
        return CommonUtils.equalsList(latexs, note.latexs);
    }

    public Note getTempCopy() {
        Note temp = new Note();
        temp.setBrief(brief);
        temp.setBrieffontsize(brieffontsize);
        temp.setBriefgravity(briefgravity);
        temp.setDetail(detail);
        temp.setDetailfontsize(detailfontsize);
        temp.setDetailgravity(detailgravity);
        temp.setTag(tag);
        temp.setPriority(priority);
        temp.setReference(reference);
        temp.setLatexs(latexs);
        return temp;
    }
}
