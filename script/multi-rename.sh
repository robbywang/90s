#!/bin/bash

# 批量文件改名
cd /Users/taowang/IBM/workspace_zcee/com.ibm.zosconnect.endpoint.connection/publish/features/l10n
for file in `ls`
do
  echo $file
  newfile=`echo $file | sed 's/serviceRestClient-2.0/endpoint.connection-1.0/'`
  echo $newfile
　`mv $file $newfile`
done