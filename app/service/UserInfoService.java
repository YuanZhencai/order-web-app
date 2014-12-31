package service;

import vo.UserInfoVo;

import java.util.List;

/**
 * Created by guxuelong on 2014/12/27.
 */
public interface UserInfoService {

    /**
     * 查询所有的用户列表
     *
     * @return
     */
    public List<UserInfoVo> findAll() throws Exception;

    /**
     * 添加新用户
     *
     * @return
     */
    public Long add(UserInfoVo userInfoVo) throws Exception;

    /**
     * 更新用户信息
     *
     * @param userInfoVo
     */
    public void update(UserInfoVo userInfoVo);

    /**
     * 删除用户信息
     *
     * @param userInfoVo
     */
    public void delete(UserInfoVo userInfoVo);
}
