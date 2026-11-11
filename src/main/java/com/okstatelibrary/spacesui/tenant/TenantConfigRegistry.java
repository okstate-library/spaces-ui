package com.okstatelibrary.spacesui.tenant;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.okstatelibrary.spacesui.globals.GlobalConfigs;

@Service
public class TenantConfigRegistry {

	private final Map<String, GlobalConfigs> configs;

	public TenantConfigRegistry(List<GlobalConfigs> configList) {

		configs = configList.stream().collect(Collectors.toMap(GlobalConfigs::getSubDomain, Function.identity()));

		configs.forEach((tenant, config) -> {
			System.out.println("Tenant: " + tenant);
			System.out.println("getInstanceName: " + config.getInstanceName());
			System.out.println("getSubDomain: " + config.getSubDomain());
			System.out.println();
		});
	}

	public GlobalConfigs getCurrentConfig() {

		String tenant = TenantContext.getTenantId();

		System.out.println("tenant " + tenant);

		return configs.getOrDefault(tenant, configs.get("edmon-low"));
	}

	public Map<String, GlobalConfigs> getConfigs() {

		return configs;
	}
}
