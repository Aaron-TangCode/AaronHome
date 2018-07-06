package com.example.reverseproxy.test;

import com.trs.idm.data.access.DBConfiguration;
import com.trs.idm.data.access.IDBAccessor;
import com.trs.idm.data.access.rdb.Hb3DataAccessor;
import com.trs.idm.exception.IdMException;
import com.trs.idm.model.IDSContext;

public class DBAccessorContext {
    private IDBAccessor dbAccessor;
    private DBConfiguration dbAccessorConfig;

    public DBAccessorContext(DBConfiguration dbAccessorConfig) {
        this.dbAccessorConfig = dbAccessorConfig;
    }

    public void start(IDSContext idsCtx) throws IdMException {
        this.dbAccessor = new Hb3DataAccessor();
        this.dbAccessor.start(this.dbAccessorConfig);
    }

    public IDBAccessor getDbAccessor() {
        return this.dbAccessor;
    }

    public DBConfiguration getDbAccessorConfig() {
        return this.dbAccessorConfig;
    }
}
