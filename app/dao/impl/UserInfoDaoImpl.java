package dao.impl;

import dao.EntityBaseDao;
import dao.UserInfoDao;
import models.UserInfos;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by guxuelong on 2014/12/26.
 */
public class UserInfoDaoImpl extends EntityBaseDao implements UserInfoDao{
    /**
     *insert into database
     */
    @Override
    public  Long  doInsert(UserInfos userInfo){
        UserInfos result =  create(userInfo);
        return result.getId();
    }

    /**
     *delete from database
     */
    @Override
    public  boolean doDelete(UserInfos userInfo){
        update(userInfo);
        return true;
    }

    /**
     *update in database
     */
    @Override
    public  boolean doUpdate( UserInfos userInfo) {
        UserInfos userInfoOrg = findById(userInfo.getId());
        if ( userInfoOrg==null){
            return false;
        }
        userInfo.setDelFlg(userInfoOrg.getDelFlg());
        userInfo.setCreateTime(userInfoOrg.getCreateTime());
        userInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        update(userInfo);
        return true;
    }

    @Override
    public List<UserInfos> findAll(){
        return findAll(UserInfos.class);
    }

    /**
     * in database
     * fetch a set of bank from database
     * @param end
     * @param start
     * @return userInfo<list>
     */
    @Override
    public  List<UserInfos> findSubset ( int start,int end ) {
        return findAll(UserInfos.class).subList(start, end);
    }

    /**
     * in database
     * select a piece of bank info via ID
     * @param id
     * @return userInfo<UserInfo>
     */
    @Override
    public UserInfos findById ( Long id ) {
        List<UserInfos> userInfos = findBy(UserInfos.class, "id", id);
        if ( userInfos.isEmpty() ) {
            return null;
        }else{
            return userInfos.get(0);
        }
    }

    /**
     * count the num of userInfos
     * @return <int>
     */
    @Override
    public int  num() {
        return findAll(UserInfos.class).size();
    }

    /**
     * in database
     * query to see it if exist
     * @param userInfo <userInfo>
     * @return <boolean>
     */
    @Override
    public  boolean isExist( UserInfos userInfo) {
        List<UserInfos> userList = (List<UserInfos>) findBy(UserInfos.class,"email",userInfo.getEmail());
        if(userList!=null && !userList.isEmpty() && "N".equals(userList.get(0).getDelFlg())){
            return true;
        }
        return false;
    }




    /**
     * 校验密码
     *
     * @param userInfo
     * @return
     */
    public boolean verify(UserInfos userInfo){
        List<UserInfos> user = (List<UserInfos>) findBy(UserInfos.class,"userId",userInfo.getUserId());
        if(user == null || user.isEmpty()){
            return false;
        }
        return user.get(0).getPassword().equals(userInfo.getPassword());
    }
}
