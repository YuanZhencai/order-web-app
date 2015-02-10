package service;

import common.vo.PageVo;
import vo.MenuInfoVo;

import java.util.List;

/**
 * Created by Administrator on 2015/2/10.
 */
public interface MenuService {
	public List<MenuInfoVo> findMenusBy(PageVo pageVo);
}
