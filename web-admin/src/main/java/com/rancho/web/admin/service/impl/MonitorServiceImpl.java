package com.rancho.web.admin.service.impl;

import com.rancho.web.admin.domain.vo.Classes;
import com.rancho.web.admin.domain.vo.Cpu;
import com.rancho.web.admin.domain.vo.Heap;
import com.rancho.web.admin.domain.vo.Threads;
import com.rancho.web.admin.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private MetricsEndpoint metricsEndpoint;

    @Override
    public Map<String, Object> getServeInfo() {
        Map<String,Object> res=new HashMap<>();
        res.put("cpu",getCpuInfo());
        res.put("heap",getHeapInfo());
        res.put("classes",getClassesInfo());
        res.put("threads",getThreadsInfo());
        return res;
    }

    @Override
    public Map<String, Object> getServerInfo() {
        Map<String,Object> res=new HashMap<>();
        res.put("cpu",getCpuInfo());
        res.put("heap",getHeapInfo());
        res.put("classes",getClassesInfo());
        res.put("threads",getThreadsInfo());
        return res;
    }

    private Cpu getCpuInfo(){
        MetricsEndpoint.MetricResponse metricResponse=metricsEndpoint.metric("system.cpu.usage",null);
        List<MetricsEndpoint.Sample> samples=metricResponse.getMeasurements();
        Cpu cpu=new Cpu();
        cpu.setUsage(samples.get(0).getValue());
        return cpu;
    }

    private Heap getHeapInfo(){
        Heap heap=new Heap();
        MetricsEndpoint.MetricResponse metricResponse=null;
        List<MetricsEndpoint.Sample> samples=null;

        metricResponse=metricsEndpoint.metric("jvm.memory.max",null);
        samples=metricResponse.getMeasurements();
        heap.setMax(samples.get(0).getValue());

        metricResponse=metricsEndpoint.metric("jvm.memory.used",null);
        samples=metricResponse.getMeasurements();
        heap.setUsed(samples.get(0).getValue());

        metricResponse=metricsEndpoint.metric("jvm.memory.committed",null);
        samples=metricResponse.getMeasurements();
        heap.setSize(samples.get(0).getValue());
        return heap;
    }

    private Classes getClassesInfo(){
        Classes classes=new Classes();
        MetricsEndpoint.MetricResponse metricResponse=null;
        List<MetricsEndpoint.Sample> samples=null;

        metricResponse=metricsEndpoint.metric("jvm.classes.loaded",null);
        samples=metricResponse.getMeasurements();
        classes.setTotalLoaded(samples.get(0).getValue().longValue());

        metricResponse=metricsEndpoint.metric("jvm.classes.unloaded",null);
        samples=metricResponse.getMeasurements();
        classes.setTotalLoaded(samples.get(0).getValue().longValue());

        return classes;
    }

    private Threads getThreadsInfo(){
        Threads threads=new Threads();
        MetricsEndpoint.MetricResponse metricResponse=null;
        List<MetricsEndpoint.Sample> samples=null;

        metricResponse=metricsEndpoint.metric("jvm.threads.daemon",null);
        samples=metricResponse.getMeasurements();
        threads.setDaemon(samples.get(0).getValue().longValue());

        metricResponse=metricsEndpoint.metric("jvm.threads.live",null);
        samples=metricResponse.getMeasurements();
        threads.setLive(samples.get(0).getValue().longValue());

        metricResponse=metricsEndpoint.metric("jvm.threads.peak",null);
        samples=metricResponse.getMeasurements();
        threads.setLivePeak(samples.get(0).getValue().longValue());

        metricResponse=metricsEndpoint.metric("jvm.threads.states",null);
        samples=metricResponse.getMeasurements();
        threads.setTotalStarted(samples.get(0).getValue().longValue());
        return threads;
    }
}
