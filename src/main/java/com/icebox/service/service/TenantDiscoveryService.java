package com.icebox.service.service;

import org.springframework.stereotype.Service;

@Service
public class TenantDiscoveryService {
    public String getTenantIdFromHostname(String hostname) {
        return "0f4d4072-5d8c-4356-8d03-00364e0ad197";
//        if (hostname.contains("fruitone")) return "0f4d4072-5d8c-4356-8d03-00364e0ad197";
//        throw new TenantNotFoundException("Unknown domain");
    }
}
