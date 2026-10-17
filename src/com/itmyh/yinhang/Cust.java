package com.itmyh.yinhang;
import java.util.ArrayList;
import java.util.Scanner;

public class Cust {
    private Scanner sc = new Scanner(System.in); // 单独初始化Scanner
    private String name;
    private String id;
    private String pwd;
    private int money;
    private int selfNum;

    static String bankName = "中国建设银行";
    static int allNum = 0; // 开户总数（原allNum拼写错误修正）


    // 含参构造方法（初始化时自动分配selfNum）
    public Cust(String name, String id, String pwd, int money) {
        this.name = name;
        this.id = id;
        this.pwd = pwd;
        this.money = money;
        Cust.allNum++;       // 开户总数自增
        this.selfNum = Cust.allNum; // 个人流水号 = 当前开户数
    }


    // Getter/Setter方法
    public String getName() { return name; }
    public String getId() { return id; }
    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public int getMoney() { return money; }
    public void setMoney(int money) { this.money = money; }
    public int getSelfNum() { return selfNum; }


    // 取款功能（普通账户：余额不足则无法取款）
    public void getMoney(ArrayList<Cust> list, int selfNum) {
        System.out.print("请输入你的取款金额：");
        int money = sc.nextInt();
        Cust current = list.get(selfNum - 1);

        if (money > current.getMoney()) {
            System.out.println("余额不足");
        } else {
            current.setMoney(current.getMoney() - money);
            System.out.println("取款成功，当前余额为：" + current.getMoney());
        }
    }


    // 存款功能
    public void saveMoney(ArrayList<Cust> list, int selfNum) {
        System.out.print("请输入你存钱的金额：");
        int money = sc.nextInt();
        Cust current = list.get(selfNum - 1);
        current.setMoney(current.getMoney() + money);
        System.out.println("存款成功，当前余额为：" + current.getMoney());
    }


    // 展示用户信息
    public void search(ArrayList<Cust> list, int selfNum) {
        Cust cust = list.get(selfNum - 1);
        System.out.println("你的所属银行为：" + Cust.bankName);
        System.out.println("你是本银行第" + Cust.allNum + "个顾客中的第" + selfNum + "个顾客");
        System.out.println("用户姓名为：" + cust.getName());
        System.out.println("用户账号为：" + cust.getId());
        System.out.println("用户余额为：" + cust.getMoney());
    }


    // 修改密码功能
    public void changePWD(ArrayList<Cust> list, int selfNum) {
        while (true) {
            System.out.print("你是否更改密码（T/F）：");
            String choose = sc.next();
            Cust current = list.get(selfNum - 1);

            if (choose.equalsIgnoreCase("T")) {
                System.out.print("请输入你的新密码！");
                String password = sc.next();
                current.setPwd(password);
                System.out.println("修改密码成功！");
                break;
            } else if (choose.equalsIgnoreCase("F")) {
                System.out.println("请到本地银行去修改密码！");
                System.exit(0);
            } else {
                System.out.println("你输入的格式有误！请重新选择！");
            }
        }
    }


    // 计算所有账户的余额总数（可变参数）
    public static void moneySum(Cust... custs) {
        int sum = 0;
        for (Cust cust : custs) {
            sum += cust.getMoney();
        }
        System.out.println("所有用户的总金额为：" + sum);
    }


    // 将所有账户按余额由多到少排序显示
    public static void sortInfo(Cust... custs) {
        // 冒泡排序（降序）
        for (int i = 0; i < custs.length; i++) {
            for (int j = 1; j < custs.length - i; j++) {
                if (custs[j - 1].getMoney() < custs[j].getMoney()) {
                    Cust temp = custs[j - 1];
                    custs[j - 1] = custs[j];
                    custs[j] = temp;
                }
            }
        }

        // 输出排序结果
        System.out.println("账户按余额从多到少排序：");
        for (Cust c : custs) {
            System.out.println(c.getName() + "," + c.getId() + "," + c.getMoney());
        }
    }
}

