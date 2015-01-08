package common.vo;

import java.util.List;

/**
 * Created by guxuelong on 2015/1/7.
 */
public class ExcelContainer {
    private List<String> fieldName;
    private List<List<String>> fieldContext;

    public List<String> getFieldName() {
        return fieldName;
    }

    public void setFieldName(List<String> fieldName) {
        this.fieldName = fieldName;
    }

    public List<List<String>> getFieldContext() {
        return fieldContext;
    }

    public void setFieldContext(List<List<String>> fieldContext) {
        this.fieldContext = fieldContext;
    }
}
