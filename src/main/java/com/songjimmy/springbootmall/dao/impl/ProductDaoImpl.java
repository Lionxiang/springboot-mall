package com.songjimmy.springbootmall.dao.impl;

import com.songjimmy.springbootmall.constant.ProductCategory;
import com.songjimmy.springbootmall.dao.ProductDao;
import com.songjimmy.springbootmall.dto.ProductQueryParams;
import com.songjimmy.springbootmall.dto.ProductRequest;
import com.songjimmy.springbootmall.model.Product;
import com.songjimmy.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "select count(1) from product where 1=1";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSQL(sql, map, productQueryParams);

        // queryObject是返回count用的api
        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "select product_id,product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date from product where 1=1";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSQL(sql, map, productQueryParams);

        sql += " order by " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();
        sql += " limit " + productQueryParams.getLimit() + " offset " + productQueryParams.getOffset() ;

        return namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql = "select product_id,product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date from product where product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(!productList.isEmpty()){
            return productList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "insert into product(product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date) values (:productName, :category, :imageUrl, :price," +
                ":stock, :description, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "update product set product_name = :productName, category = :category, " +
                "image_url = :imageUrl, price = :price, stock = :stock, description = :description, " +
                "last_modified_date = :lastModifiedDate where product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "delete from product where product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    private String addFilteringSQL(String sql, Map<String, Object> map, ProductQueryParams productQueryParams){

        if(productQueryParams.getCategory()!= null){
            sql += " and category = :category";
            map.put("category", productQueryParams.getCategory().name());
        }

        if(productQueryParams.getSearch() != null){
            sql += " and product_name like :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        return sql;
    }
}
