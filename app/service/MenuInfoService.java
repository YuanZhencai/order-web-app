package service;

import vo.MenuInfoVo;

import java.util.List;

/**
 * Created by guxuelong on 2014/12/27.
 */
public interface MenuInfoService {

    /**
     * 查询所有的菜单列表
     *
     * @return
     */
    public List<MenuInfoVo> findAll() throws Exception;

    /**
     * 添加新菜单
     *
     * @return
     */
    public Long add(MenuInfoVo menuInfoVo) throws Exception;

    /**
     * 更新菜单信息
     *
     * @param menuInfoVo
     */
    public void update(MenuInfoVo menuInfoVo) throws Exception;

    /**
     * 删除菜单信息
     *
     * @param menuInfoVo
     */
    public void delete(MenuInfoVo menuInfoVo);
}
