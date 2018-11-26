package kr.hellodev.support.commons.property.impl;

import kr.hellodev.support.commons.property.PropertyService;
import org.apache.commons.collections.ExtendedProperties;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 4:55 PM
 * <p>
 * 프로퍼티 공통 클래스
 */
public class PropertyServiceImpl implements PropertyService, InitializingBean, DisposableBean, ResourceLoaderAware {

    private ExtendedProperties extendedProperties;
    private ResourceLoader resourceLoader;

    private Set<?> extFileName;
    private Map<?, ?> properties;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void destroy() {
        extendedProperties = null;
    }

    /**
     * Bean 초기화 함수로 최초 생성시 필요한 Property set 처리
     */
    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        extendedProperties = new ExtendedProperties();

        // 외부파일이 정의되었을때
        if (extFileName != null)
            refreshPropertyFiles();

        // "properties" 속성이 없는 경우 처리
        if (properties == null)
            return;

        for (Object o : properties.entrySet()) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            if (key == null || "".equals(value))
                throw new Exception("error.properties.check.essential");

            extendedProperties.put(key, value);
        }
    }

    @Override
    public Iterator<?> getKeys() {
        return getConfiguration().getKeys();
    }

    @Override
    public Iterator<?> getKeys(String prefix) {
        return getConfiguration().getKeys(prefix);
    }

    @Override
    public boolean getBoolean(String key) {
        return getConfiguration().getBoolean(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return getConfiguration().getBoolean(key, defaultValue);
    }

    @Override
    public double getDouble(String key) {
        return getConfiguration().getDouble(key);
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return getConfiguration().getDouble(key, defaultValue);
    }

    @Override
    public float getFloat(String key) {
        return getConfiguration().getFloat(key);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return getConfiguration().getFloat(key, defaultValue);
    }

    @Override
    public int getInt(String key) {
        return getConfiguration().getInteger(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return getConfiguration().getInteger(key, defaultValue);
    }

    @Override
    public long getLong(String key) {
        return getConfiguration().getLong(key);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return getConfiguration().getLong(key, defaultValue);
    }

    @Override
    public String getString(String key) {
        return getConfiguration().getString(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return getConfiguration().getString(key, defaultValue);
    }

    @Override
    public String[] getStringArray(String key) {
        return getConfiguration().getStringArray(key);
    }

    @Override
    public Vector<?> getVector(String key) {
        return getConfiguration().getVector(key);
    }

    @Override
    public Vector<?> getVector(String key, Vector<?> defaultValue) {
        return getConfiguration().getVector(key, defaultValue);
    }

    @Override
    public Collection<?> getAllKeyValue() {
        return getConfiguration().values();
    }

    @Override
    public String getReplaceString(String key, String target, String val) {
        return this.getString(key).replace(target, val);
    }

    @Override
    public void refreshPropertyFiles() throws Exception {
        String fileName;

        for (Object element : extFileName) {
            // get element
            String enc = null;

            if (element instanceof Map) {
                Map<?, ?> ele = (Map<?, ?>) element;
                enc = (String) ele.get("encoding");
                fileName = (String) ele.get("filename");
            } else {
                fileName = (String) element;
            }
            loadPropertyResources(fileName, enc);
        }
    }

    /**
     * properties 얻기
     *
     * @return Properties of requested Service
     */
    private ExtendedProperties getConfiguration() {
        return extendedProperties;
    }

    /**
     * 파일위치 정보를 가지고 resources 정보 추출
     *
     * @param location 파일 위치
     * @param encoding 인코딩 정보
     * @throws Exception exception
     */
    private void loadPropertyResources(String location, String encoding) throws Exception {
        if (resourceLoader instanceof ResourcePatternResolver) {
            try {
                Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(location);
                loadPropertyLoop(resources, encoding);
            } catch (IOException ex) {
                throw new BeanDefinitionStoreException("Could not resolve Properties resource pattern [" + location + "]", ex);
            }
        } else {
            Resource resource = resourceLoader.getResource(location);
            loadPropertyRes(resource, encoding);
        }
    }

    /**
     * 멀티로 지정된 경우 처리를 위해 Loop 처리
     *
     * @param resources 리소스 정보
     * @param encoding  인코딩 정보
     * @throws Exception exception
     */
    private void loadPropertyLoop(Resource[] resources, String encoding) throws Exception {
        Assert.notNull(resources, "Resource array must not be null");
        for (Resource resource : resources) loadPropertyRes(resource, encoding);
    }

    /**
     * 파일 정보를 읽어서 props 에 저장
     *
     * @param resource 리소스 정보
     * @param encoding 인코딩 정보
     * @throws Exception exception
     */
    private void loadPropertyRes(Resource resource, String encoding) throws Exception {
        ExtendedProperties props = new ExtendedProperties();
        props.load(resource.getInputStream(), encoding);
        extendedProperties.combine(props);
    }

    /**
     * extFileName 을 지정할 때 Attribute 로 정의
     *
     * @param extFileName extFileName
     */
    public void setExtFileName(Set<?> extFileName) {
        this.extFileName = extFileName;
    }

    /**
     * properties 를 지정할 때 Attribute 로 정의
     *
     * @param properties properties
     */
    public void setProperties(Map<?, ?> properties) {
        this.properties = properties;
    }
}
