package com.example.android_kt02.data;

import android.content.Context;



import java.util.List;

public class ShopRepository {
    private static volatile ShopRepository INSTANCE;

    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final ProductDao productDao;
    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;

    private ShopRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        DatabaseSeeder.seedIfNeeded(db);
        userDao = db.userDao();
        categoryDao = db.categoryDao();
        productDao = db.productDao();
        orderDao = db.orderDao();
        orderDetailDao = db.orderDetailDao();
    }

    public static ShopRepository getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ShopRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ShopRepository(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    public User login(String username, String password) {
        return userDao.login(username, password);
    }

    public List<Category> getCategories() {
        return categoryDao.getAll();
    }

    public List<Product> getProducts(Integer categoryId) {
        if (categoryId == null || categoryId <= 0) {
            return productDao.getAll();
        }
        return productDao.getByCategoryId(categoryId);
    }

    public Product getProductById(int productId) {
        return productDao.getById(productId);
    }

    public Order getOpenOrderForUser(int userId) {
        return orderDao.getOpenOrderByUser(userId);
    }

    public Order getOrderById(int orderId) {
        return orderDao.getById(orderId);
    }

    public int addProductToCurrentOrder(int userId, int productId) {
        Product product = productDao.getById(productId);
        if (product == null) {
            return -1;
        }

        int orderId = getOrCreateOpenOrder(userId);
        OrderDetail existed = orderDetailDao.getByOrderAndProduct(orderId, productId);
        if (existed == null) {
            orderDetailDao.insert(new OrderDetail(orderId, productId, 1, product.getPrice()));
        } else {
            existed.setQuantity(existed.getQuantity() + 1);
            orderDetailDao.update(existed);
        }

        double total = orderDetailDao.calculateOrderTotal(orderId);
        orderDao.updateTotal(orderId, total);
        return orderId;
    }

    public List<OrderLine> getOrderLines(int orderId) {
        return orderDetailDao.getLinesByOrderId(orderId);
    }

    public int checkoutOpenOrder(int userId) {
        Order openOrder = orderDao.getOpenOrderByUser(userId);
        if (openOrder == null) {
            return -1;
        }
        orderDao.markPaid(openOrder.getId());
        return openOrder.getId();
    }

    private int getOrCreateOpenOrder(int userId) {
        Order openOrder = orderDao.getOpenOrderByUser(userId);
        if (openOrder != null) {
            return openOrder.getId();
        }

        Order newOrder = new Order(userId, "OPEN", 0, System.currentTimeMillis());
        return (int) orderDao.insert(newOrder);
    }
}

