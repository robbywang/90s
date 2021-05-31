1. Add log4j.properties to install/conf directory.



--- Spark startup log ---

/Users/robby/Applications/spark-1.6.1-bin-hadoop2.6/conf
localhost:conf robby$ spark-shell
16/06/27 15:12:57 WARN NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
16/06/27 15:12:57 INFO SecurityManager: Changing view acls to: robby
16/06/27 15:12:57 INFO SecurityManager: Changing modify acls to: robby
16/06/27 15:12:57 INFO SecurityManager: SecurityManager: authentication disabled; ui acls disabled; users with view permissions: Set(robby); users with modify permissions: Set(robby)
16/06/27 15:12:57 INFO HttpServer: Starting HTTP Server
16/06/27 15:12:57 INFO Utils: Successfully started service 'HTTP class server' on port 51970.
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 1.6.1
      /_/

Using Scala version 2.10.5 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_91)
Type in expressions to have them evaluated.
Type :help for more information.
16/06/27 15:13:01 WARN Utils: Your hostname, localhost resolves to a loopback address: 127.0.0.1; using 172.16.54.248 instead (on interface en0)
16/06/27 15:13:01 WARN Utils: Set SPARK_LOCAL_IP if you need to bind to another address
16/06/27 15:13:01 INFO SparkContext: Running Spark version 1.6.1
16/06/27 15:13:01 INFO SecurityManager: Changing view acls to: robby
16/06/27 15:13:01 INFO SecurityManager: Changing modify acls to: robby
16/06/27 15:13:01 INFO SecurityManager: SecurityManager: authentication disabled; ui acls disabled; users with view permissions: Set(robby); users with modify permissions: Set(robby)
16/06/27 15:13:01 INFO Utils: Successfully started service 'sparkDriver' on port 51971.
16/06/27 15:13:01 INFO Slf4jLogger: Slf4jLogger started
16/06/27 15:13:01 INFO Remoting: Starting remoting
16/06/27 15:13:01 INFO Remoting: Remoting started; listening on addresses :[akka.tcp://sparkDriverActorSystem@172.16.54.248:51972]
16/06/27 15:13:02 INFO Utils: Successfully started service 'sparkDriverActorSystem' on port 51972.
16/06/27 15:13:02 INFO SparkEnv: Registering MapOutputTracker
16/06/27 15:13:02 INFO SparkEnv: Registering BlockManagerMaster
16/06/27 15:13:02 INFO DiskBlockManager: Created local directory at /private/var/folders/73/yv8l5gpj1x3f8kshdgs0ph9c0000gn/T/blockmgr-5be496a5-795c-4e53-b2f9-e1c90759eceb
16/06/27 15:13:02 INFO MemoryStore: MemoryStore started with capacity 511.1 MB
16/06/27 15:13:02 INFO SparkEnv: Registering OutputCommitCoordinator
16/06/27 15:13:02 INFO Utils: Successfully started service 'SparkUI' on port 4040.
16/06/27 15:13:02 INFO SparkUI: Started SparkUI at http://172.16.54.248:4040
16/06/27 15:13:02 INFO Executor: Starting executor ID driver on host localhost
16/06/27 15:13:02 INFO Executor: Using REPL class URI: http://172.16.54.248:51970
16/06/27 15:13:02 INFO Utils: Successfully started service 'org.apache.spark.network.netty.NettyBlockTransferService' on port 51973.
16/06/27 15:13:02 INFO NettyBlockTransferService: Server created on 51973
16/06/27 15:13:02 INFO BlockManagerMaster: Trying to register BlockManager
16/06/27 15:13:02 INFO BlockManagerMasterEndpoint: Registering block manager localhost:51973 with 511.1 MB RAM, BlockManagerId(driver, localhost, 51973)
16/06/27 15:13:02 INFO BlockManagerMaster: Registered BlockManager
16/06/27 15:13:02 INFO SparkILoop: Created spark context..
Spark context available as sc.
16/06/27 15:13:03 INFO HiveContext: Initializing execution hive, version 1.2.1
16/06/27 15:13:03 INFO ClientWrapper: Inspected Hadoop version: 2.6.0
16/06/27 15:13:03 INFO ClientWrapper: Loaded org.apache.hadoop.hive.shims.Hadoop23Shims for Hadoop version 2.6.0
16/06/27 15:13:03 INFO HiveMetaStore: 0: Opening raw store with implemenation class:org.apache.hadoop.hive.metastore.ObjectStore
16/06/27 15:13:04 INFO ObjectStore: ObjectStore, initialize called
16/06/27 15:13:04 INFO Persistence: Property hive.metastore.integral.jdo.pushdown unknown - will be ignored
16/06/27 15:13:04 INFO Persistence: Property datanucleus.cache.level2 unknown - will be ignored
16/06/27 15:13:04 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
16/06/27 15:13:04 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
16/06/27 15:13:05 INFO ObjectStore: Setting MetaStore object pin classes with hive.metastore.cache.pinobjtypes="Table,StorageDescriptor,SerDeInfo,Partition,Database,Type,FieldSchema,Order"
16/06/27 15:13:06 INFO Datastore: The class "org.apache.hadoop.hive.metastore.model.MFieldSchema" is tagged as "embedded-only" so does not have its own datastore table.
16/06/27 15:13:06 INFO Datastore: The class "org.apache.hadoop.hive.metastore.model.MOrder" is tagged as "embedded-only" so does not have its own datastore table.
16/06/27 15:13:07 INFO Datastore: The class "org.apache.hadoop.hive.metastore.model.MFieldSchema" is tagged as "embedded-only" so does not have its own datastore table.
16/06/27 15:13:07 INFO Datastore: The class "org.apache.hadoop.hive.metastore.model.MOrder" is tagged as "embedded-only" so does not have its own datastore table.
16/06/27 15:13:08 INFO MetaStoreDirectSql: Using direct SQL, underlying DB is DERBY
16/06/27 15:13:08 INFO ObjectStore: Initialized ObjectStore
16/06/27 15:13:08 WARN ObjectStore: Version information not found in metastore. hive.metastore.schema.verification is not enabled so recording the schema version 1.2.0
16/06/27 15:13:08 WARN ObjectStore: Failed to get database default, returning NoSuchObjectException
16/06/27 15:13:08 INFO HiveMetaStore: Added admin role in metastore
16/06/27 15:13:08 INFO HiveMetaStore: Added public role in metastore
16/06/27 15:13:08 INFO HiveMetaStore: No user is added in admin role, since config is empty
16/06/27 15:13:08 INFO HiveMetaStore: 0: get_all_databases
16/06/27 15:13:08 INFO audit: ugi=robby	ip=unknown-ip-addr	cmd=get_all_databases
16/06/27 15:13:08 INFO HiveMetaStore: 0: get_functions: db=default pat=*
16/06/27 15:13:08 INFO audit: ugi=robby	ip=unknown-ip-addr	cmd=get_functions: db=default pat=*
16/06/27 15:13:08 INFO Datastore: The class "org.apache.hadoop.hive.metastore.model.MResourceUri" is tagged as "embedded-only" so does not have its own datastore table.
16/06/27 15:13:09 INFO SessionState: Created local directory: /var/folders/73/yv8l5gpj1x3f8kshdgs0ph9c0000gn/T/7bb64c05-449f-4cd1-9b23-35b2d63043b3_resources
16/06/27 15:13:09 INFO SessionState: Created HDFS directory: /tmp/hive/robby/7bb64c05-449f-4cd1-9b23-35b2d63043b3
16/06/27 15:13:09 INFO SessionState: Created local directory: /var/folders/73/yv8l5gpj1x3f8kshdgs0ph9c0000gn/T/robby/7bb64c05-449f-4cd1-9b23-35b2d63043b3
16/06/27 15:13:09 INFO SessionState: Created HDFS directory: /tmp/hive/robby/7bb64c05-449f-4cd1-9b23-35b2d63043b3/_tmp_space.db
16/06/27 15:13:09 INFO HiveContext: default warehouse location is /user/hive/warehouse
16/06/27 15:13:09 INFO HiveContext: Initializing HiveMetastoreConnection version 1.2.1 using Spark classes.
16/06/27 15:13:09 INFO ClientWrapper: Inspected Hadoop version: 2.6.0
16/06/27 15:13:09 INFO ClientWrapper: Loaded org.apache.hadoop.hive.shims.Hadoop23Shims for Hadoop version 2.6.0
16/06/27 15:13:09 INFO HiveMetaStore: 0: Opening raw store with implemenation class:org.apache.hadoop.hive.metastore.ObjectStore
16/06/27 15:13:09 INFO ObjectStore: ObjectStore, initialize called
16/06/27 15:13:09 INFO Persistence: Property hive.metastore.integral.jdo.pushdown unknown - will be ignored
16/06/27 15:13:09 INFO Persistence: Property datanucleus.cache.level2 unknown - will be ignored
16/06/27 15:13:09 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
16/06/27 15:13:10 WARN Connection: BoneCP specified but not present in CLASSPATH (or one of dependencies)
16/06/27 15:13:11 INFO ObjectStore: Setting MetaStore object pin classes with hive.metastore.cache.pinobjtypes="Table,StorageDescriptor,SerDeInfo,Partition,Database,Type,FieldSchema,Order"
16/06/27 15:13:12 INFO Datastore: The class "org.apache.hadoop.hive.metastore.model.MFieldSchema" is tagged as "embedded-only" so does not have its own datastore table.
16/06/27 15:13:12 INFO Datastore: The class "org.apache.hadoop.hive.metastore.model.MOrder" is tagged as "embedded-only" so does not have its own datastore table.
16/06/27 15:13:13 INFO Datastore: The class "org.apache.hadoop.hive.metastore.model.MFieldSchema" is tagged as "embedded-only" so does not have its own datastore table.
16/06/27 15:13:13 INFO Datastore: The class "org.apache.hadoop.hive.metastore.model.MOrder" is tagged as "embedded-only" so does not have its own datastore table.
16/06/27 15:13:13 INFO MetaStoreDirectSql: Using direct SQL, underlying DB is DERBY
16/06/27 15:13:13 INFO ObjectStore: Initialized ObjectStore
16/06/27 15:13:13 WARN ObjectStore: Version information not found in metastore. hive.metastore.schema.verification is not enabled so recording the schema version 1.2.0
16/06/27 15:13:13 WARN ObjectStore: Failed to get database default, returning NoSuchObjectException
16/06/27 15:13:13 INFO HiveMetaStore: Added admin role in metastore
16/06/27 15:13:13 INFO HiveMetaStore: Added public role in metastore
16/06/27 15:13:13 INFO HiveMetaStore: No user is added in admin role, since config is empty
16/06/27 15:13:14 INFO HiveMetaStore: 0: get_all_databases
16/06/27 15:13:14 INFO audit: ugi=robby	ip=unknown-ip-addr	cmd=get_all_databases
16/06/27 15:13:14 INFO HiveMetaStore: 0: get_functions: db=default pat=*
16/06/27 15:13:14 INFO audit: ugi=robby	ip=unknown-ip-addr	cmd=get_functions: db=default pat=*
16/06/27 15:13:14 INFO Datastore: The class "org.apache.hadoop.hive.metastore.model.MResourceUri" is tagged as "embedded-only" so does not have its own datastore table.
16/06/27 15:13:14 INFO SessionState: Created local directory: /var/folders/73/yv8l5gpj1x3f8kshdgs0ph9c0000gn/T/5832dc67-fa3e-499f-8406-430e8faca8c0_resources
16/06/27 15:13:14 INFO SessionState: Created HDFS directory: /tmp/hive/robby/5832dc67-fa3e-499f-8406-430e8faca8c0
16/06/27 15:13:14 INFO SessionState: Created local directory: /var/folders/73/yv8l5gpj1x3f8kshdgs0ph9c0000gn/T/robby/5832dc67-fa3e-499f-8406-430e8faca8c0
16/06/27 15:13:14 INFO SessionState: Created HDFS directory: /tmp/hive/robby/5832dc67-fa3e-499f-8406-430e8faca8c0/_tmp_space.db
16/06/27 15:13:14 INFO SparkILoop: Created sql context (with Hive support)..
SQL context available as sqlContext.