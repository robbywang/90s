#!/bin/bash

# default value.
APP_JAR=target/ddu-spark.jar
MAIN_CLASS=ddu.spark.rdd.ScalaWordCount

DEPLOY_MODE=client
MASTER=local[1]

NUM_EXECUTORS=1
EXECUTOR_CORES=1
DRIVER_MEMORY=512m
EXECUTOR_MEMORY=512m

# spark-submit
SPARK_HOME=${SPARK_HOME}
SPARK_SUBMIT=${SPARK_HOME}/bin/spark-submit

# main class args.
ARG_ARRAY=()

function usage() {
  echo "========== Usage: $0 -class <class> [args] =========="
  echo "    -class           Main class "
  echo "    -master          集群类型 [local，yarn]， 默认local[1]"
  echo "    -deploy-mode     部署方式 [client, cluster]"
  echo "    -num-executors   默认1"
  echo "    -executor-cores  默认1"
  echo "    -driver-memory   默认512m"
  echo "    -executor-memory 默认512m"
}

# get arguments
while true; do
  if [[ $# == 0 ]]; then
    break
  fi
  case "$1" in
    (-h)
      usage
      exit
      ;;
    (-class)
      MAIN_CLASS="$2"
      shift 2
      ;;
    (-master)
      MASTER="$2"
      shift 2
      ;;
    (-deploy-mode)
      DEPLOY_MODE="$2"
      shift 2
      ;;
    (-num-executors)
      NUM_EXECUTORS="$2"
      shift 2
      ;;
    (-executor-cores)
      EXECUTOR_CORES="$2"
      shift 2
      ;;
    (-driver-memory)
      DRIVER_MEMORY="$2"
      shift 2
      ;;
    (-executor-memory)
      EXECUTOR_MEMORY="$2"
      shift 2
      ;;
    (*)
    # main class 参数
      ARG_ARRAY=(${ARG_ARRAY[@]} "$1")
      shift 1
  esac
done

SPARK_SUBMIT_CMD="${SPARK_SUBMIT} \
    --class ${MAIN_CLASS} \
    --master ${MASTER} \
    --deploy-mode ${DEPLOY_MODE} \
    --driver-memory ${DRIVER_MEMORY} \
    --executor-memory ${EXECUTOR_MEMORY} \
    --executor-cores ${EXECUTOR_CORES} \
    --num-executors ${NUM_EXECUTORS} \
    ${APP_JAR} \
    ${ARG_ARRAY[@]}"

echo " ---  spark submit details ---"
echo ${SPARK_SUBMIT_CMD}
echo " ---  spark submit details ---"

${SPARK_SUBMIT_CMD}

