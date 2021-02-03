package com.rancho.web.actuator.endpoint;

import com.rancho.web.actuator.util.LogStackReaderUtil;
import com.rancho.web.actuator.util.LogQueueReaderUtil;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;

@WebEndpoint(id="log")
public class LogEndpoint {

    private int redNum;

    @ReadOperation
    public String log(int redNum) {
//        String msg;
//        if(this.redNum!=redNum){
//            this.redNum=redNum;
//            msg= LogStackReaderUtil.popNum("C:\\Users\\admin\\Desktop\\test.txt", redNum);
//        }else{
//            msg= LogQueueReaderUtil.poll("C:\\Users\\admin\\Desktop\\test.txt");
//        }
//        System.out.println(msg);
//        return msg;
        String msg= LogStackReaderUtil.popNum("C:\\Users\\admin\\Desktop\\test.txt", redNum);
        return msg;
    }

}
