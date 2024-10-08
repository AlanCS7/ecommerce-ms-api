INSERT INTO category (name, description)
VALUES ('Electronics', 'Devices and gadgets'),
       ('Books', 'Printed and digital literature'),
       ('Clothing', 'Apparel for men, women, and children'),
       ('Home Appliances', 'Various appliances for home use'),
       ('Sports Equipment', 'Gear and equipment for sports and outdoor activities');

INSERT INTO product (name, description, available_quantity, price, category_id)
VALUES ('Smartphone', 'Latest model smartphone with advanced features', 100, 699.99, 1),
       ('Laptop', 'High-performance laptop for work and gaming', 50, 1299.99, 1),
       ('Fiction Novel', 'Popular fiction book by a renowned author', 200, 19.99, 2),
       ('Cookbook', 'A collection of recipes from around the world', 150, 29.99, 2),
       ('T-shirt', 'Cotton t-shirt available in various colors', 300, 15.99, 3),
       ('Jeans', 'Classic fit jeans for everyday wear', 250, 49.99, 3),
       ('Refrigerator', 'Energy-efficient refrigerator with large capacity', 20, 899.99, 4),
       ('Microwave Oven', 'Compact microwave oven for quick meals', 35, 149.99, 4),
       ('Tennis Racket', 'Lightweight tennis racket for beginners', 80, 89.99, 5),
       ('Yoga Mat', 'Durable yoga mat for comfort and stability', 150, 29.99, 5);