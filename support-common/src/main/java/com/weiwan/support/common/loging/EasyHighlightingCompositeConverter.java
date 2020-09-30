package com.weiwan.support.common.loging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * 自定义日志等级高亮颜色
 * 
 * @author kun
 * 
 */
public class EasyHighlightingCompositeConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {

    /**
     * (non-Javadoc)
     * 
     * @see ForegroundCompositeConverterBase#
     *      getForegroundColorCode(java.lang.Object)
     */
    protected String getForegroundColorCode(ILoggingEvent event) {
        switch (event.getLevel().toInt()) {
            case Level.ERROR_INT:
                return ANSIConstants.RED_FG;
            case Level.WARN_INT:
                return ANSIConstants.YELLOW_FG;
            case Level.INFO_INT:
                return ANSIConstants.GREEN_FG;
            case Level.DEBUG_INT:
                return ANSIConstants.MAGENTA_FG;
            default:
                return null;
        }
    }
}