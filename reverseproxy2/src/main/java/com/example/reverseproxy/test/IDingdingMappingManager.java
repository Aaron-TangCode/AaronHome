package com.example.reverseproxy.test;

import com.trs.idm.data.access.PagedList;
import com.trs.idm.data.bo.CoApp;
import com.trs.idm.data.bo.WeixinMapping;
import com.trs.idm.data.service.SearchFilter;
import com.trs.idm.exception.IdMException;
import com.trs.idm.util.RequestContext;

public interface IDingdingMappingManager {
    void bind(String var1, CoApp var2, String var3) throws IdMException;

    void bind(String var1, String var2, String var3) throws IdMException;

    void bind(RequestContext var1, String var2, String var3, String var4, String var5) throws IdMException;

    void update(DingdingMapping var1) throws IdMException;

    void record(String var1, String var2, CoApp var3, String var4) throws IdMException;

    void record(RequestContext var1, String var2, String var3, String var4, String var5, String var6, String var7) throws IdMException;

    boolean isBind(String var1, String var2) throws IdMException;

    DingdingMapping getBinding(String var1, String var2) throws IdMException;

    void delete(String var1, String var2) throws IdMException;

    PagedList pl(SearchFilter var1) throws IdMException;
}
