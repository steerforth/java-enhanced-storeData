package com.jzp.dcim.poseidon;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Program: medusha
 * @Author: Steerforth
 * @Description: 测试基类
 * @Date: 2018-07-09 12:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PoseidonApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
@Rollback(true)
public abstract class BaseSpringBootTest {
}
