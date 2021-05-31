#!//bin/bash

###################################################
#$1=catalog scheduler transform jenkins ui sql all#
###################################################

Test_HOME=/home/hadoop

if (( $# == 0 ));then
  echo -e "parameter error.\nUsage:sh updateWebapps.sh <all|sql|catalog|scheduler|transform|ui>"
  exit 1
fi


function catalog(){
 cd $Test_HOME
 rm -rf Test/webapps/catalog.war
 /bin/wget -P ./Test/webapps/ http://10.10.32.50:8080/view/Test/job/TestDailyDevelopmentTest/lastSuccessfulBuild/artifact/catalog/target/catalog.war
}

function scheduler(){
 cd $Test_HOME
 rm -rf Test/webapps/scheduler.war
 /bin/wget -P ./Test/webapps/ http://10.10.32.50:8080/view/Test/job/TestDailyDevelopmentTest/lastSuccessfulBuild/artifact/factory/scheduler/target/scheduler.war
}

function transform(){
 cd $Test_HOME
 rm -rf Test/webapps/transform.war
 /bin/wget -P ./Test/webapps/ http://10.10.32.50:8080/view/Test/job/TestDailyDevelopmentTest/lastSuccessfulBuild/artifact/factory/transform/target/transform.war
}

function ui(){
 cd $Test_HOME
 rm -rf Test/webapps/ui.war
 /bin/wget -P ./Test/webapps/ http://10.10.32.50:8080/view/Test/job/TestDailyDevelopmentTest/lastSuccessfulBuild/artifact/ui/target/ui.war
}

function sql(){
 cd $Test_HOME
 rm -rf Test/webapps/Test.sql
 /bin/wget -P ./Test/webapps/ http://10.10.32.50:8080/view/Test/job/TestDailyBuild/lastSuccessfulBuild/artifact/init/sql/target/Test.sql
}

function jenkins(){
 cd $Test_HOME
 rm -rf Test/webapps/jenkins.war
 /bin/wget -P ./Test/webapps/ http://10.10.32.50:8080/view/Test/job/TestDailyBuild/lastSuccessfulBuild/artifact/factory/jenkins/target/jenkins.war
}

function xml(){
 cd $Test_HOME
 rm -rf Test/webapps/*.xml
 /bin/wget -P ./Test/webapps/ http://10.10.32.50:8080/view/Test/job/TestDailyBuild/lastSuccessfulBuild/artifact/factory/jenkins/resource/jenkins.xml
 /bin/wget -P ./Test/webapps/ http://10.10.32.50:8080/view/Test/job/TestDailyBuild/lastSuccessfulBuild/artifact/catalog/src/main/resources/catalog.xml
 /bin/wget -P ./Test/webapps/ http://10.10.32.50:8080/view/Test/job/TestDailyBuild/lastSuccessfulBuild/artifact/factory/scheduler/src/main/resources/scheduler.xml
 /bin/wget -P ./Test/webapps/ http://10.10.32.50:8080/view/Test/job/TestDailyBuild/lastSuccessfulBuild/artifact/factory/transform/src/main/resources/transform.xml
 /bin/wget -P ./Test/webapps/ http://10.10.32.50:8080/view/Test/job/TestDailyBuild/lastSuccessfulBuild/artifact/ui/src/main/resources/ui.xml
}

function serviceRestart(){
  cd $Test_HOME
  /bin/bash ./Test/bin/jetty.sh restart

}

function syncsql(){
 USER=Test
 PASSWORD=Test@talkingdata
 DATABASE=Test

 OUT_PATH=/usr/local/Test/webapps/Test.sql
 /bin/mysql3306 -u$USER -p$PASSWORD -D$DATABASE —default-character-set=utf8 < $OUT_PATH

 if (( $? == 0 ));then
   echo "execute sql has succeed."
 else
   echo "execute sql has error."
 fi
}

function all(){
 catalog
 scheduler
 transform
 ui
 jenkins
 xml
}


##############main#################
if [[ "sql" == $1 ]];then
 sql
 syncsql
elif [[ "all" == $1 ]];then
 all
elif [[ "catalog" == $1 ]];then
 catalog
elif [[ "scheduler" == $1 ]];then
 scheduler
elif [[ "transform" == $1 ]];then
 transform
elif [[ "ui" == $1 ]];then
 ui
fi

serviceRestart