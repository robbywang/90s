#!/bin/bash

# 1. 把不包含usage的行删除掉
# 2. 替换=号前面的为空串
# 3. 去掉回车符号
# -> test-usage
USAGE=`sed '/usage/!d;s/.*=//' sed-test.txt | tr -d '\r'`
echo $USAGE