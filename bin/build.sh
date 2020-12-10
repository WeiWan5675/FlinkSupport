#!/bin/bash


source /etc/profile
source "$(cd "`dirname "$0"`"/..; pwd)"/bin/support-env
MAVEN_HOME=$M2_HOME

$MAVEN_HOME/bin/mvn clean package install -Dmaven.test.skip=true -f $1


exit $?