package com.holmes.hotswap;

import java.util.concurrent.TimeUnit;

/**
 * 测试热更新的代码
 */
public class HotSwapTest {

    public static void main(String[] args) throws InterruptedException {

        HotSwapService hotSwapService = new HotSwapServiceImpl();
        while(true) {
            hotSwapService.hello();
            TimeUnit.SECONDS.sleep(3);
        }
    }
}
