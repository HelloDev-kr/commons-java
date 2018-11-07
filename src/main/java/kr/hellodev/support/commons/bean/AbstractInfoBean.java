package kr.hellodev.support.commons.bean;

import kr.hellodev.support.commons.exception.BeanException;

import java.io.Serializable;
import java.util.*;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 07/11/2018 11:19 AM
 */
abstract class AbstractInfoBean implements Map<String, Object>, Serializable {

    public static final Object[] emptyArray = new Object[0];
    public static final String[] emptyStringArray = new String[0];
    public static final List<Object> emptyList = new ArrayList<>(0);
    public static final List<String> emptyStringList = new ArrayList<>(0);

    private static final long serialVersionUID = 8628093839650953690L;

    private Map<String, Object> data = new HashMap<>();

    public Map<String, Object> getDataMap() {
        return data;
    }

    public void put(String key, String value) {
        data.put(key, value);
    }

    public void put(String key, String[] value) {
        data.put(key, value);
    }

    public void put(String key, Integer value) {
        data.put(key, value);
    }

    public void put(String key, Long value) {
        data.put(key, value);
    }

    public void put(String key, Date value) {
        data.put(key, value);
    }

    public void put(String key, List<Object> value) {
        data.put(key, value);
    }

    /**
     * 동일 키에 동이 형의 데이터가 있으면 추가로 입력하고 없으면 put 한다.
     *
     * @param key key
     * @param obj obj
     */
    public void add(String key, Object obj) {
        try {
            if (obj == null)
                return;
            Object val = get(key);
            if ((val instanceof Map) && (obj instanceof Map)) {
                @SuppressWarnings("unchecked")
                Map<String, Object> value1 = (Map<String, Object>) val;
                @SuppressWarnings("unchecked")
                Map<String, Object> value2 = (Map<String, Object>) obj;
                value1.putAll(value2);
            } else if ((val instanceof List) && (obj instanceof List)) {
                @SuppressWarnings("unchecked")
                List<Object> value1 = (List<Object>) val;
                @SuppressWarnings("unchecked")
                List<Object> value2 = (List<Object>) obj;
                value1.addAll(value2);
            } else if ((val instanceof String[]) && (obj instanceof String[])) {
                String[] value1 = (String[]) val;
                String[] value2 = (String[]) obj;
                String[] value = new String[value1.length + value2.length];
                System.arraycopy(value1, 0, value, 0, value1.length);
                System.arraycopy(value2, 0, value, value1.length, value2.length);
                put(key, value);
            } else {
                throw new BeanException("The added object is NOT same!");
            }
        } catch (BeanException e) {
            put(key, obj);
        }
    }

    /**
     * 동일키의 데이터는 스트링배열로 셋팅되는데, 그 배열에 값을 추가해서 put 한다.
     *
     * @param key key
     * @param obj obj
     */
    public void addStringToArray(String key, String obj) {
        try {
            if (obj == null)
                return;
            Object val = get(key);
            String[] value1 = null;
            String[] value2 = new String[1];
            value2[0] = obj;

            if (val instanceof String) {
                value1 = new String[1];
                value1[0] = (String) val;
            } else if (val instanceof String[]) {
                value1 = (String[]) val;
            } else {
                throw new BeanException("The added object is NOT same!");
            }

            String[] value = new String[value1.length + value2.length];
            System.arraycopy(value1, 0, value, 0, value1.length);
            System.arraycopy(value2, 0, value, value1.length, value2.length);
            put(key, value);
        } catch (BeanException e) {
            put(key, obj);
        }
    }

    public Object get(String key) {
        try {
            Object value = data.get(key);
            if (value == null)
                value = data.get(key);
            if (value == null)
                throw new BeanException(key + "'s value is not found.");
            return value;
        } catch (NullPointerException e) {
            throw new BeanException("key is NULL on calling InfoBean");

        }
    }

    public Object get(String key, String defultValue) {
        try {
            Object value = data.get(key);
            if (value == null)
                value = data.get(key);
            if (value == null)
                return defultValue;

            return value;
        } catch (NullPointerException e) {
            throw new BeanException("key is NULL on calling InfoBean");

        }
    }

    public Iterator<String> getKeys() {
        return data.keySet().iterator();
    }

    public Integer getInt(String key) {
        Object value = get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return new Integer((String) value);
            } catch (NumberFormatException e) {
                throw new BeanException(key + "'s value is not a integer value(" + value + ").");
            }
        } else {
            throw new BeanException(key + "'s value is not a integer value(Object:" + value.getClass() + ").");
        }
    }

    public Long getByteLong(String key) {
        Object value = get(key);
        if (value instanceof Integer) {
            return Long.parseLong(value.toString());
        } else if (value instanceof int[]) {
            int[] vals = (int[]) value;
            long sum = 0;
            for (int val : vals) {
                sum = sum + val;
            }
            return sum;
        } else if (value instanceof String[]) {
            String[] vals = (String[]) value;
            long sum = 0;
            for (String val : vals) {
                sum = sum + (1 << Long.parseLong("".equals(val) ? "0" : val));
            }
            return sum;
        } else if (value instanceof String) {
            try {
                return (long) (1 << Long.parseLong((String) value));
            } catch (NumberFormatException e) {
                throw new BeanException(key + "'s value is not a Long value(" + value + ").");
            }
        } else {
            throw new BeanException(key + "'s value is not a Long value(Object:" + value.getClass() + ").");
        }
    }

    public Long getLong(String key) {
        Object value = get(key);
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof String) {
            try {
                return new Long((String) value);
            } catch (NumberFormatException e) {
                throw new BeanException(key + "'s value is not a long value(" + value + ").");
            }
        } else {
            throw new BeanException(key + "'s value is not a long value(Object:" + value.getClass() + ").");
        }
    }

    public Double getDouble(String key) {
        Object value = get(key);
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof String) {
            try {
                return new Double((String) value);
            } catch (NumberFormatException e) {
                throw new BeanException(key + "'s value is not a double value(" + value + ").");
            }
        } else {
            throw new BeanException(key + "'s value is not a double value(Object:" + value.getClass() + ").");
        }
    }

    public String getString(String key) {
        Object value = get(key);

        if (value == null)
            return null;

        if (value instanceof String) {
            return (String) value;
        }

        if (value instanceof String[]) {
            StringBuilder sb = new StringBuilder();
            String[] vals = (String[]) value;

            for (int k = 0; k < vals.length; k++) {
                sb.append(vals[k]);
                if ((k + 1) != vals.length)
                    sb.append(",");
            }

            return sb.toString();
        }

        if (value instanceof List) {
            StringBuilder sb = new StringBuilder();

            for (Iterator<?> k = ((List<?>) value).iterator(); k.hasNext(); ) {
                sb.append(k.next());
                if (k.hasNext())
                    sb.append(",");
            }

            return sb.toString();
        }

        return value.toString();
    }

    public String[] getStringArray(String key) {
        Object value = get(key);

        if (value == null)
            return null;

        if (value instanceof String[]) {
            return (String[]) value;
        }

        if (value instanceof String) {
            return new String[]{
                    (String) value
            };
        }

        if (value instanceof List<?>) {
            List<?> reval = (List<?>) value;
            String[] result = new String[reval.size()];

            for (int i = 0; i < reval.size(); i++) {
                result[i] = (reval.get(i) == null ? null : reval.get(i).toString());
            }

            return result;
        }

        throw new BeanException(key + "'s value is not a String[] value(" + value.getClass() + ").");
    }

    public List<?> getList(String key) {
        Object value = get(key);

        if (value == null)
            return null;

        if (value instanceof List) {
            return (List<?>) value;
        }

        if (value instanceof String[]) {
            return Arrays.asList((String[]) value);
        }

        if (value instanceof String) {
            return Collections.singletonList((String) value);
        }

        throw new BeanException(key + "'s value is not a java.util.List value(" + value.getClass() + ").");
    }

    public Object get(String key, Object defaultValue) {
        try {
            return get(key);
        } catch (BeanException e) {
            return defaultValue;
        }
    }

    public Integer getInt(String key, Integer defaultValue) {
        try {
            return getInt(key);
        } catch (BeanException e) {
            return defaultValue;
        }
    }

    public Long getLong(String key, Long defaultValue) {
        try {
            return getLong(key);
        } catch (BeanException e) {
            return defaultValue;
        }
    }

    public Long getByteLong(String key, Long defaultValue) {
        try {
            return getByteLong(key);
        } catch (BeanException e) {
            return defaultValue;
        }
    }

    public String getByteLong(String key, String defaultValue) {
        try {
            return getByteLong(key).toString();
        } catch (BeanException e) {
            return defaultValue;
        }
    }

    public Double getDouble(String key, Double defaultValue) {
        try {
            return getDouble(key);
        } catch (BeanException e) {
            return defaultValue;
        }
    }

    /**
     * String return 함수에서는 "" 값도 null 일때와 똑같이 default value 를 돌여준다.
     *
     * @param key          key
     * @param defaultValue defaultValue
     * @return String
     */
    public String getString(String key, String defaultValue) {
        try {
            String value = getString(key);
            if ("".equals(value)) {
                return defaultValue;
            } else {
                return getString(key);
            }
        } catch (BeanException e) {
            return defaultValue;
        }
    }

    public String[] getStringArray(String key, String[] defaultValue) {
        try {
            String[] value = getStringArray(key);
            if (value.length == 0) {
                return defaultValue;
            } else {
                return value;
            }

        } catch (BeanException e) {
            return defaultValue;
        }
    }

    public List<?> getList(String key, List<?> defaultValue) {
        try {
            List<?> value = getList(key);
            if (value.size() == 0) {
                return defaultValue;
            } else {
                return value;
            }
        } catch (BeanException e) {
            return defaultValue;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<String> i = getKeys(); i.hasNext(); ) {
            String key = (String) i.next();
            Object value = data.get(key);
            sb.append(key);
            if (value == null) {
                sb.append(":(null)");
            } else if (value instanceof String[]) {
                String[] vals = (String[]) value;
                sb.append(":(String[").append(vals.length).append("] - {\"");
                sb.append(getString(key));
                sb.append("\"})");
            } else if (value instanceof List) {
                List<?> vals = (List<?>) value;
                sb.append(":(List[").append(vals.size()).append("] - {\"");
                sb.append(getString(key));
                sb.append("\"})");
            } else {
                sb.append("(").append(value.getClass()).append(":");
                sb.append(value);
                sb.append(")");
            }
            sb.append("\n");
        }
        return sb.toString();

    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    public Object get(Object key) {
        return data.get(key);
    }

    public Object put(String key, Object value) {
        return data.put(key, value);
    }

    public Object remove(Object key) {
        return data.remove(key);
    }

    public void putAll(Map<? extends String, ?> m) {
        data.putAll(m);
    }

    public void clear() {
        data.clear();
    }

    public Set<String> keySet() {
        return data.keySet();
    }

    public Collection<Object> values() {
        return data.values();
    }

    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return data.entrySet();
    }

    public List<String> getKeyList() {

        List<String> result = new ArrayList<>();

        for (Iterator<String> iterator = this.getKeys(); iterator.hasNext(); ) {
            result.add(iterator.next());
        }

        return result;
    }

    public String getKey(String key) {

        String result;

        for (Iterator<String> iterator = this.getKeys(); iterator.hasNext(); ) {
            result = iterator.next();
            if ((key).equals(result))
                return result;
        }

        return key;
    }
}
