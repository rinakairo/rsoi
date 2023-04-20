package com.example.rsoi3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.Scanner;

@SpringBootApplication
public class Rsoi3Application {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Rsoi3Application.class, args);
        ProductService productService = context.getBean(ProductService.class);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить продукт");
            System.out.println("2. Удалить продукт");
            System.out.println("3. Добавить оценку");
            System.out.println("4. Вывести отсортированный по средней оценке список продуктов");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    Product product = new Product();
                    System.out.println("Введите название продукта:");
                    product.setName(scanner.nextLine());
                    System.out.println("Введите количество заказов:");
                    product.setOrders(scanner.nextInt());
                    scanner.nextLine();
                    productService.addProduct(product);
                    break;
                case 2:
                    System.out.println("Введите id продукта, который нужно удалить:");
                    Long productId = scanner.nextLong();
                    scanner.nextLine();
                    productService.deleteProduct(productId);
                    break;
                case 3:
                    Rating rating = new Rating();
                    System.out.println("Введите id продукта, для которого нужно добавить оценку:");
                    rating.setProduct(productService.getById(scanner.nextLong()));
                    System.out.println("Введите оценку (от 1 до 5):");
                    Scanner scanner2 = new Scanner(System.in);
                    rating.setScore(scanner2.nextInt());
                    rating.setDate(LocalDate.now());
                    productService.addRating(rating);
                    break;
                case 4:
                   productService.sortProductsByRating();
                    break;
                default:
                    System.out.println("Введена некорректная цифра.");
            }
        }
    }
}
