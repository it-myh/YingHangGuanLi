package com.itmyh.yinhang;
import java.util.ArrayList;
import java.util.Scanner;

public class VIPCust extends Cust {
    private final int overdraftLimit = 3000; // 透支额度（固定3000元）
    private Scanner sc = new Scanner(System.in);
    // VIP账户构造方法（继承父类构造）
    public VIPCust(String name, String id, String pwd, int money) {
        super(name, id, pwd, money);
    }
    // 重写取款方法（支持透支：余额+透支额度）
    @Override
    public void getMoney(ArrayList<Cust> list, int selfNum) {
        System.out.print("请输入你的取款金额：");
        int money = sc.nextInt();
        VIPCust current = (VIPCust) list.get(selfNum - 1); // 强转为VIPCust
        // 可用额度 = 账户余额 + 透支额度
        int available = current.getMoney() + overdraftLimit;
        if (money > available) {
            System.out.println("取款失败：超过可用额度（最大可取" + available + "元）");
        } else {
            current.setMoney(current.getMoney() - money);
            System.out.println("取款成功，当前余额为：" + current.getMoney()
                    + "（透支剩余：" + (overdraftLimit + current.getMoney()) + "元）");
        }
    }
    // 新增转账方法（向其他Cust账户转账）
    public void transfer(ArrayList<Cust> list, int selfNum) {
        System.out.print("请输入目标账号：");
        String targetId = sc.next();
        System.out.print("请输入转账金额：");
        int amount = sc.nextInt();
        System.out.print("请输入你的密码：");
        String inputPwd = sc.next();

        VIPCust current = (VIPCust) list.get(selfNum - 1);
        // 1. 密码验证
        if (!inputPwd.equals(current.getPwd())) {
            System.out.println("转账失败：密码错误");
            return;
        }
        // 2. 金额合法性
        if (amount <= 0) {
            System.out.println("转账失败：金额需大于0");
            return;
        }
        // 3. 可用额度验证
        int available = current.getMoney() + overdraftLimit;
        if (amount > available) {
            System.out.println("转账失败：超过可用额度（最大可转" + available + "元）");
            return;
        }

        // 4. 查找目标账户
        Cust target = null;
        for (Cust c : list) {
            if (c.getId().equals(targetId)) {
                target = c;
                break;
            }
        }
        if (target == null) {
            System.out.println("转账失败：目标账号不存在");
            return;
        }

        // 5. 执行转账
        current.setMoney(current.getMoney() - amount);
        target.setMoney(target.getMoney() + amount);
        System.out.println("转账成功！向账号" + targetId + "转账" + amount + "元，当前余额：" + current.getMoney());
    }
}