package common.util;

import common.MsgCode;
import common.Severity;
import common.exceptions.BusinessRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.MessageFormat;
import java.util.Date;

/**
 * <p>Project: fsp</p>
 * <p>Title: CommonUtil.java</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2014 Sunlights.cc</p>
 * <p>All Rights Reserved.</p>
 *
 * @author <a href="mailto:jiaming.wang@sunlights.cc">wangJiaMing</a>
 */
public class CommonUtil {
    private static CommonUtil commonUtil = new CommonUtil();

    public static CommonUtil getInstance() {
        return commonUtil;
    }

    public CommonUtil() {

    }


    /**
     * 参数验证
     *
     * @param params
     */
    public void validateParams(String... params) {
        for (String param : params) {
            if (StringUtils.isEmpty(param)) {
                throw errorBusinessException(MsgCode.ACCESS_FAIL, param);
            }
        }
    }

    public BusinessRuntimeException errorBusinessException(MsgCode msgCode, Object... params) {
        String detail = getDetail(msgCode, params);
        return new BusinessRuntimeException(Severity.ERROR, msgCode.getCode(), msgCode.getMessage(), detail);
    }

    private String getDetail(MsgCode msgCode, Object[] params) {
        String detail = msgCode.getDetail();
        if (params != null) {
            detail = MessageFormat.format(detail, params);
        }
        return detail;
    }

    public BusinessRuntimeException fatalBusinessException(MsgCode msgCode, Object... params) {
        String detail = getDetail(msgCode, params);
        return new BusinessRuntimeException(Severity.FATAL, msgCode.getCode(), msgCode.getMessage(), detail);
    }

    public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";

    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String DATE_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_ICU = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern(DATE_FORMAT_SHORT);

    public static  String dateToString(Date date, String... format) {
        if (date == null) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        if (format != null && format.length > 0) {
            return dateTime.toString(format[0]);
        } else {
            return dateTime.toString(DATE_FORMAT_SHORT);
        }
    }

    public static Date stringToDate(String dateString, String... format) {
        if (StringUtils.isEmpty(dateString)) {
            return new DateTime().toDate();
        }

        if (format != null && format.length > 0) {
            DateTimeFormatter dateFormat = DateTimeFormat.forPattern(format[0]);
            DateTime dateTime = DateTime.parse(dateString, dateFormat);
            return dateTime.toDate();
        } else {
            return DateTime.parse(dateString, DATE_FORMAT).toDate();
        }
    }

}
