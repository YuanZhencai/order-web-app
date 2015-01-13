package dao;

import models.Rule;

import java.util.List;

/**
 *
 */
public interface RuleDao {
    public Rule doInsert(Rule rule);

    public boolean doDelete(Rule rule);

    public boolean doUpdate(Rule rule);

    public List<Rule> findAll();

    public List<Rule> findSubset(int start, int end);

    public Rule findById(Long id);

    public List<Rule> findByType(String type);

    public Rule findByTK(Rule rule);

    public int num();

    public boolean isExist(Rule rule);
}
