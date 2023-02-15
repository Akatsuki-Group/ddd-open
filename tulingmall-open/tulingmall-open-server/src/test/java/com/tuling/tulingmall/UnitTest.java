package com.tuling.tulingmall;

import com.alibaba.fastjson.JSONObject;
import com.tuling.tulingmall.open.service.DecrypService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UnitTest {

    @Resource
    private DecrypService decrypService;

    public void decryptTest(){
//        JSONObject jsonObject = decrypService.decrypData();
//        System.out.println(jsonObject);
    }
}
