# conf/zoo.cfg
tickTime=2000
dataDir=/Users/tom/zookeeper
clientPort=2181

# Connecting to ZooKeeper 
bin/zkCli.sh -server 127.0.0.1:2181

# List znode
% zkCli.sh -server localhost ls /zoo