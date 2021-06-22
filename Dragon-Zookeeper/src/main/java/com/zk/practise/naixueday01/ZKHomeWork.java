package com.zk.practise.naixueday01;

import org.apache.zookeeper.ZooKeeper;

import java.util.Map;

/*** 作者： 马中华：http://blog.csdn.net/zhongqi2513
 * 日期： 2017年4月9日 下午11:39:10
 ** 编程思维训练
 * 1、级联查看某节点下所有节点及节点值
 * 2、删除一个节点，不管有有没有任何子节点
 * 3、级联创建任意节点
 * 4、清空子节点
 */
public interface ZKHomeWork {
    /*** 级联查看某节点下所有节点及节点值 */
    public Map<String, String> getChildNodeAndValue(String path) throws Exception;

    /**
     * 删除一个节点，不管有有没有任何子节点
     */
    public boolean rmr(String path, ZooKeeper zk) throws Exception;

    /*** 级联创建任意节点 * /a/b/c/d/e */
    public boolean createZNode(String znodePath, String data, ZooKeeper zk) throws Exception;

    /*** 清空子节点 */
    public boolean clearChildNode(String znodePath, ZooKeeper zk) throws Exception;
}
