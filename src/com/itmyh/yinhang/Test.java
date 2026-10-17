package com.itmyh.yinhang;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        ArrayList<Cust> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int selfNum = 0;

        // 添加普通账户
        Cust c1 = new Cust("张三", "001", "1234", 6000);
        Cust c2 = new Cust("李四", "002", "12345", 7000);
        Cust c3 = new Cust("王五", "003", "123456", 8000);
        // 添加VIP账户
        VIPCust v1 = new VIPCust("赵六", "004", "888888", 2000);
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(v1); // 将VIP账户加入集合

        System.out.println("***进行ATM操作前需核对信息***");
        // 1. 验证用户名
        while (true) {
            System.out.print("请输入你的用户名：");
            String name = sc.next();
            selfNum = checkName(list, name);
            if (selfNum >= 1) {
                break;
            } else {
                System.out.println("该用户不存在！");
            }
        }


        // 2. 业务操作循环
        while (true) {
            Cust current = list.get(selfNum - 1);
            System.out.println("******" + current.getName() + "欢迎来到ATM******");
            System.out.println("1.取款");
            System.out.println("2.存钱");
            System.out.println("3.修改当前密码");
            System.out.println("4.查询账号信息");
            System.out.println("5.查看银行里所有用户的余额总值");
            System.out.println("6.将账户按余额由多到少显示信息");
            // 给VIP账户增加“7.转账”选项
            if (current instanceof VIPCust) {
                System.out.println("7.转账");
                System.out.println("8.退出");
            } else {
                System.out.println("7.退出");
            }
            System.out.print("请选择你的业务：");
            String num = sc.next();


            // 业务逻辑处理
            switch (num) {
                case "1":
                    current.getMoney(list, selfNum);
                    break;
                case "2":
                    current.saveMoney(list, selfNum);
                    break;
                case "3":
                    current.changePWD(list, selfNum);
                    break;
                case "4":
                    current.search(list, selfNum);
                    break;
                case "5":
                    // 将集合转为数组传入moneySum
                    Cust[] custArray = list.toArray(new Cust[0]);
                    Cust.moneySum(custArray);
                    break;
                case "6":
                    Cust[] sortArray = list.toArray(new Cust[0]);
                    Cust.sortInfo(sortArray);
                    break;
                case "7":
                    // 普通用户：7是退出；VIP用户：7是转账
                    if (current instanceof VIPCust) {
                        ((VIPCust) current).transfer(list, selfNum);
                    } else {
                        System.out.println("欢迎您下次使用！");
                        System.exit(0);
                    }
                    break;
                case "8":
                    // VIP用户的退出选项
                    if (current instanceof VIPCust) {
                        System.out.println("欢迎您下次使用！");
                        System.exit(0);
                    } else {
                        System.out.println("没有该业务！");
                    }
                    break;
                default:
                    System.out.println("没有该业务！");
            }
        }
    }


    // 核对用户名是否存在，返回个人流水号
    public static int checkName(ArrayList<Cust> list, String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return i + 1; // 返回第i+1个用户
            }
        }
        return 0;
    }
}
