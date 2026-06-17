package com.agro.crm.features.farmers;

import org.springframework.data.jpa.domain.Specification;

public class FarmerSpec {
    public static Specification<Farmer> filter(FarmerFilter f) {
        return Specification
                .where(distinct())
                .and(likeSearch(f.search()))
                .and(eqRegion(f.region()))
                .and(eqStatus(f.status()));
    }

    private static Specification<Farmer> likeSearch(String search) {
        return (root, query, cb) -> {
            if (search == null || search.trim().isEmpty()) {
                return cb.conjunction();
            }
            String pattern = "%" + search.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("fullName")), pattern),
                    cb.like(cb.lower(root.get("region")), pattern),
                    cb.like(cb.lower(root.get("phone")), pattern)
            );
        };
    }

    private static Specification<Farmer> eqRegion(String region) {
        return (root, query, cb) -> {
            if (region == null || region.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("region"), region.trim());
        };
    }

    private static Specification<Farmer> eqStatus(FarmerStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("status"), status);
        };
    }

    private static Specification<Farmer> distinct() {
        return (root, query, cb) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                query.distinct(true);
            }
            return cb.conjunction();
        };
    }
}
