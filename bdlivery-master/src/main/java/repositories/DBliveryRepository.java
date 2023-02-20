package repositories;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import model.Order;
import model.Product;
import model.Supplier;
import model.User;

 
public class DBliveryRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public User getUserById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user= (User)session.get(User.class, id);
		return user;
	}
	
	public Order getOrderById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Order o= (Order)session.get(Order.class, id);
		return o;
	}
	
	public Product getProductById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Product p= (Product)session.get(Product.class, id);
		return p;
	}
	
	public User getUserByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select u " + "from User u " + "where u.username = '" + username + "'");
		User user = (User)query.getSingleResult();
		return user;
	}
	
	public User getUserByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select u " + "from User u " + "where u.email = '" + email + "'");
		User user = (User)query.getSingleResult();
		return user;
	}
	
	public void createUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}
	
	public void createOrder(Order order) {
		Session session = sessionFactory.getCurrentSession();
		session.save(order);
	}
	
	public void createSupplier(Supplier supplier) {
		Session session = sessionFactory.getCurrentSession();
		session.save(supplier);
	}
	
	public void createProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.save(product);
	}
	
	public Product updateProductPrice(Long id, Float price, Date startDate) {
		Session session = sessionFactory.getCurrentSession();
		Product p= (Product)session.get(Product.class, id);
		p.changePrice(price,startDate);
		session.update(p);
		return p;
	}
	
	public Order addProduct (Long id,Long quantity, Product product ) {
		Session session = sessionFactory.getCurrentSession();
		Order o= (Order)session.get(Order.class, id);
		o.addProduct(product, quantity);
		session.update(o);
		return o;
	}
	
	public Order deliverOrder(Long id, User deliveryUser) {
		Session session = sessionFactory.getCurrentSession();
		Order o= (Order)session.get(Order.class, id);
		o.deliverOrder(deliveryUser);
		session.update(o);
		return o;
	}
	
	public Order cancelOrder(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Order o= (Order)session.get(Order.class, id);
		o.cancelOrder();
		session.update(o);
		return o;
	}
	
	public Order finishOrder(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Order o= (Order)session.get(Order.class, id);
		o.finishOrder();
		session.update(o);
		return o;
	}
	
	public List<Product> getProductByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select p " + "from Product p " + "where p.name LIKE '%" + name + "%'");
		List<Product> products = (List<Product>)query.getResultList();
		return products;
	}
	
	
	
	
	
	
	
	
	
	
}
