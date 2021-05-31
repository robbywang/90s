package org.ddu.zk;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.ddu.zk.sample.LocalZooKeeperEnv;

public class MyZooKeeperTest implements Watcher, StatCallback {

	private static final int SESSION_TIMEOUT = 5000;
	protected ZooKeeper zk;
	private CountDownLatch connectedSignal = new CountDownLatch(1);

	public void connect(String hosts) throws IOException, InterruptedException {
		zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		connectedSignal.await();
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println(event.toString());
		if (event.getState() == KeeperState.SyncConnected) {
			connectedSignal.countDown();
		}
	}

	@Override
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		System.out.println("ZooKeeper StatCallback.");
	}

	public void close() throws InterruptedException {
		zk.close();
	}

	public static void main(String[] args) throws Exception {
		MyZooKeeperTest test = new MyZooKeeperTest();
		if (args.length > 0) {
			test.connect(args[0]);
		} else {
			test.connect(LocalZooKeeperEnv.IP_PORT);
			String group = "/" + LocalZooKeeperEnv.GROUP;

			Stat stat = test.syncExists(group);
			if (stat == null) {
				System.out.println("znode does not exist: " + group);
				test.create(LocalZooKeeperEnv.GROUP);
			} else {
				// use async method register StatCallback.
				test.asyncExists(group);
				test.delete(LocalZooKeeperEnv.GROUP);
			}
		}
	}

	public void create(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		String createdPath = zk.create(path, null/* data */, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("Created " + createdPath);
	}

	public void asyncExists(String znode) throws KeeperException, InterruptedException {
		zk.exists(znode, true, new StatCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx, Stat stat) {
				System.out.println("asyncExists StatCallback, path: " + path + " stat : " + stat.toString());
			}
		}, null);
	}

	public Stat syncExists(String znode) throws KeeperException, InterruptedException {
		Stat stat = zk.exists(znode, true);
		return stat;
	}

	public void delete(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		try {
			List<String> children = zk.getChildren(path, false);
			for (String child : children) {
				zk.delete(path + "/" + child, -1);
			}
			zk.delete(path, -1);
		} catch (KeeperException.NoNodeException e) {
			System.out.printf("Group %s does not exist\n", groupName);
		}
	}

	public void setData(String groupName, String memberName) throws KeeperException, InterruptedException {
		String path = "/" + groupName + "/" + memberName;
		byte[] oldData = zk.getData(path, false, null);
		String oldDataStr = new String(oldData);
		String newDataStr = oldDataStr + "1";
		System.out.println("old data: " + oldDataStr);
		zk.setData(path, newDataStr.getBytes(), -1);
	}

}
