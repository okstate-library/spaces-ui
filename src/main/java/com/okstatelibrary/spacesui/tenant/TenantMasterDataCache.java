package com.okstatelibrary.spacesui.tenant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TenantMasterDataCache {

    private final Map<String, MasterData> cache = new ConcurrentHashMap<>();

    public void put(String tenant, MasterData data) {
        cache.put(tenant, data);
    }

    public MasterData get(String tenant) {
        return cache.get(tenant);
    }
}