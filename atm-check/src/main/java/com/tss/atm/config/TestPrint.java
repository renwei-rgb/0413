package com.tss.atm.config;

import com.tss.config.config.DatasourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestPrint {

    @Autowired
    private DatasourceProperties properties;

    @PostConstruct
    public void init() {
        System.out.println("数据库地址: " + properties.getUrl());
    }
    }