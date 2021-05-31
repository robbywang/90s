# -XX:+UseParallelOldGC 
# -XX:-UseConcMarkSweepGC
VM_OPT="-server -XX:+PrintGCDetails -XX:-UseConcMarkSweepGC -Xmx5M -Xms5M -Xloggc:target/gc.log"

echo ${VM_OPT}
# VM 参数放在前面才生效
java ${VM_OPT} -cp target/core-java-1.0.0-SNAPSHOT.jar org.ddu.java.PrintGCDetails



# CommandLine flags: -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelOldGC
# Heap
#  PSYoungGen      total 2560K, used 979K [0x00000007bfd00000, 0x00000007c0000000, 0x00000007c0000000)
#   eden space 2048K, 47% used [0x00000007bfd00000,0x00000007bfdf4ca8,0x00000007bff00000)
#   from space 512K, 0% used [0x00000007bff80000,0x00000007bff80000,0x00000007c0000000)
#   to   space 512K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007bff80000)
#  ParOldGen       total 7168K, used 0K [0x00000007bf600000, 0x00000007bfd00000, 0x00000007bfd00000)
#   object space 7168K, 0% used [0x00000007bf600000,0x00000007bf600000,0x00000007bfd00000)
#  Metaspace       used 2738K, capacity 4486K, committed 4864K, reserved 1056768K
#   class space    used 294K, capacity 386K, committed 512K, reserved 1048576K