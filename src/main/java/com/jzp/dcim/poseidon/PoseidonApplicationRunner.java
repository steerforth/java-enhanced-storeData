package com.jzp.dcim.poseidon;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description:
 * @Date: 2018-09-27 15:29
 */
@Component
@Order(1)
public class PoseidonApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("服务加载完毕"+args);
    }
}
