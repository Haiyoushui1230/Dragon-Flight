package com.practise.test;

public class Test01 {
    public int removeDuplicates(int[] A){
        //判断边界
        if(A==null||A.length==0){
            return 0;
        }
        int left = 0;
        for (int right = 1; right < A.length; right++){
            if(A[left]==A[right]){

            }else {
                left++;
                A[left]=A[right];
            }

        }
        return ++left;
    }

    public static void main(String[] args) {
        Test01 test01 = new Test01();
        int[] a={1,1,2,3};
        System.out.println(test01.removeDuplicates(a));
    }
}
