package com.romashko.romashkoTestProject.services.Impl;

import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.repositories.ProductRepository;
import com.romashko.romashkoTestProject.services.ProductsService;
import com.romashko.romashkoTestProject.services.utils.ProductSpecification;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ProductsServiceImpl implements ProductsService {

    private final ProductRepository productRepository;
    private static final int MAX_RETURN_VALUES = 100;

    public ProductsServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        Sort sortByName = Sort.by(Sort.Direction.DESC, "name");
        return productRepository.findAll(sortByName);
    }


    @Override
    public Product findProductByName(String name) {
        return productRepository.findProductByName(name).stream().findFirst().orElse(null);
    }

    @Override
    public List<Product> filterProducts(Map<String, String> parameters) {
        Specification<Product> specifications = Specification.where(null);
        Sort sort = Sort.unsorted();
        for (String parameterName: parameters.keySet()) {
            switch (parameterName) {
                case "name":
                    specifications = specifications.and(ProductSpecification.columnEqual("name", parameters.get(parameterName)));
                    break;
                case "nameContains":
                    specifications = specifications.and(ProductSpecification.columnContains("name", parameters.get(parameterName)));
                    break;
                case "priceEquals":
                    specifications = specifications.and(ProductSpecification.columnEqual("price", parameters.get(parameterName)));
                    break;
                case "priceGreater":
                    specifications = specifications.and(ProductSpecification.columnGreaterThan("price", Float.parseFloat(parameters.get(parameterName))));
                    break;
                case "priceLower":
                    specifications = specifications.and(ProductSpecification.columnLowerThan("price", Float.parseFloat(parameters.get(parameterName))));
                    break;
                case "available":
                    specifications = specifications.and(ProductSpecification.isAvailable("available", Boolean.parseBoolean(parameters.get(parameterName))));
                    break;
                case "sortName":
                    if ("ASC".equals(parameters.get(parameterName))) {
                        sort = sort.and(Sort.by(Sort.Direction.ASC, "name"));
                        break;
                    } else if ("DESC".equals(parameters.get(parameterName))) {
                        sort = sort.and(Sort.by(Sort.Direction.DESC, "name"));
                        break;
                    } else {
                        throw new IllegalArgumentException(String.format("SortType can be 'ASC' or 'DESC'. It can't be '%s'.", parameterName));
                    }
                case "sortPrice":
                    if ("ASC".equals(parameters.get(parameterName))) {
                        sort = sort.and(Sort.by(Sort.Direction.ASC, "price"));
                        break;
                    } else if ("DESC".equals(parameters.get(parameterName))) {
                        sort = sort.and(Sort.by(Sort.Direction.DESC, "price"));
                        break;
                    } else {
                        throw new IllegalArgumentException(String.format("SortType can be 'ASC' or 'DESC'. It can't be '%s'.", parameterName));
                    }
                default:
                    throw new IllegalArgumentException(String.format("Can't find filter parameter with name '%s'.", parameterName));
            }
        }
        Pageable pageable = PageRequest.of(0, MAX_RETURN_VALUES, sort);
        return productRepository.findAll(specifications, pageable).getContent();
    }

    @Override
    public Product findProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product old_product = productRepository.findById(product.getId()).orElse(null);
        if (old_product == null) {
            throw new IllegalArgumentException("Product with such id not found!");
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteProductId(Long id) {
        productRepository.deleteById(id);
    }
}
