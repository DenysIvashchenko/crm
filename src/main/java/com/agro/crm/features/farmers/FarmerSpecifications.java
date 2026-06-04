package com.agro.crm.features.farmers;

import com.agro.crm.features.agromap.feild.SoilType;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FarmerSpecifications {
    public static Specification<Farmer> getFilterSpecification(
            String search,
            FarmerStatus status,
            SoilType soilType
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (soilType != null) {
                Join<Object, Object> fieldsJoin = root.join("fields");
                predicates.add(cb.equal(fieldsJoin.get("soilType"), soilType));
            }

            if (search != null) {
                String pattern = "%" + search.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("fullName")), pattern),
                        cb.like(cb.lower(root.get("phone")), pattern),
                        cb.like(cb.lower(root.get("region")), pattern)
                ));
            }

            query.distinct(true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
