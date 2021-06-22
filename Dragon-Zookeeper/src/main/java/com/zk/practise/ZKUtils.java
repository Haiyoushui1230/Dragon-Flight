package com.zk.practise;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class ZKUtils {

    /**
     * 创建zk连接
     * @param address
     * @return
     * @throws IOException
     */
    public static ZooKeeper getConnect(String address) throws IOException {

        String connectString = address;
        int sessionTimeOut = 30000;

        ZooKeeper zk = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("触发了事件"+event.getState());
            }
        });

        System.out.println("zookeeper connection success!");
        return zk;
    }


    public void operater() throws IOException {

        ZooKeeper zooKeeper = ZKUtils.getConnect("127.0.0.1:2181");
        /**
         * 2.创建节点
         */
        try {

            if(zooKeeper != null) {
                // 1.1.创建zookeeper的znode
                System.out.println(zooKeeper.create("/test", "test node".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL));
                // 1.2.重复创建节点
                System.out.println(zooKeeper.create("/test", "test node".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL));
                // 1.3.创建一个父节点不存在的子节点
                System.out.println(zooKeeper.create("/father/son", "no father".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL));
                // 1.4.创建一个序列节点(持久化节点)
                System.out.println(zooKeeper.create("/seq_node", "sequential".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL));
                // 1.5.创建一个只读节点
                System.out.println(zooKeeper.create("/ro_node", "read only node".getBytes(), ZooDefs.Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT));
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        /**
         * 3.获取节点信息
         */
        try {
            // 2.1获取子节点
            List<String> nodeList = zooKeeper.getChildren("/", false);
            for (String nodeName : nodeList) {
                System.out.println(nodeName);
            }
            // 2.2.获取节点权限信息
            List<ACL> aclList = zooKeeper.getACL("/ro_node", new Stat());
            for (ACL acl : aclList) {
                System.out.println(acl);
            }
            // 2.3.查看节点数据
            byte[] data = zooKeeper.getData("/ro_node", false, new Stat());
            System.out.println(new String(data));
            // 2.4.获取客户端状态
            ZooKeeper.States states = zooKeeper.getState();
            System.out.println("zookeeper states:" + states);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 4.删除节点
         */
        try {
            // 删除前先获取节点的数据结构
            Stat stat = zooKeeper.exists("/seq_node0000000011", false);
            if(stat != null) {
                // 3.1.version = -1 是删除所有版本的节点
                zooKeeper.delete("/seq_node0000000011", stat.getVersion());
                // 3.2.删除一个含有子节点的节点（KeeperErrorCode = Directory not empty for /app）
                zooKeeper.delete("/app", -1);
            } else {
                System.out.println("节点不存在！");
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 5.修改节点
         */
        try {
            zooKeeper.setData("/app", "app update".getBytes(), -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 6.权限认证
         */
        try {

            zooKeeper.addAuthInfo("digest", "wu".getBytes());
            // 创建带权限验证的节点
            zooKeeper.create("/auth_node", "auth node".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
            // 其他客户端获取该节点（KeeperErrorCode = NoAuth for /auth_node）
            zooKeeper = ZKUtils.getConnect("127.0.0.1:2181");
            zooKeeper.getData("/auth_node", false, null);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
