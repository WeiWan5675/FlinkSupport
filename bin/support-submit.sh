#!/bin/bash

source "$(cd "`dirname "$0"`"/..; pwd)"/bin/support-env.sh


echo "--------------[Support App Env]--------------"
echo "SUPPORT_HOME$CPSP "$SUPPORT_HOME
echo "SUPPORT_CONF_DIR$CPSP "$SUPPORT_CONF_DIR
echo "SUPPORT_LIB_DIR$CPSP "$SUPPORT_LIB_DIR
echo "SUPPORT_EXTLIB_DIR$CPSP "$SUPPORT_EXTLIB_DIR
echo "SUPPORT_PLUGINS_DIR$CPSP "$SUPPORT_PLUGINS_DIR
echo "---------------------------------------------"

CPSP=":"


JAVA_RUN="$JAVA_RUN -Dlog4j.configurationFile=""${SUPPORT_CONF_DIR}/log4j.properties"
CLASS_PATH=".$CPSP$JAVA_HOME/lib$CPSP$JAVA_HOME/lib/dt.jar$CPSP$JAVA_HOME/lib/tools.jar"


for jar in $SUPPORT_LIB_DIR/*.jar
do
CLASS_PATH="$CLASS_PATH$CPSP$jar"
done

for jar in $SUPPORT_PLUGINS_DIR/*.jar
do
  CLASS_PATH="$CLASS_PATH$CPSP$jar"
done


echo $CLASS_PATH

CLASS_NAME=com.weiwan.support.launcher.SupportAppClient

echo "Flink Support starting ..."
# shellcheck disable=SC2068
$JAVA_RUN -cp "$CLASS_PATH" $CLASS_NAME $@
#nohup $JAVA_RUN -cp $cp $CLASS_NAME $@ &
echo "Flink Support started ..."


