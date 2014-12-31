package service.impl;
import dao.UserInfoDao;
import dao.impl.UserInfoDaoImpl;
import models.UserInfos;
import org.apache.commons.beanutils.BeanUtils;
import org.joda.time.DateTime;
import service.UserInfoService;
import vo.UserInfoVo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guxuelong on 2014/12/27.
 */
public class UserInfoServiceImpl implements UserInfoService {

    private  UserInfoDao dao = new UserInfoDaoImpl();

    /**
     * 查询所有的用户列表
     *
     * @return
     */
    @Override
    public List<UserInfoVo> findAll() throws Exception {
        List<UserInfos> list = dao.findAll();
        List<UserInfoVo> voList = new ArrayList<>();
        for (UserInfos userInfos : list) {
            if("N".equals(userInfos.getDelFlg())) {
                UserInfoVo userInfoVo = new UserInfoVo();
                BeanUtils.copyProperties(userInfoVo, userInfos);
                voList.add(userInfoVo);
            }
        }
        return voList;
    }

    /**
     * 添加新用户
     *
     * @param userInfoVo
     * @return
     */
    @Override
    public Long add(UserInfoVo userInfoVo) throws Exception {
        UserInfos userinfo = new UserInfos();
        BeanUtils.copyProperties(userinfo,userInfoVo);
        if(dao.isExist(userinfo)){
            throw new Exception("用户已存在");
        }
        userinfo.setDelFlg("N");
        userinfo.setPassword("sunlights");
        userinfo.setCreateBy("sunlights");
        userinfo.setCreateTime(new DateTime().toDate());
        userinfo.setUpdateBy("sunlights");
        userinfo.setUpdateTime(new DateTime().toDate());
        return dao.doInsert(userinfo);
    }

    /**
     * 更新用户信息
     *
     * @param userInfoVo
     */
    @Override
    public void update(UserInfoVo userInfoVo) {
        UserInfos userInfo = dao.findById(userInfoVo.getId());
        userInfo.setEmail(userInfoVo.getEmail());
        userInfo.setUserName(userInfoVo.getUserName());
        userInfo.setUpdateTime(new DateTime().toDate());
        dao.doUpdate(userInfo);
    }

    /**
     * 删除用户信息
     *
     * @param userInfoVo
     */
    @Override
    public void delete(UserInfoVo userInfoVo) {

        UserInfos userInfo = dao.findById(userInfoVo.getId());
        if ( userInfo==null){
            return;
        }
        userInfo.setDelFlg("Y");
        userInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        dao.doDelete(userInfo);
    }
}
