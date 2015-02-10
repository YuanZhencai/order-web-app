package dao.impl;

import common.dao.EntityBaseDao;
import dao.MenuInfoDao;
import models.MenuInfo;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by guxuelong on 2014/12/26.
 */
public class MenuInfoDaoImpl extends EntityBaseDao implements MenuInfoDao {
    /**
     *insert into database
     */
    @Override
    public  Long doInsert(MenuInfo menuInfo){
        MenuInfo result = create(menuInfo);

        return result.getId();
    }

    /**
     *delete from database
     */
    @Override
    public  boolean doDelete(MenuInfo menuInfo){
        update(menuInfo);
        return true;
    }

    /**
     *update in database
     */
    @Override
    public  boolean doUpdate( MenuInfo menuInfo) {
        MenuInfo menuInfoOrg = findById(menuInfo.getId());
        if ( menuInfoOrg==null){
            return false;
        }
        menuInfo.setDelFlg(menuInfoOrg.getDelFlg());
        menuInfo.setCreateTime(menuInfoOrg.getCreateTime());
        menuInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        update(menuInfo);
        return true;
    }

    @Override
    public List<MenuInfo> findAll(){
        return findAll(MenuInfo.class);
    }

    /**
     * in database
     * fetch a set of bank from database
     * @param end
     * @param start
     * @return menuInfo<list>
     */
    @Override
    public  List<MenuInfo> findSubset ( int start,int end ) {
        return findAll(MenuInfo.class).subList(start, end);
    }

    /**
     * in database
     * select a piece of bank info via ID
     * @param id
     * @return menuInfo<menuInfo>
     */
    @Override
    public MenuInfo findById ( Long id ) {
        List<MenuInfo> MenuInfo = findBy(MenuInfo.class, "id", id);
        if ( MenuInfo.isEmpty() ) {
            return null;
        }else{
            return MenuInfo.get(0);
        }
    }

    /**
     * count the num of MenuInfo
     * @return <int>
     */
    @Override
    public int  num() {
        return findAll(MenuInfo.class).size();
    }

    /**
     * in database
     * query to see it if exist
     * @param menuInfo <menuInfo>
     * @return <boolean>
     */
    @Override
    public  boolean isExist( MenuInfo menuInfo) {
        List<MenuInfo> userList = (List<MenuInfo>) findBy(MenuInfo.class,"foodId",menuInfo.getFoodId());
        if(userList!=null && !userList.isEmpty() && "N".equals(userList.get(0).getDelFlg())){
            return true;
        }
        return false;
    }
}
