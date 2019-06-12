package com.xudong.im.constant;

import org.evanframework.CoreConstants;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * 公用常量
 * <p>
 * create at 2016年4月2日 下午1:18:47
 *
 * @author Evan.Shen
 * @since %I%, %G%
 * @see
 */
public interface CommonConstant extends CoreConstants {

    /**
     * 图片尺寸
     */
    int SCALE_WIDTH = 80;
    /**
     * 是
     */
    int YES = 1;
    /**
     * 否
     */
    int NO = 0;
    /**
     * 删除状态 已删除
     */
    int DELETED_TAG = 1;

    /**
     * 删除状态 未删除
     */
    int NOT_DELETED_TAG = 0;

    /**
     * 状态 正常
     */
    int STATUS_NORMAL = 1;

    /**
     * 字符编码
     */
    String WEB_ENCODING = "UTF-8";

    /**
     * 分隔符
     */
    String SPLIT = "&_&";

    /**
     * 短信验证码默认有效时间
     */
    int VALIDATE_CODE_DEFAILT_EXPIRE_SECONDS = 600;

    /**
     * 小数
     */
    int BIGDECIMAL_SCALE_COMPUTING = 4;

    /**
     * 小数
     */
    int BIGDECIMAL_SCALE_SIX = 6;

    /**
     * 小数
     */
    int BIGDECIMAL_SCALE_RESULT = 2;

    /**
     * 小数
     */
    int BIGDECIMAL_SCALE_TEN = 10;

    /**
     * 默认每页记录数
     */
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * 默认token，没有登录时的接口用
     */
    String DEFAULT_TOKEN = "69c735ba3d4b4778e2085f120e0fda52af67f19f";


    /**
     *  默认SECRET，没有登录时的接口用
     */
    String DEFAULT_SECRET = "1ce780e1ce4319df82fb5801a90b01f47a3d59c741c0b63e19de1d905233e1b1";


    String MESSAGE_TOPIC = "xudong";

    /**
     * 初始密码
     */
    String INIT_PASSWORD = "123456";

    //中文排序
    Comparator CHINA_COMPARE = Collator.getInstance(Locale.CHINA);

    int ZERO = 0;

    int HUNDRED = 100;

    int THOUSAND = 1000;

    int TEN_THOUSAND = 100000;


    //pdf 文件后缀
    String PDF_SUFFIX = ".pdf";

    //临时
    String TEMP = "temp";

    String FORMAT_SHORT_STRING_CN = "yyyy年MM月dd日";

    String DEFAULT_STAFF_ID = "1";
}
