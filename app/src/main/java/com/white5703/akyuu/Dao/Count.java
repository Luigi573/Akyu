package com.white5703.akyuu.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.white5703.akyuu.Dao.DaoSession;
import com.white5703.akyuu.Dao.CountDao;

@Entity(active = true)
public class Count {
    private long itemCount;
    private long prioritySum;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1996494525)
    private transient CountDao myDao;
    @Generated(hash = 96191550)
    public Count(long itemCount, long prioritySum) {
        this.itemCount = itemCount;
        this.prioritySum = prioritySum;
    }
    @Generated(hash = 858450839)
    public Count() {
    }
    public long getItemCount() {
        return this.itemCount;
    }
    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }
    public long getPrioritySum() {
        return this.prioritySum;
    }
    public void setPrioritySum(long prioritySum) {
        this.prioritySum = prioritySum;
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
    @Generated(hash = 2092991082)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCountDao() : null;
    }
}
