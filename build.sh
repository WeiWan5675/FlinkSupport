#!/bin/bash
MVN_HOME=${M2_HOME}
echo "MavenHome:"$MVN_HOME
$MVN_HOME/bin/mvn clean install -Dmaven.test.skip=true


ll