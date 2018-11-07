package kr.hellodev.support.commons.bean;

import kr.hellodev.support.commons.exception.BeanException;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 07/11/2018 11:23 AM
 */
public class InfoBean extends AbstractInfoBean {

    private static final long serialVersionUID = -4341285060725800644L;

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        try {
            return super.getString(key);
        } catch (BeanException e) {
            return defaultValue;
        }
    }
}
