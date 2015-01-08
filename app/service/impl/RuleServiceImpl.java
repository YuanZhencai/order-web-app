package service.impl;

import dao.RuleDao;
import dao.impl.RuleDaoImpl;
import models.Rule;
import org.apache.commons.beanutils.BeanUtils;
import service.RuleService;
import vo.RuleVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guxuelong on 2015/1/4.
 */
public class RuleServiceImpl implements RuleService {

    private RuleDao dao = new RuleDaoImpl();

    /**
     * 查询所有的规则列表
     *
     * @return
     */
    @Override
    public List<RuleVo> findAll() throws Exception {
        List<Rule> ruleList = dao.findAll();
        List<RuleVo> resultList = new ArrayList<>();
        for (Rule rule : ruleList) {
            RuleVo vo = new RuleVo();
            BeanUtils.copyProperties(vo,rule);
            resultList.add(vo);
        }
        return resultList;
    }

    /**
     * 添加新规则
     *
     * @param ruleVo
     * @return
     */
    @Override
    public void add(RuleVo ruleVo) throws Exception {
        Rule rule = new Rule();
        BeanUtils.copyProperties(rule, ruleVo);
        dao.doInsert(rule);
    }

    /**
     * 更新规则信息
     *
     * @param ruleVo
     */
    @Override
    public void update(RuleVo ruleVo) throws Exception {
        Rule rule = new Rule();
        BeanUtils.copyProperties(rule, ruleVo);
        Rule result = dao.findByTK(rule);
        result.setRuleValue(rule.getRuleValue());
        dao.doUpdate(result);
    }

    /**
     * 删除规则信息
     *
     * @param ruleVo
     */
    @Override
    public void delete(RuleVo ruleVo)throws Exception  {
        Rule rule = new Rule();
        BeanUtils.copyProperties(rule, ruleVo);
        dao.doDelete(rule);
    }
}
