package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sample")
record SampleConfigurationProperties(String message) { }
