package com.okstatelibrary.spacesui.tenant;

import org.springframework.stereotype.Service;

import com.okstatelibrary.spacesui.models.Category;
import com.okstatelibrary.spacesui.services.SpacesService;

@Service
public class TenantMasterDataLoader {

	private final TenantConfigRegistry registry;
	private final TenantMasterDataCache cache;
	private final SpacesService spacesService;

	public TenantMasterDataLoader(TenantConfigRegistry registry, TenantMasterDataCache cache,
			SpacesService spacesService) {

		this.registry = registry;
		this.cache = cache;
		this.spacesService = spacesService;
	}

	public void loadAll() {

		registry.getConfigs().forEach((tenant, config) -> {

			try {

				MasterData data = spacesService.loadMasterData(config);

				cache.put(tenant, data);

				System.out.println("Loaded to data cahed" + tenant);

				for (Category category : data.getCategories()) {
					System.out.println(category.getCid());
				}

			} catch (Exception ex) {

				System.err.println("Failed to load " + tenant);
				ex.printStackTrace();
			}

		});

	}
}
