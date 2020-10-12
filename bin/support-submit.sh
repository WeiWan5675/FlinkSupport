#!/bin/bash

source "$(cd "`dirname "$0"`"/..; pwd)"/bin/support-env.sh


echo "--------------[Support App Env]--------------"
echo "SUPPORT_HOME: "$SUPPORT_HOME
echo "SUPPORT_CONF_DIR: "$SUPPORT_CONF_DIR
echo "SUPPORT_LIB_DIR: "$SUPPORT_LIB_DIR
echo "SUPPORT_EXTLIB_DIR: "$SUPPORT_EXTLIB_DIR
echo "SUPPORT_PLUGINS_DIR: "$SUPPORT_PLUGINS_DIR
echo "---------------------------------------------"


JAVA_RUN=$JAVA_RUN" -Dlogback.configurationFile="${ARGUS_HOME/conf/logback.xml}""
CLASS_PATH=".:$JAVA_HOME/lib:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar"


for jar in $SUPPORT_LIB_DIR/*.jar
do
CLASS_PATH=$CLASS_PATH:$jar
done

for jar in $SUPPORT_PLUGINS_DIR/*.jar
do
  CLASS_PATH=$CLASS_PATH:$jar
done
for jar in $SUPPORT_EXTLIB_DIR/*.jar
do
  CLASS_PATH=$CLASS_PATH:$jar
done

echo $CLASS_PATH

CLASS_NAME=com.weiwan.support.launcher.SupportAppClient

echo "Flink Argus starting ..."
$JAVA_RUN -cp $CLASS_PATH $CLASS_NAME $@
#nohup $JAVA_RUN -cp $cp $CLASS_NAME $@ &
echo "Flink Argus started ..."
