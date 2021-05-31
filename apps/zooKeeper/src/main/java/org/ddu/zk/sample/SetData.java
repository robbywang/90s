package org.ddu.zk.sample;

import org.apache.zookeeper.KeeperException;

public class SetData extends ConnectionWatcher {
	public void setData(String groupName, String memberName) throws KeeperException, InterruptedException {
		String path = "/" + groupName + "/" + memberName;
		byte[] oldData = zk.getData(path, false, null);
		String oldDataStr = new String(oldData);
		String newDataStr = oldDataStr + "1";
		System.out.println("old data: " + oldDataStr);
		zk.setData(path, newDataStr.getBytes(), -1);
	}

	public static void main(String[] args) throws Exception {
		SetData joinGroup = new SetData();
		if (args.length > 0) {
			joinGroup.connect(args[0]);
			joinGroup.setData(args[1], args[2]);			
		} else {
			joinGroup.connect(LocalZooKeeperEnv.IP_PORT);
			joinGroup.setData(LocalZooKeeperEnv.GROUP, LocalZooKeeperEnv.NODE);
		}
	}
}
