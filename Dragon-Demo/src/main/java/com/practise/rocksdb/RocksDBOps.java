package com.practise.rocksdb;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

/**
 * RocksDB基本操作类
 * 由于默认操作要传入的是bytes所以做了一层封装，直接传入string操作。
 */
public class RocksDBOps {
    //数据库路径（文件夹）
    private String path;
    //rocksdb连接
    private RocksDB rocksDB;
    static {
        //加载库
        RocksDB.loadLibrary();
    }
    public RocksDBOps(String path) throws RocksDBException {
        this.path=path;
        rocksDB=RocksDB.open(path);
    }

    /**
     * 写入数据
     * @param key 键
     * @param value 值
     * @return boolean 成功与否
     */
    public boolean put(String key,String value){
        try {
            rocksDB.put(key.getBytes(),value.getBytes());
            return true;
        }catch (RocksDBException e){
            return false;
        }
    }

    /**
     * 获取数据
     * @param key 键
     * @return string 值
     */
    public String  get(String key){
        try {
            byte[] bytes=rocksDB.get(key.getBytes());
            if(bytes!=null){
                return new String(bytes);
            }
        }catch (RocksDBException e){
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 删除数据
     * @param key 键
     * @return boolean 成功与否
     */
    public boolean delete(String key){
        try {
            rocksDB.delete(key.getBytes());
            return true;
        }catch (RocksDBException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 关闭连接
     */
    public void close(){
        if(rocksDB!=null){
            rocksDB.close();
        }
    }
    public static void main(String[] args) {
        String path="./data/";
        try {
            //创建连接
            RocksDBOps rocksDBOps=new RocksDBOps(path);
            String key="chen";
            //写入
            rocksDBOps.put(key,"programmer");
            //获取
            String job=rocksDBOps.get(key);
            System.out.println("job："+job);
            //删除
//            boolean ret=rocksDBOps.delete(key);
//            if(ret){
//                System.out.println("deleted");
//            }else {
//                System.err.println("delete failed");
//            }
            //关闭连接
            rocksDBOps.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}


