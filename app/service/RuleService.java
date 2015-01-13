package service;

import vo.RuleVo;

import java.util.List;

/**
 * Created by guxuelong on 2014/12/27.
 */
public interface RuleService {

    /**
     * 查询所有的规则列表
     *
     * @return
     */
    public List<RuleVo> findAll() throws Exception;


    /**
     * 查询指定规则列表
     *
     * @return
     */
    public List<RuleVo> findByType(String type) throws Exception;

    /**
     * 添加新规则
     *
     * @return
     */
    public void add(RuleVo ruleVo) throws Exception;

    /**
     * 更新规则信息
     *
     * @param ruleVo
     */
    public void update(RuleVo ruleVo) throws Exception;

    /**
     * 删除规则信息
     *
     * @param ruleVo
     */
    public void delete(RuleVo ruleVo)throws Exception ;
}
