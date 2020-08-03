package com.holmes.hotswap;

/**
 * 即将被替换的代码
 */
public class HotSwapServiceImpl implements HotSwapService {

    private int count = 0;

    public void hello() {

//        System.out.println("hello world!");
        System.out.println("hello hot swap, count = " + count++);
    }
}
