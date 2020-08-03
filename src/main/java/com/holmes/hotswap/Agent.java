package com.holmes.hotswap;

import jdk.internal.org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 *
 */
public class Agent {

    public static void agentmain(String agentArgs, Instrumentation inst) {

        System.out.println("开始执行热更新代码, agentArgs: " + agentArgs);

        try {
            RandomAccessFile f = new RandomAccessFile(agentArgs, "r");
            final byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            final String clazzName = readClassName(bytes);

            System.out.println("更新的代码类路径为: " + clazzName);

            // 获取所有加载的class
            for (Class clazz : inst.getAllLoadedClasses()) {
                if (clazz.getName().equals(clazzName)) {
                    ClassDefinition definition = new ClassDefinition(clazz, bytes);
                    // 通过ClassDefinition的redefineClasses替换类的定义
                    inst.redefineClasses(definition);
                    System.out.println("热更新代码完成");
                }
            }
        } catch (IOException | ClassNotFoundException | UnmodifiableClassException e) {
            System.out.println("热更新代码失败");
            e.printStackTrace();
        }

    }

    /**
     * 从 class 文件中读取类路径
     * @param bytes
     * @return
     */
    private static String readClassName(final byte[] bytes) {
        return new ClassReader(bytes).getClassName().replace("/", ".");
    }
}
