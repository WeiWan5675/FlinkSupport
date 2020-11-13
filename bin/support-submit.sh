#!/bin/bash

source "$(cd "`dirname "$0"`"/..; pwd)"/bin/support-env.sh

CPSP=":"
echo "--------------[Support App Env]--------------"
echo "SUPPORT_HOME: "$SUPPORT_HOME
echo "SUPPORT_CONF_DIR: "$SUPPORT_CONF_DIR
echo "SUPPORT_LIB_DIR: "$SUPPORT_LIB_DIR
echo "SUPPORT_EXTLIB_DIR: "$SUPPORT_EXTLIB_DIR
echo "SUPPORT_PLUGINS_DIR: "$SUPPORT_PLUGINS_DIR
echo "SUPPORT_LOG_DIR: "$SUPPORT_LOG_DIR
echo "---------------------------------------------"


JAVA_RUN="$JAVA_RUN -Dlog.file=${SUPPORT_LOG_DIR}/flink-support.log -Dlog4j.configurationFile=${SUPPORT_CONF_DIR}/log4j-support.properties"
CLASS_PATH=".$CPSP$JAVA_HOME/lib$CPSP$JAVA_HOME/lib/dt.jar$CPSP$JAVA_HOME/lib/tools.jar"


for jar in $SUPPORT_LIB_DIR/*.jar
do
CLASS_PATH="$CLASS_PATH$CPSP$jar"
done

for jar in $SUPPORT_PLUGINS_DIR/*.jar
do
  CLASS_PATH="$CLASS_PATH$CPSP$jar"
done

#echo $CLASS_PATH
CLASS_NAME=com.weiwan.support.launcher.SupportAppClient
echo "Flink Support starting ..."
# shellcheck disable=SC2068
$JAVA_RUN -cp "$CLASS_PATH" $CLASS_NAME $@
#nohup $JAVA_RUN -cp $cp $CLASS_NAME $@ &
echo "Flink Support started ..."


