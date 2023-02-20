package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;

 
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
	
	public void create(Object obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}
	
	public void update(Object obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
	}
	
	public List<Product> getProductByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select p " + "from Product p " + "where p.name LIKE '%" + name + "%'");
		List<Product> products = (List<Product>)query.getResultList();
		return products;
	}
	

	public List<Order> getAllOrdersMadeByUser(String username){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select o " + 
							"from Order o inner join o.client u " 
							+ "where u.username = :username ");
		List<Order> orders = (List<Order>)query.setParameter("username", username).getResultList();
		return orders;
	}
	
	
	public List<User> getUsersSpendingMoreThan(Float amount){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select distinct u " + 
							"from Order o inner join o.client u " +
							"where o.amount > :amount" );
		List<User> users = (List<User>)query.setParameter("amount", amount).getResultList();
		return users;
	}
	
	
	public List<Supplier> getTopNSuppliersInSentOrders(int n){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select s " + 
							"from Order o "
							+ "inner join o.state os "
							+ "inner join o.products pc "
							+ "inner join pc.product p "
							+ "inner join p.supplier s " +
							"where (os.name = 'Send') " +
							"group by s.id " +
							"order by sum(pc.quantity)desc ");
		List<Supplier> supps = (List<Supplier>)query.setMaxResults(n).getResultList();
		return supps;
	}


	public List<Product> getTop10MoreExpensiveProducts(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select p " + 
							"from Product p " +
							"order by (p.price) desc");
		List<Product> products = (List<Product>)query.setMaxResults(10).getResultList();
		return products;
	}
	

	public List<User> getTop6UsersMoreOrders(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select  u " + 
							"from Order o inner join o.client u " +
							"group by u.id " +
							"order by count(o)desc" );
		List<User> users = (List<User>)query.setMaxResults(6).getResultList();
		return users;
	}
	
	
	 public List <Order> getCancelledOrdersInPeriod(Date startDate, Date endDate){
		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("select o " + 
								"from Order o inner join o.state os " +
								 "where (os.name = 'Cancelled') " +
								"and (os.date between :startDate and :endDate)");
		List<Order> orders = (List<Order>)query.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
		return orders;
	 }
	
	 
	public List <Order>  getPendingOrders(){
		Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("select o " + 
								"from Order o inner join o.state os " +
								 "where (os.name = 'Pending') " );
		List<Order> orders = (List<Order>)query.getResultList();
		return orders;
	 }
	
	
	 public List <Order>  getSentOrders(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select o " + 
								"from Order o inner join o.state os " +
									"where (os.name = 'Send') " );
		List<Order> orders = (List<Order>)query.getResultList();
		return orders;
	 }
	 
	 
	 public List <Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate){
		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("select o " + 
								"from Order o inner join o.state os " +
								 "where (os.name = 'Delivered') " +
									"and (os.date between :startDate and :endDate)");
		List<Order> orders = (List<Order>)query.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
		return orders;
	 }
	 
	  
	 public List <Order> getDeliveredOrdersForUser(String username){
		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("select o " + 
								"from Order o inner join o.state os inner join o.client u " +
								 "where (os.name = 'Delivered') " +
									" and (o.client.username = :username )");
		List<Order> orders = (List<Order>)query.setParameter("username", username).getResultList();
		return orders;
	 }
	 
	 //11. Obtiene las órdenes que fueron enviadas luego de 24hs más tarde) no una hora
	 public List <Order> getSentMoreOneHour(){
		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("select o " +  
									"from Order o inner join o.stateHistory os " +
									 "where (os.name = 'Pending') and exists ("
									 							+ "select ord "
									 							+ "from Order ord inner join ord.stateHistory ordst "
									 							+ "where (o.id = ord.id) and "
									 							+ "(ordst.name = 'Send') and "
									 							+ "((ordst.date - os.date) >= 60*24) )" );
		 														//hicimos 60*24 porque nos dijiste que devuelve en minutos
		 List <Order> orders = (List<Order>)query.getResultList();
		 return orders;

	 }
	 
	 public List <Order> getDeliveredOrdersSameDay(){
		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("select o " +  
									"from Order o inner join o.state os " +
									  "where (os.name = 'Delivered') and (os.date = o.dateOfOrder)");
		 List <Order> orders = (List<Order>)query.getResultList();
		 return orders;
	 }
	 
	

	 public List <User> get5LessDeliveryUsers(){
		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("select u " + 
								"from Order o  inner join o.deliveryUser u " +
								  "group by o.deliveryUser.id "+
								  "order by count(o)asc");
		List<User> users = (List<User>)query.setMaxResults(5).getResultList();
		return users;
	 }

     public Product getBestSellingProduct() {
    	 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("select p " + 
								"from Order o inner join o.products pc inner join pc.product p " +
								  "group by p.id "+
								  "order by count(o) desc ");
		
		 Product product = (Product)query.setMaxResults(1).getSingleResult();
		return product;
     }
	 
    
 	public List<Product> getProductsOnePrice() {
 		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select p " + 
							"from Product p inner join p.priceHistory ph " +
							"where not exists ("+
											"select prod "+
											"from Product prod inner join prod.priceHistory pri "+
											"where (p.id=prod.id) and " +
											"(pri.oldPrice <> ph.oldPrice))");
		List<Product> products = (List<Product>)query.getResultList();
		return products;
 	}
 	
 	

 	public List<Product> getProductIncreaseMoreThan100() {
 		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
									"select p "+
									"from Product p inner join p.priceHistory ph "+
									"where ((ph.oldPrice * 2) <= p.price) ");
		List<Product> products = (List<Product>)query.getResultList();
		return products;
 	}
 	
 	
 	public Supplier getSupplierLessExpensiveProduct() {
 		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("select s " + 
											"from Product p "
											+"inner join p.supplier s "
											+"inner join p.priceHistory pri "   			
								  			+ "order by pri.oldPrice asc ");
		
		 Supplier sup = (Supplier)query.setMaxResults(1).getSingleResult();
		return sup;
 	}
 	

 	public List<Supplier> getSuppliersDoNotSellOn(Date day) {
 		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(	"select sup "
											+ "from Supplier sup "
											+ "where not exists ("
															+"select s "  
															+"from Order o "
															+ "inner join o.products pc "
															+ "inner join pc.product p "
															+ "inner join p.supplier s "
															+ "where o.dateOfOrder = :day and "
															+ "s.id = sup.id )" );
		List<Supplier> supps = (List<Supplier>)query.setParameter("day", day).getResultList();
		return supps;
 	}
 	
 	
 	public List<Product> getSoldProductsOn(Date day) {
 		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(	"select prod "
											+ "from Product prod "
											+ "where exists ("
															+"select p "  
															+"from Order o "
															+ "inner join o.products pc "
															+ "inner join pc.product p "		
															+ "where o.dateOfOrder = :day and "
															+ "p.id = prod.id )" );
		List<Product> prod = (List<Product>)query.setParameter("day", day).getResultList();
		return prod;
 	}
 	
 	
 	public List<Order> getOrdersCompleteMorethanOneDay() {
 		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("select o " +  
									"from Order o inner join o.stateHistory os " 
									 + "where (os.name = 'Delivered') and "
									  + "date(os.date) >= (date(o.dateOfOrder) + 1)" );
									 
		 List <Order> orders = (List<Order>)query.getResultList();
		 return orders;
		 
		 
 		
 	}
 	

 	public List<Object[]> getProductsWithPriceAt(Date day) {
 		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select prod, price.oldPrice " +  
								"from Product prod inner join prod.priceHistory price " +
								  "where (price.startDate <= :day) and not exists ("+
													"select p "+
								  					"from Product p inner join p.priceHistory pri "+
													"where (p.id=prod.id) and " +
								  					"(pri.startDate <= :day) and "+
								  					 " (pri.startDate > price.startDate))");
		
		List <Object[]> obj = (List<Object[]>)query.setParameter("day", day).getResultList();
		return obj;
 	}
 	

 	public List<Product> getProductsNotSold() {
 		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select p " + 
								"from Product p " +
								  "where p.id not in ("+
											"select p.id "+
								  			"from Order o inner join o.products pc inner join pc.product p)");
		
		List <Product> product = (List<Product>)query.getResultList();
		return product;
 	}
 	
 	
 	public List<Order> getOrderWithMoreQuantityOfProducts(Date day) {
 		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select distinct o " + 
										"from Order o inner join o.products pc " +
										"where o.dateOfOrder = :day "+
								  		"group by o.id "+
								  		"order by sum(pc.product)desc");
		
		List <Order> orders = (List<Order>)query.setMaxResults(1).setParameter("day", day).getResultList();
		return orders;
 	}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
