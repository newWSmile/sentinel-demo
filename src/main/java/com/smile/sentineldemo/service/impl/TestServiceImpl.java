package com.smile.sentineldemo.service.impl;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.smile.sentineldemo.service.TestService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author ：Smile(wangyajun)
 * @date ：Created in 2019/4/9 10:25
 * @description：
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    @SentinelResource(value = "testSentinel3" , entryType = EntryType.IN , fallback = "helloFallbackHandler")
    public String test(long s ) {
        try {
            TimeUnit.MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello jane :"+s;
    }


    @Override
    @SentinelResource(value = "testSentinel2" , entryType = EntryType.IN,blockHandler = "helloBlockHandler",fallback = "helloFallbackHandler")
    public String hello(long s) {
        try {
            TimeUnit.MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello smile :"+s;
    }

    public String helloFallbackHandler(long s){
        return "this is fallback response";
    }

    public String helloBlockHandler(long s, BlockException ex){
        return "sorry,this is default response "+s;
    }
}
