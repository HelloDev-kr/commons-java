package kr.hellodev.support.commons.property;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 4:54 PM
 * <p>
 * 공통 Property 를 처리하는 서비스
 */

public interface PropertyService {

    /**
     * 프로퍼티 키 목록 읽기
     *
     * @return key를 위한 Iterator
     */
    public Iterator<?> getKeys();

    /**
     * prefix를 이용한 키 목록 읽기
     *
     * @param prefix prefix
     * @return prefix에 매칭되는 키목록
     */
    public Iterator<?> getKeys(String prefix);

    /**
     * boolean 타입의 프로퍼티 값 얻기
     *
     * @param key 프로퍼티 키
     * @return boolean 타입의 값
     */
    public boolean getBoolean(String key);

    /**
     * boolean 타입의 프로퍼티 값 얻기(디폴트값을 입력받음)
     *
     * @param key          프로퍼티
     * @param defaultValue 기본 값
     * @return boolean 타입의 값
     */
    public boolean getBoolean(String key, boolean defaultValue);

    /**
     * double 타입의 프로퍼티 값 얻기
     *
     * @param key 프로퍼티 키
     * @return double 타입의 값
     */
    public double getDouble(String key);

    /**
     * double 타입의 프로퍼티 값 얻기
     *
     * @param key          프로퍼티 키
     * @param defaultValue 기본 값
     * @return double 타입의 값
     */
    public double getDouble(String key, double defaultValue);

    /**
     * float 타입의 프로퍼티 값 얻기
     *
     * @param key 프로퍼티 키
     * @return float 타입의 값
     */
    public float getFloat(String key);

    /**
     * float 타입의 프로퍼티 값 얻기
     *
     * @param key          프로퍼티 키
     * @param defaultValue 기본 값
     * @return float 타입의 값
     */
    public float getFloat(String key, float defaultValue);

    /**
     * int 타입의 프로퍼티 값 얻기
     *
     * @param key 프로퍼티 키
     * @return int 타입의 값
     */
    public int getInt(String key);

    /**
     * int 타입의 프로퍼티 값 얻기
     *
     * @param key          프로퍼티 키
     * @param defaultValue 기본 값
     * @return int 타입의 값
     */
    public int getInt(String key, int defaultValue);

    /**
     * long 타입의 프로퍼티 값 얻기
     *
     * @param key 프로퍼티 키
     * @return long 타입의 값
     */
    public long getLong(String key);

    /**
     * long 타입의 프로퍼티 값 얻기
     *
     * @param key          프로퍼티 키
     * @param defaultValue 기본 값
     * @return long 타입의 값
     */
    public long getLong(String key, long defaultValue);

    /**
     * String 타입의 프로퍼티 값 얻기
     *
     * @param key 프로퍼티 키
     * @return String 타입의 값
     */
    public String getString(String key);

    /**
     * String 타입의 프로퍼티 값 얻기
     *
     * @param key          프로퍼티 키
     * @param defaultValue 기본 값
     * @return String 타입의 값
     */
    public String getString(String key, String defaultValue);

    /**
     * String[] 타입의 프로퍼티 값 얻기
     *
     * @param key 프로퍼티 키
     * @return String[] 타입의 값
     */
    public String[] getStringArray(String key);

    /**
     * Vector 타입의 프로퍼티 값 얻기
     *
     * @param key 프로퍼티 키
     * @return Vector 타입의 값
     */
    public Vector<?> getVector(String key);

    /**
     * Vector 타입의 프로퍼티 값 얻기
     *
     * @param key          프로퍼티 키
     * @param defaultValue 기본 값
     * @return Vector 타입의 값
     */
    public Vector<?> getVector(String key, Vector<?> defaultValue);

    /**
     * 전체 키/값 쌍 얻기
     *
     * @return 전체 키/값 쌍
     */
    public Collection<?> getAllKeyValue();

    /**
     * 프로퍼티 replace 처리
     *
     * @param key
     * @param target
     * @param val
     * @return
     * @throws Exception
     */
    public String getReplaceString(String key, String target, String val) throws Exception;

    /**
     * resource 변경시 refresh
     *
     * @throws Exception
     */
    public void refreshPropertyFiles() throws Exception;
}
