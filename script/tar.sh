#!/bin/bash
#
# Traverse all current directories and compress
#

target_dir=/home/hadoop/offline-etl/logs/tenant/shuce/2017-11            

for file in $(find $target_dir -type f) 
do    
     if [ ${file##*.} = gz ] 
            then 
            echo "${file##*/} ......was tared pass!"
     else 
            cd ${file%/*} 
            tar zcvf ${file##*/}.tar.gz ${file##*/} 
            echo "${file##*/} ......is tared ok!"
            rm -rf ${file##*/} 
     fi 
done

