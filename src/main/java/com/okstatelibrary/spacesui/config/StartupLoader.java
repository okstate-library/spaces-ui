package com.okstatelibrary.spacesui.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.okstatelibrary.spacesui.tenant.TenantMasterDataLoader;

@Component
public class StartupLoader {

    private final TenantMasterDataLoader loader;

    public StartupLoader(TenantMasterDataLoader loader) {
        this.loader = loader;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        loader.loadAll();
    }
}