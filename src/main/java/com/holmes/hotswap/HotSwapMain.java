package com.holmes.hotswap;

import com.sun.tools.attach.*;

import java.io.IOException;

public class HotSwapMain {

    public static void main(String[] args) {

        if (args == null || args.length < 1) {
            System.out.println("请输入参数");
            return;
        }

        if ("show".equals(args[0])) {
            // 输出进程列表
            for (VirtualMachineDescriptor descriptor : VirtualMachine.list()) {
                System.out.println("id: " + descriptor.id() + ", name: " + descriptor.displayName());
            }
        } else if ("swap".equals(args[0])) {

            if (args.length < 3) {
                System.out.println("请输入进程 pid 和 class文件的绝对路径");
                return;
            }

            String pid = args[1];
            String classPath = args[2];

            System.out.println("进行热更新的jvm进程PID为" + pid + ", 更换的class文件路径为" + classPath);

            // 获取当前jar的路径
            String jarPath = HotSwapMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();

            try {
                // 连接指定的jvm进程
                VirtualMachine virtualMachine = VirtualMachine.attach(pid);
                virtualMachine.loadAgent(jarPath, classPath);
            } catch (AttachNotSupportedException | IOException | AgentLoadException | AgentInitializationException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("参数输入错误{show, swap}");
        }

    }
}
