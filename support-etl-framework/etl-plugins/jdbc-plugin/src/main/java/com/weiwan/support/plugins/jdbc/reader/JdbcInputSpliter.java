package com.weiwan.support.plugins.jdbc.reader;


import com.weiwan.support.etl.framework.api.reader.BaseInputSpliter;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/22 15:43
 * @Package: org.weiwan.argus.core.pub.api
 * @ClassName: JdbcInputSpliter
 * @Description:
 **/
public class JdbcInputSpliter extends BaseInputSpliter {

    private long minOffset;

    private long maxOffset;

    private long userLastOffser;

    private String[] tables;

    public JdbcInputSpliter(int partitionNumber, int totalNumberOfPartitions) {
        super(partitionNumber, totalNumberOfPartitions);
    }


    public long getMinOffset() {
        return minOffset;
    }

    public void setMinOffset(long minOffset) {
        this.minOffset = minOffset;
    }

    public long getMaxOffset() {
        return maxOffset;
    }

    public void setMaxOffset(long maxOffset) {
        this.maxOffset = maxOffset;
    }

    public long getUserLastOffser() {
        return userLastOffser;
    }

    public void setUserLastOffser(long userLastOffser) {
        this.userLastOffser = userLastOffser;
    }

    public String[] getTables() {
        return tables;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }
}
