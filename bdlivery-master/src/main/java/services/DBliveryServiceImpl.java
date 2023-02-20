package services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import model.Order;
import model.OrderStatus;
import model.Product;
import model.Supplier;
import model.User;
import repositories.DBliveryException;
import repositories.DBliveryRepository;

public class DBliveryServiceImpl implements DBliveryService {
	private DBliveryRepository repository;
	
	public DBliveryServiceImpl(DBliveryRepository repository) {
		this.repository=repository;}
	@Transactional
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product p = new Product( name,  price,  weight,  supplier);
		repository.createProduct(p);
		return p;
	}
	@Transactional
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier s = new Supplier( name,  cuil,  address,  coordX,  coordY);
		repository.createSupplier(s);
		return s;
	}
	@Transactional
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User u = new User ( email,  password,  username,  name,  dateOfBirth);
		repository.createUser(u);
		return u;
	}
	@Transactional
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException{
		Product p = repository.updateProductPrice(id, price, startDate);
		return p;
	}
	@Transactional
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY,User client) {
		Order o=new Order( dateOfOrder,  address,  coordX,  coordY, client);
		repository.createOrder(o);
		return o;
	}
	@Transactional
	public Order addProduct (Long order,Long quantity, Product product ){
		Order o = repository.addProduct(order,quantity,product);
		return o;
	}
	@Transactional
	public Order deliverOrder(Long id, User deliveryUser) throws DBliveryException{
		
			if (this.canDeliver(id)&&( this.getOrderById(id)!= null) ) {
				Order o = repository.deliverOrder(id,deliveryUser);
				return o;}
			else {
				throw new DBliveryException("The order can't be delivered");
			}
			
	}
	@Transactional
	public Order cancelOrder(Long id) throws DBliveryException{
		
		if (this.canCancel(id)&&( this.getOrderById(id)!= null) ) {
			Order o = repository.cancelOrder(id);
			return o;}
		else {
			throw new DBliveryException("The order can't be cancelled");
		}
	}
	@Transactional
	public Order finishOrder(Long id) throws DBliveryException{
		
		if (this.canFinish(id)&&( this.getOrderById(id)!= null) ) {
			Order o = repository.finishOrder(id);
			return o;}
		else {
			throw new DBliveryException("The order can't be finished");
		}
	}
	@Transactional
	public boolean canCancel(Long id) throws DBliveryException{
		Order o = repository.getOrderById(id);
		return (o.getState()).canCancel();
	}
	@Transactional
	public boolean canFinish(Long id) throws DBliveryException{
		Order o = repository.getOrderById(id);
		return (o.getState()).canFinish();
	}
	@Transactional
	public boolean canDeliver(Long id) throws DBliveryException{
		Order o = repository.getOrderById(id);
		if ((o.getProducts()).size() > 0) {
			return (o.getState()).canDeliver();
		}
		return false;
	}
	@Transactional
	public OrderStatus getActualStatus(Long id){
		Order o = repository.getOrderById(id);
		return o.getState();
	}
	@Transactional
	public List<Product> getProductByName(String name){
		List<Product> l = repository.getProductByName(name);
		return l;
	}
	@Transactional
	public Optional<User> getUserById(Long id){
		return Optional.of(repository.getUserById(id));
	}
	
	@Transactional
	public Optional<User> getUserByEmail(String email){
		return Optional.of(repository.getUserByEmail(email));
	}
	@Transactional
	
	public Optional<User> getUserByUsername(String username){
		return Optional.of(repository.getUserByUsername(username));
	}
	
	@Transactional
	public Optional<Product> getProductById(Long id){
		return Optional.of(repository.getProductById(id));
	}
	
	@Transactional
	public Optional<Order> getOrderById(Long id){
		return Optional.of(repository.getOrderById(id));
	}
}