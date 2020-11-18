package com.weiwan.support.core.pojo;

import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author: xiaozhennan
 * @Date: 2020/8/10 10:21
 * @Package: com.weiwan.support.core.pub.pojo
 * @ClassName: DataRow
 * @Description:
 **/
@PublicEvolving
public class DataRow<T> implements Serializable {


    private final T[] fields;


    public DataRow(int arity) {
        this.fields = (T[]) new Object[arity];
    }



    public int getArity() {
        return fields.length;
    }


    public T getField(int pos) {
        return fields[pos];
    }


    public void setField(int pos, T value) {
        fields[pos] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(StringUtils.arrayAwareToString(fields[i]));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataRow row = (DataRow) o;

        return Arrays.deepEquals(fields, row.fields);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(fields);
    }


    public static DataRow of(DataField... values) {
        DataRow row = new DataRow(values.length);
        for (int i = 0; i < values.length; i++) {
            row.setField(i, values[i]);
        }
        return row;
    }

    public static DataRow copy(DataRow row) {
        final DataRow newRow = new DataRow(row.fields.length);
        System.arraycopy(row.fields, 0, newRow.fields, 0, row.fields.length);
        return newRow;
    }


    public static DataRow project(DataRow row, int[] fields) {
        final DataRow newRow = new DataRow(fields.length);
        for (int i = 0; i < fields.length; i++) {
            newRow.fields[i] = row.fields[fields[i]];
        }
        return newRow;
    }


    public T[] getFields() {
        return fields;
    }
}
