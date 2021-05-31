package org.ddu.zk.sample;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class CreateGroup implements Watcher {
	private static final int SESSION_TIMEOUT = 5000;
	private ZooKeeper zk;
	private CountDownLatch connectedSignal = new CountDownLatch(1);

	public void connect(String hosts) throws IOException, InterruptedException {
		zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		connectedSignal.await();
	}

	@Override
	public void process(WatchedEvent event) { // Watcher interface
		if (event.getState() == KeeperState.SyncConnected) {
			connectedSignal.countDown();
		}
	}

	public void create(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		String createdPath = zk.create(path, null/* data */, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("Created " + createdPath);
	}

	public void createSequental(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		for (int i=0; i<5; i++) {
			String createdPath = zk.create(path, null/* data */, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			System.out.println("Created " + createdPath);			
		}
	}
	
	public void close() throws InterruptedException {
		zk.close();
	}

	public static void main(String[] args) throws Exception {
		CreateGroup createGroup = new CreateGroup();

		if (args.length > 0) {
			createGroup.connect(args[0]);
			createGroup.create(args[1]);
		} else {
			createGroup.connect(LocalZooKeeperEnv.IP_PORT);
			createGroup.createSequental("seqNode-");
//			createGroup.create(LocalZooKeeperEnv.GROUP);
		}
		createGroup.close();
	}
}
