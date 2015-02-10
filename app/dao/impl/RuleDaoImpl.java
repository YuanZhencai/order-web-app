package dao.impl;

import common.dao.EntityBaseDao;
import dao.RuleDao;
import models.Rule;
import org.joda.time.DateTime;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by guxuelong on 2015/1/4.
 */
public class RuleDaoImpl extends EntityBaseDao implements RuleDao{
    private static final String QUERY_RULE = "select t from Rule t where t.ruleType = :ruleType and t.ruleKey = :ruleKey";

    @Override
    public Rule doInsert(Rule rule) {
        rule.setDelFlg("N");
        rule.setCreateTime(new DateTime().toDate());
        rule.setCreateBy("sunlights");
        rule.setUpdateTime(new DateTime().toDate());
        rule.setUpdateBy("sunlights");
        return create(rule);
    }

    @Override
    public boolean doDelete(Rule rule) {
        rule.setDelFlg("Y");
        rule.setUpdateTime(new DateTime().toDate());
        rule.setUpdateBy("sunlights");
        update(rule);
        return true;
    }

    @Override
    public boolean doUpdate(Rule rule) {
        rule.setUpdateTime(new DateTime().toDate());
        rule.setUpdateBy("sunlights");
        update(rule);
        return true;
    }

    @Override
    public List<Rule> findAll() {
        return findAll(Rule.class);
    }

    @Override
    public List<Rule> findSubset(int start, int end) {
        return null;
    }

    @Override
    public Rule findById(Long id) {
        return findById(id);
    }

    @Override
    public List<Rule> findByType(String type) {

        return findBy(Rule.class,"ruleType",type);
    }

    @Override
    public Rule findByTK(Rule rule) {
        Query query = super.createQuery(QUERY_RULE);
        query.setParameter("ruleType",rule.getRuleType());
        query.setParameter("ruleKey",rule.getRuleKey());
        List<Rule> result = query.getResultList();
        return result.get(0);
    }

    @Override
    public int num() {
        return 0;
    }

    @Override
    public boolean isExist(Rule rule) {
        return false;
    }
}
