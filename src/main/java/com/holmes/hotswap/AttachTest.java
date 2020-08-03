package com.holmes.hotswap;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class AttachTest {

    public static void main(String[] args) {

        // 获取虚拟机中的java进程
        for (VirtualMachineDescriptor descriptor : VirtualMachine.list()) {
            System.out.println("id: " + descriptor.id() + ", name: " + descriptor.displayName());
        }

        try {
            // 连接上指定 pid 的进程
            VirtualMachine vm = VirtualMachine.attach("1572");

            // 获取系统属性
            System.out.println("\nshow system properties");
            Properties properties = vm.getSystemProperties();
            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                Object key = enumeration.nextElement();
                System.out.println("key: " + key + ", value: " + properties.get(key));
            }

            // 获取代理属性
            System.out.println("\nshow agent properties");
            properties = vm.getAgentProperties();
            enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                Object key = enumeration.nextElement();
                System.out.println("key: " + key + ", value: " + properties.get(key));
            }

            // 断开连接
            vm.detach();
        } catch (AttachNotSupportedException | IOException e) {
            e.printStackTrace();
        }


    }
}
