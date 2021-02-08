package com.rancho.web.actuator.endpoint;

import com.rancho.web.actuator.util.LogStackReaderUtil;
import com.rancho.web.actuator.util.LogQueueReaderUtil;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;

@WebEndpoint(id="log")
public class LogEndpoint {

    private int redNum;

    @Autowired
    private JvmMemoryMetrics jvmMemoryMetrics;

    @Autowired
    private MetricsEndpoint metricsEndpoint;

    @ReadOperation
    public String log(int redNum) {
        MetricsEndpoint.MetricResponse metricResponse=metricsEndpoint.metric("jvm.memory.max",null);
        System.out.println(metricResponse.getAvailableTags());
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
