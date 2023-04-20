package com.example.rsoi3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final RatingRepository ratingRepository;

    public ProductService(ProductRepository productRepository, RatingRepository ratingRepository) {
        this.productRepository = productRepository;
        this.ratingRepository = ratingRepository;
    }


   public Product getById(Long id){
        return productRepository.getById(id);
   }


    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public void sortProductsByRating() {
        List<Product> products = productRepository.findAll();

        products.sort((p1, p2) -> {
            Double avgRating1 = getAverageRating(p1);
            Double avgRating2 = getAverageRating(p2);
            return avgRating2.compareTo(avgRating1);
        });
        products.forEach(p -> System.out.println(p.getName() + ": " + getAverageRating(p)));
    }

    public void addRating(Rating rating) {
        ratingRepository.save(rating);
    }

    public Double getAverageRating(Product product) {
        List<Rating> ratings = ratingRepository.findAll();
        if (product.getOrders() < 100) {
            LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
            ratings = ratings.stream().filter(r -> r.getDate().isAfter(threeMonthsAgo)).collect(Collectors.toList());
        }
    else {
            LocalDate oneYearAgo = LocalDate.now().minusYears(1);
            ratings = ratings.stream().filter(r -> r.getDate().isAfter(oneYearAgo)).collect(Collectors.toList());
        }
            ratings = ratings.stream().filter(r -> r.getProduct().getId().equals(product.getId())).collect(Collectors.toList());
            return ratings.stream().mapToInt(Rating::getScore).average().getAsDouble();
    }

}




