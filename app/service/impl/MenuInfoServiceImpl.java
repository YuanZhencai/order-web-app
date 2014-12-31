package service.impl;
import dao.MenuInfoDao;
import dao.impl.MenuInfoDaoImpl;
import models.MenuInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.joda.time.DateTime;
import service.MenuInfoService;
import vo.MenuInfoVo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guxuelong on 2014/12/27.
 */
public class MenuInfoServiceImpl implements MenuInfoService {

    private  MenuInfoDao dao = new MenuInfoDaoImpl();

    /**
     * 查询所有的菜单列表
     *
     * @return
     */
    @Override
    public List<MenuInfoVo> findAll() throws Exception {
        List<MenuInfo> list = dao.findAll();
        List<MenuInfoVo> voList = new ArrayList<>();
        for (MenuInfo menuInfos : list) {
            if("N".equals(menuInfos.getDelFlg())) {
                MenuInfoVo MenuInfoVo = new MenuInfoVo();
                BeanUtils.copyProperties(MenuInfoVo, menuInfos);
                voList.add(MenuInfoVo);
            }
        }
        return voList;
    }

    /**
     * 添加新菜单
     *
     * @param menuInfoVo
     * @return
     */
    @Override
    public Long add(MenuInfoVo menuInfoVo) throws Exception {
        MenuInfo menuInfo = new MenuInfo();
        BeanUtils.copyProperties(menuInfo,menuInfoVo);
        if(dao.isExist(menuInfo)){
            throw new Exception("菜单已存在");
        }
        menuInfo.setDelFlg("N");
        menuInfo.setCount(BigDecimal.ZERO);
        menuInfo.setCreateBy("sunlights");
        menuInfo.setCreateTime(new DateTime().toDate());
        menuInfo.setUpdateBy("sunlights");
        menuInfo.setUpdateTime(new DateTime().toDate());
        Long id = dao.doInsert(menuInfo);
        return id;
    }

    /**
     * 更新菜单信息
     *
     * @param menuInfoVo
     */
    @Override
    public void update(MenuInfoVo menuInfoVo) throws Exception {
        MenuInfo menuInfo = dao.findById(menuInfoVo.getId());
        menuInfo.setFoodName(menuInfoVo.getFoodName());
        menuInfo.setPrice(menuInfoVo.getPrice());
        menuInfo.setPicture(menuInfoVo.getPicture());
        menuInfo.setSource(menuInfoVo.getSource());
        menuInfo.setUpdateTime(new DateTime().toDate());
        dao.doUpdate(menuInfo);
    }

    /**
     * 删除菜单信息
     *
     * @param menuInfoVo
     */
    @Override
    public void delete(MenuInfoVo menuInfoVo){

        MenuInfo menuInfo = dao.findById(menuInfoVo.getId());
        if ( menuInfo==null){
            return;
        }
        menuInfo.setDelFlg("Y");
        menuInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        dao.doDelete(menuInfo);
    }
}
