package com.weiwan.support.common.config;

import com.google.gson.internal.LinkedTreeMap;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


public abstract class AbstractConfig implements Serializable {

    protected Map<String, Object> internalMap;

    public AbstractConfig(Map<String, Object> map) {
        if (map != null) {
            internalMap = map;
        } else {
            internalMap = new HashMap<>();
        }
    }

    public AbstractConfig() {

    }


    public Map<String, Object> getAll() {
        return internalMap;
    }

    public void setVal(String key, Object value) {
        internalMap.put(key, value);
    }

    public void setStringVal(String key, String value) {
        setVal(key, value);
    }

    public void setBooleanVal(String key, boolean value) {
        setVal(key, value);
    }

    public void setIntVal(String key, int value) {
        setVal(key, value);
    }

    public void setLongVal(String key, long value) {
        setVal(key, value);
    }

    public void setDoubleVal(String key, double value) {
        setVal(key, value);
    }

    public Object getVal(String key) {
        Object obj = internalMap.get(key);
        if (obj instanceof LinkedTreeMap) {
            LinkedTreeMap treeMap = (LinkedTreeMap) obj;
            Map<String, Object> newMap = new HashMap<>(treeMap.size());
            newMap.putAll(treeMap);
            return newMap;
        }
        return obj;
    }

    public Object getVal(String key, Object defaultValue) {
        Object ret = getVal(key);
        if (ret == null) {
            return defaultValue;
        }
        return ret;
    }

    public String getStringVal(String key) {
        return (String) internalMap.get(key);
    }

    public String getStringVal(String key, String defaultValue) {
        String ret = getStringVal(key);
        if (ret == null || ret.trim().length() == 0) {
            return defaultValue;
        }
        return ret;
    }

    public int getIntVal(String key, int defaultValue) {
        Object ret = internalMap.get(key);
        if (ret == null) {
            return defaultValue;
        }
        if (ret instanceof Integer) {
            return ((Integer) ret).intValue();
        }
        if (ret instanceof String) {
            return Integer.valueOf((String) ret).intValue();
        }
        if (ret instanceof Long) {
            return ((Long) ret).intValue();
        }
        if (ret instanceof Float) {
            return ((Float) ret).intValue();
        }
        if (ret instanceof Double) {
            return ((Double) ret).intValue();
        }
        if (ret instanceof BigInteger) {
            return ((BigInteger) ret).intValue();
        }
        if (ret instanceof BigDecimal) {
            return ((BigDecimal) ret).intValue();
        }
        throw new RuntimeException("can't cast " + key + " from " + ret.getClass().getName() + " to Integer");
    }

    public long getLongVal(String key, long defaultValue) {
        Object ret = internalMap.get(key);
        if (ret == null) {
            return defaultValue;
        }
        if (ret instanceof Long) {
            return ((Long) ret);
        }
        if (ret instanceof Integer) {
            return ((Integer) ret).longValue();
        }
        if (ret instanceof String) {
            return Long.valueOf((String) ret);
        }
        if (ret instanceof Float) {
            return ((Float) ret).longValue();
        }
        if (ret instanceof Double) {
            return ((Double) ret).longValue();
        }
        if (ret instanceof BigInteger) {
            return ((BigInteger) ret).longValue();
        }
        if (ret instanceof BigDecimal) {
            return ((BigDecimal) ret).longValue();
        }
        throw new RuntimeException("can't cast " + key + " from " + ret.getClass().getName() + " to Long");
    }

    public double getDoubleVal(String key, double defaultValue) {
        Object ret = internalMap.get(key);
        if (ret == null) {
            return defaultValue;
        }
        if (ret instanceof Double) {
            return ((Double) ret);
        }
        if (ret instanceof Long) {
            return ((Long) ret).doubleValue();
        }
        if (ret instanceof Integer) {
            return ((Integer) ret).doubleValue();
        }
        if (ret instanceof String) {
            return Double.valueOf((String) ret);
        }
        if (ret instanceof Float) {
            return ((Float) ret).doubleValue();
        }
        if (ret instanceof BigInteger) {
            return ((BigInteger) ret).doubleValue();
        }
        if (ret instanceof BigDecimal) {
            return ((BigDecimal) ret).doubleValue();
        }
        throw new RuntimeException("can't cast " + key + " from " + ret.getClass().getName() + " to Double");
    }


    public boolean getBooleanVal(String key, boolean defaultValue) {
        Object ret = internalMap.get(key);
        if (ret == null) {
            return defaultValue;
        }
        if (ret instanceof Boolean) {
            return (Boolean) ret;
        }
        if (ret instanceof String) {
            return Boolean.valueOf(((String) ret).toLowerCase());
        }
        throw new RuntimeException("can't cast " + key + " from " + ret.getClass().getName() + " to Boolean");
    }

    public List<String> getListForSplit(String key, String split) {
        List<String> vars = new ArrayList<>();
        if (key != null && !"".equalsIgnoreCase(key)) {
            Object obj = internalMap.get(key);
            if (obj instanceof String) {
                String[] splits = ((String) obj).split(split);
                vars.addAll(Arrays.asList(splits));
            }
        }
        return vars;
    }

}