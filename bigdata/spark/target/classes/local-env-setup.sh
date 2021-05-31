#!/bin/bash
export INSTALL_DIR=/Users/taowang/Robby
export HADOOP_DIR=${INSTALL_DIR}/hadoop-2.7.3
export SPARK_DIR=${INSTALL_DIR}/spark-2.0.2-bin-hadoop2.7
export PATH=$PATH:${HADOOP_DIR}/bin:${HADOOP_DIR}/sbin
export HADOOP_CONF_DIR=${HADOOP_DIR}/etc/hadoop
export JAVA_HOME=`/usr/libexec/java_home`

echo "------ Environment setup --------"
echo HADOOP_CONF_DIR : ${HADOOP_CONF_DIR}
echo PATH : $PATH
echo SPARK_DIR : ${SPARK_DIR}
echo HADOOP_CONF_DIR : ${HADOOP_CONF_DIR}
echo JAVA_HOME : ${JAVA_HOME} 
echo "------ Environment setup --------"

#if (( $# == 0 ));then
#  echo -e "parameter error.\nUsage:sh setup-env.sh <start|start-yarn|start-hdfs|stop|stop-hdfs|stop-yarn|format |start-master|start-slave>"
#  exit 1
#fi

function ft-hdfs() {
  ${HADOOP_DIR}/bin/hdfs namenode -format 
}

function start-hdfs() {
  # namenode: http://localhost:50070/
  # equal to following 2 comamnds:
  #   sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs start namenode
  #   sbin/hadoop-daemons.sh --config $HADOOP_CONF_DIR --script hdfs start datanode
  # This script check etc/hadoop/slaves and start slave on each node.
  # ${HADOOP_DIR}/bin/hdfs getconf -namenodes 
  ${HADOOP_DIR}/sbin/start-dfs.sh
}

function start-yarn() {
  # ResourceManager - http://localhost:8088/
  # equal to following 3 comamnds:
  #   sbin/yarn-daemon.sh --config $HADOOP_CONF_DIR start resourcemanager
  #   sbin/yarn-daemons.sh --config $HADOOP_CONF_DIR start nodemanager
  #   sbin/yarn-daemon.sh --config $HADOOP_CONF_DIR start proxyserver
  # This script check etc/hadoop/slaves and start slave on each node.  
  ${HADOOP_DIR}/sbin/start-yarn.sh   
}

function start-master() { 
  ${SPARK_DIR}/sbin/start-master.sh -h 9.181.91.120 -p 9990
  #${SPARK_DIR}/sbin/start-master.sh -h localhost -p 9990
}

function start-slave() { 
  ${SPARK_DIR}/sbin/start-slave.sh spark://9.181.91.120:9990
  #${SPARK_DIR}/sbin/start-slave.sh spark://localhost:9990
}

function start() {
  start-hdfs
  start-yarn
}

function stop-hdfs() {
  ${HADOOP_DIR}/sbin/stop-dfs.sh   
}

function stop-yarn() {        
  ${HADOOP_DIR}/sbin/stop-yarn.sh   
}

function stop() {
  stop-hdfs
  stop-yarn
}

function scopy() {
  scp ${HADOOP_DIR}/etc/hadoop/$2 hadoop@192.168.1.103:/Users/hadoop/hadoop-2.7.3/etc/hadoop/
}

############## main #################
if [[ "start" == $1 ]];then
 start
elif [[ "start-hdfs" == $1 ]];then
 start-hdfs
elif [[ "start-yarn" == $1 ]];then
 start-yarn
elif [[ "stop" == $1 ]];then
 stop
elif [[ "stop-hdfs" == $1 ]];then
 stop-hdfs
elif [[ "stop-yarn" == $1 ]];then
 stop-yarn 
elif [[ "format" == $1 ]];then
 ft-hdfs
elif [[ "start-master" == $1 ]];then
 start-master
elif [[ "start-slave" == $1 ]];then
 start-slave 
elif [[ "scopy" == $1 ]];then
 scopy
fi
