package com.zk.practise.naixueday01;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 ** 编程思维训练
 * 1、级联查看某节点下所有节点及节点值
 * 2、删除一个节点，不管有有没有任何子节点
 * 3、级联创建任意节点
 * 4、清空子节点
 */
public class ZKHomeWorkImpl implements ZKHomeWork {

    ZooKeeper zk =null;
    /**
     * 创建zk连接
     * @param address
     * @return
     * @throws IOException
     */

    public  ZooKeeper getConnect(String address) throws IOException {

        String connectString = address;
        int sessionTimeOut = 30000;

         zk = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("触发了事件"+event.getState());
            }
        });

        System.out.println("zookeeper connection success!");
        return zk;
    }



    public Map<String, String> getChildNodeAndValue(String path) throws Exception {
        List<String> list = zk.getChildren("/", false);

        Map<String,String> nodeMap = new HashMap<String, String>();
        for (String nodePath : list) {
            byte[] data = zk.getData(nodePath, false, new Stat());
            nodeMap.put(nodePath,new String(data));
        }

        return nodeMap;
    }

    /**
     * ZooKeeper类提供了 delete 方法来删除指定的znode。 delete 方法的签名如下：
     * delete(String path, int version)
     * path - Znode路径。
     * version - znode的当前版本
     *
     * @param path
     * @param zk
     * @return
     * @throws Exception
     */
    public boolean rmr(String path, ZooKeeper zk) throws Exception {

        boolean flag = false;

        //判断节点状态
        Stat stat = zk.exists(path, false);
        if(stat!=null){
            zk.delete(path,stat.getVersion());
            flag = true;
        }else {
            System.out.println("节点不存在");
        }


        return flag;
    }

    /**
     * 其中
     * - path:节点路径
     * - data[]:节点上存放值得字节数组
     * - acl:权限控制,可取值在ZooDefs.Ids中有详细定义
     * - createMode：是否为临时节点,可取值为：PERSISTENT(持久无序)PERSISTENT_SEQUENTIAL(持久有序) EPHEMERAL(临时无序) EPHEMERAL_SEQUENTIAL(临时有序)
     *
     * @param znodePath
     * @param data
     * @param zk
     * @return
     * @throws Exception
     */
    public boolean createZNode(String znodePath, String data, ZooKeeper zk) throws Exception {
        boolean flag = false;
        try {
            zk.create(znodePath, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

    public boolean clearChildNode(String znodePath, ZooKeeper zk) throws Exception {
        boolean flag = false;

        try{
            String[] split = znodePath.split("/");
            for (int i = 2; i < split.length; i++) {
                Stat stat = zk.exists(split[i], false);
                zk.delete(split[i],stat.getVersion());
            }
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }


        return flag;
    }
}
