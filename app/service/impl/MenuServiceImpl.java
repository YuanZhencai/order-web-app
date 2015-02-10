package service.impl;

import common.service.PageService;
import common.vo.PageVo;
import common.dao.EntityBaseDao;
import service.MenuService;
import vo.MenuInfoVo;

import java.util.List;

/**
 * Created by Yuan on 2015/2/10.
 */
public class MenuServiceImpl extends EntityBaseDao implements MenuService {

	private PageService pageService = new PageService();

	@Override
	public List<MenuInfoVo> findMenusBy (PageVo pageVo) {
		StringBuffer xsql = new StringBuffer();
		xsql.append(" select new vo.MenuInfoVo(m) from MenuInfo m");
		xsql.append(" where 1=1 and m.delFlg = 'N'");
		xsql.append(" /~ and m.id = {id} ~/ ");
		xsql.append(" /~ and m.foodId = {foodId} ~/ ");
		xsql.append(" /~ and m.foodName like {foodName} ~/ ");
		return pageService.findXsqlBy(xsql.toString(),pageVo);
	}
}
