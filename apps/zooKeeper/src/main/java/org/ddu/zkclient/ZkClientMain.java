package org.ddu.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class ZkClientMain {

	public static void main(String[] args) {
		//ZkClient通过内部封装，将zookeeper异步的会话过程同步化了
		 ZkClient zkClient = new ZkClient("localhost:2181",5000);
		 System.out.println("ZooKeeper session established.");
		 
		//ZKClient可以递归创建父节点
		 zkClient.createPersistent("/robby/zkClient", true);
		 }
	}