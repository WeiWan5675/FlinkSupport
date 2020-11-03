#!/bin/bash


###################################################
#    作者:肖振男
#    功能:FlinkSupport程序环境相关设置
#    修改日期:
#    备注:
###################################################
source ${HOME}/.bash_profile
#支持配置文件按环境区分
SUPPORT_ENV=pro

#设置home
SUPPORT_HOME=${FLINK_SUPPORT_HOME}
if [ -z "${SUPPORT_HOME}" ]; then
  SUPPORT_HOME="$(cd "`dirname "$0"`"/..; pwd)"
fi


#区分环境
SUPPORT_CONF_DIR=$SUPPORT_HOME/conf
#if [ -z "$SUPPORT_ENV" ]; then
#  SUPPORT_CONF_DIR=$SUPPORT_CONF_DIR
#else
#  SUPPORT_CONF_DIR=$SUPPORT_CONF_DIR/$SUPPORT_ENV
#fi

#声明变量
export SUPPORT_HOME=${SUPPORT_HOME}
export SUPPORT_CONF_DIR=$SUPPORT_CONF_DIR
export SUPPORT_LIB_DIR=$SUPPORT_HOME/lib
export SUPPORT_BIN_DIR=$SUPPORT_HOME/bin
export SUPPORT_EXTLIB_DIR=$SUPPORT_HOME/extlib
export SUPPORT_PLUGINS_DIR=$SUPPORT_HOME/plugins


# Find the java binary
if [ -n "${JAVA_HOME}" ]; then
  JAVA_RUN="${JAVA_HOME}/bin/java"
else
  if [ `command -v java` ]; then
    JAVA_RUN="java"
  else
    echo "JAVA_HOME is not set" >&2
    exit 1
  fi
fi

export JAVA_RUN="${JAVA_RUN}"

