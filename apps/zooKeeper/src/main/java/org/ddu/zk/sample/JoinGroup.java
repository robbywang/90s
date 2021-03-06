package org.ddu.zk.sample;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

public class JoinGroup extends ConnectionWatcher {
	public void join(String groupName, String memberName) throws KeeperException, InterruptedException {
		String path = "/" + groupName + "/" + memberName;
		byte[] data = "zookeeper".getBytes();
		String createdPath = zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("Created " + createdPath);
	}

	public static void main(String[] args) throws Exception {
		JoinGroup joinGroup = new JoinGroup();
		if (args.length > 0) {
			joinGroup.connect(args[0]);
			joinGroup.join(args[1], args[2]);			
		} else {
			joinGroup.connect(LocalZooKeeperEnv.IP_PORT);
			joinGroup.join(LocalZooKeeperEnv.GROUP, LocalZooKeeperEnv.NODE);
		}

		// stay alive until process is killed or thread is interrupted
//		Thread.sleep(Long.MAX_VALUE);
	}
}
