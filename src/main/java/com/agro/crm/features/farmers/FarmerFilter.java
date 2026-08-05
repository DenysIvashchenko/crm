package com.agro.crm.features.farmers;

public record FarmerFilter(
        String       search,
        String       region,
        FarmerStatus status
) {
    public static FarmerFilter of(
            String search,
            String region,
            FarmerStatus status) {

        String cleanSearch = search != null && !search.trim().isEmpty()
                ? search.trim()
                : null;

        String cleanRegion = region != null && !region.trim().isEmpty()
                ? region.trim()
                : null;

        return new FarmerFilter(cleanSearch, cleanRegion, status);
    }

    public boolean isEmpty() {
        return search == null && region == null && status == null;
    }
}
