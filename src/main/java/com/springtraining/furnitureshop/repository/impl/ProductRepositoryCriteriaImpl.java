package com.springtraining.furnitureshop.repository.impl;

import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Category_;
import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.domain.Producer_;
import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.domain.Product_;
import com.springtraining.furnitureshop.entity.ProductBean;
import com.springtraining.furnitureshop.repository.ProductRepositoryCriteria;
import com.springtraining.furnitureshop.util.ProductProps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepositoryCriteriaImpl implements ProductRepositoryCriteria {
    private EntityManager entityManager;

    private ProductProps props;

    public ProductRepositoryCriteriaImpl(EntityManager entityManager, ProductProps props) {
        this.entityManager = entityManager;
        this.props = props;
    }

    @Override
    public long countProducts(ProductBean bean) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Product> root = criteria.from(Product.class);
        criteria.select(builder.count(root.get(Product_.id)));
        setFilters(bean, criteria, root, builder);

        return entityManager.createQuery(criteria).getSingleResult();
    }

    @Override
    public List<Product> getProducts(ProductBean bean) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);
        criteria.select(root);
        setFilters(bean, criteria, root, builder);
        setSort(bean, criteria, root, builder);

        int size = bean.getSize() == null ? props.getSize() : bean.getSize();
        int currentPage = bean.getPage() == null ? props.getPage() : bean.getPage();
        int from = (currentPage - 1) * size;

        return entityManager.createQuery(criteria).setFirstResult(from).setMaxResults(size).getResultList();
    }

    @Override
    public Page<Product> getProductsPageable(ProductBean bean) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);
        criteria.select(root);
        setFilters(bean, criteria, root, builder);
        setSort(bean, criteria, root, builder);

        int size = bean.getSize() == null ? props.getSize() : bean.getSize();
        int currentPage = bean.getPage() == null ? props.getPage() : bean.getPage();
        currentPage--;
        Sort.Direction direction = bean.getSortOrder() == null ? props.getSortOrder() : bean.getSortOrder();
        String sortField = bean.getSortField() == null ? props.getSortField() : bean.getSortField();
        int from = currentPage * size;
        List<Product> products = entityManager.createQuery(criteria)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

        return new PageImpl<>(products,
                PageRequest.of(
                        currentPage,
                        size,
                        Sort.by(direction, sortField)),
                countProducts(bean));
    }


    private void setSort(ProductBean bean, CriteriaQuery<?> criteria, Root<Product> root, CriteriaBuilder builder) {
        String field = props.getSortField();
        Sort.Direction sortOrder = props.getSortOrder();
        if (bean.getSortField() != null) {
            field = bean.getSortField();
        }
        if (bean.getSortOrder() != null) {
            sortOrder = bean.getSortOrder();
        }
        if (sortOrder == Sort.Direction.ASC) {
            criteria.orderBy(builder.asc(root.get(field)));
        } else if (sortOrder == Sort.Direction.DESC) {
            criteria.orderBy(builder.desc(root.get(field)));
        }
    }

    private void setFilters(ProductBean bean, CriteriaQuery<?> criteria, Root<Product> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        Join<Product, Category> categoryJoin = root.join(Product_.category);

        Join<Product, Producer> producerJoin = root.join(Product_.producer);

        if (!bean.isFiltersEmpty()) {
            if (bean.getName() != null) {
                predicates.add(builder.equal(root.get(Product_.name),
                        bean.getName()));
            }
            if (bean.getMinPrice() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(Product_.price),
                        bean.getMinPrice()));
            }
            if (bean.getMaxPrice() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get(Product_.price),
                        bean.getMaxPrice()));
            }
            if (bean.getCategory() != null) {
                predicates.add(builder.equal(
                        categoryJoin.get(Category_.ID), bean.getCategory().getId()));
            }
            if (bean.getProducers() != null && !bean.getProducers().isEmpty()) {
                List<Predicate> producers = new ArrayList<>();
                for (Producer producer : bean.getProducers()) {
                    producers.add(builder.equal(producerJoin.get(Producer_.ID), producer.getId()));
                }
                predicates.add(builder.or(producers.toArray(new Predicate[]{})));
            }
            criteria.where(builder.and(predicates.toArray(new Predicate[]{})));
        }
    }
}
