#!/bin/bash

# [ ] 实际上是bash 中 test 命令的简写。即所有的 [ expr ] 等于 test expr
SCRIPT=$0
echo script name : $SCRIPT
PARAM=$1
echo input param: $PARAM

if [[ $PARAM == 0 && $2 == 1 ]];
	then
	echo "[[ $PARAM == 0 ]]"
fi

if [ $PARAM == 0 ];
	then
	echo "[ $PARAM == 0 ]"
fi

if [[ $PARAM -eq 0 ]];
	then
	echo "[[ $PARAM -eq 0 ]]"
fi

if [ $PARAM -eq 0 ];
	then
	echo "[ $PARAM -eq 0 ]"
fi