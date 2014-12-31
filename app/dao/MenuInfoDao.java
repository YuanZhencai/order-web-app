package dao;

import models.MenuInfo;

import java.util.List;

/**
 * Created by guxuelong on 2014/12/26.
 */
public interface MenuInfoDao {
    public Long doInsert(MenuInfo menuInfo);

    public boolean doDelete(MenuInfo menuInfo);

    public boolean doUpdate(MenuInfo menuInfo);

    public List<MenuInfo> findAll();

    public List<MenuInfo> findSubset(int start, int end);

    public MenuInfo findById(Long id);

    public int num();

    public boolean isExist(MenuInfo menuInfo);
}
