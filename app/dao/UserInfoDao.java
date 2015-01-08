package dao;

import models.UserInfos;

import java.util.List;

/**
 * Created by guxuelong on 2014/12/26.
 */
public interface UserInfoDao {
    public Long doInsert(UserInfos userInfo);

    public boolean doDelete(UserInfos userInfo);

    public boolean doUpdate(UserInfos userInfo);

    public List<UserInfos> findAll();

    public List<UserInfos> findSubset(int start, int end);

    public UserInfos findById(Long id);

    public int num();

    public boolean isExist(UserInfos userInfo);

    public UserInfos verify(UserInfos userInfo);
}
