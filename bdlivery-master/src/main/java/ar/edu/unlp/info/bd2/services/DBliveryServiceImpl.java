package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryRepository;

public class DBliveryServiceImpl implements DBliveryService {
	private DBliveryRepository repository;
	
	public DBliveryServiceImpl(DBliveryRepository repository) {
		this.repository=repository;}
	@Transactional
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product p = new Product( name,  price,  weight,  supplier);
		repository.create(p);
		return p;
	}
	
	@Transactional 
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		Product p = new Product( name,  price,  weight,  supplier, date);
		repository.create(p);
		return p;
	}
	
	
	@Transactional
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier s = new Supplier( name,  cuil,  address,  coordX,  coordY);
		repository.create(s);
		return s;
	}
	@Transactional
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User u = new User ( email,  password,  username,  name,  dateOfBirth);
		repository.create(u);
		return u;
	}
	@Transactional
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException{
		Optional<Product> p = this.getProductById(id);
		if (p.isPresent()){
			p.get().changePrice(price, startDate);
			repository.update(p.get());}
		else {
			throw new DBliveryException("No existe el producto");
		}
		return p.get();
	}
	@Transactional
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY,User client) {
		Order o=new Order( dateOfOrder,  address,  coordX,  coordY, client);
		repository.create(o);
		return o;
	}
	@Transactional
	public Order addProduct (Long order,Long quantity, Product product ){
		Optional<Order> o = this.getOrderById(order);
		o.get().addProduct(product, quantity);
		repository.update(o.get());
		return o.get();
	}
	@Transactional
	public Order deliverOrder(Long id, User deliveryUser) throws DBliveryException{
		Optional<Order> o = this.getOrderById(id);
		if (o.isPresent()) {
			o.get().deliverOrder(deliveryUser);
			this.repository.update(o.get());
			return o.get();
		}
		else {
			throw new DBliveryException("The order do not exist");
		}
			
			
	}
	@Transactional
	public Order deliverOrder(Long id, User deliveryUser, Date aDate) throws DBliveryException{
		Optional<Order> o = this.getOrderById(id);
		if(o.isPresent()) {
			o.get().deliverOrder(deliveryUser,aDate);
			this.repository.update(o.get());
			return o.get();
		}
		else {
			throw new DBliveryException("The order do not exist");
		}
			
	}
	@Transactional
	public Order cancelOrder(Long id) throws DBliveryException{
		Optional<Order> o = this.getOrderById(id);
		if (o.isPresent()) {
			o.get().cancelOrder();
			this.repository.update(o.get());
			return o.get();
		}
		else {
			throw new DBliveryException("The order do not exist");
		}
	}
	@Transactional
	public Order cancelOrder(Long id, Date aDate) throws DBliveryException{
		Optional<Order> o = this.getOrderById(id);
		if (o.isPresent()) {
			o.get().cancelOrder(aDate);
			this.repository.update(o.get());
			return o.get();
		}
		else {
			throw new DBliveryException("The order do not exist");
		}
	}
	@Transactional
	public Order finishOrder(Long id) throws DBliveryException{
		Optional<Order> o = this.getOrderById(id);
		if (o.isPresent()) {
			o.get().finishOrder();
			this.repository.update(o.get());
			return o.get();
		}
		else {
			throw new DBliveryException("The order do not exist");
		}
	}
	@Transactional
	public Order finishOrder(Long id,Date aDate) throws DBliveryException{
		Optional<Order> o = this.getOrderById(id);
		if (o.isPresent()){
			o.get().finishOrder(aDate);
			this.repository.update(o.get());
			return o.get();
		}
		else {
			throw new DBliveryException("The order do not exist");
		}
	}
	@Transactional
	public boolean canCancel(Long id) throws DBliveryException{
		Order o = repository.getOrderById(id);
		return o.canCancel();
	}
	@Transactional
	public boolean canFinish(Long id) throws DBliveryException{
		Order o = repository.getOrderById(id);
		return o.canFinish();
	}
	@Transactional
	public boolean canDeliver(Long id) throws DBliveryException{
		Order o = repository.getOrderById(id);
		return o.canDeliver();
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
	@Transactional
	public List<Order> getAllOrdersMadeByUser(String username){
		List<Order> l = repository.getAllOrdersMadeByUser(username);
		return l;
	}
	
	@Transactional
	public List<User> getUsersSpendingMoreThan(Float amount){
		List<User> l = repository.getUsersSpendingMoreThan(amount);
		return l;
	}
	
	@Transactional
	public List<Supplier> getTopNSuppliersInSentOrders(int n){
		List<Supplier> l = repository.getTopNSuppliersInSentOrders(n);
		return l;
	}
	
	@Transactional
	public List<Product> getTop10MoreExpensiveProducts(){
		List<Product> l = repository.getTop10MoreExpensiveProducts();
		return l;
	}
	
	@Transactional
	public List<User> getTop6UsersMoreOrders(){
		List<User> l = repository.getTop6UsersMoreOrders();
		return l;
	}
	
	@Transactional
	public List <Order> getCancelledOrdersInPeriod(Date startDate, Date endDate){
		List<Order> l = repository.getCancelledOrdersInPeriod( startDate,  endDate);
		return l;
	}
	
	@Transactional
	public List <Order>  getPendingOrders(){
		List<Order> l = repository.getPendingOrders();
		return l;
	}
	
	@Transactional
	public List <Order>  getSentOrders(){
		List<Order> l = repository.getSentOrders();
		return l;
	}
	
	@Transactional
	public List <Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate){
		List<Order> l = repository.getDeliveredOrdersInPeriod( startDate,  endDate);
		return l;
	}
	@Transactional
	public List<Order> getDeliveredOrdersForUser(String username) {
		List<Order> l = repository.getDeliveredOrdersForUser(username);
		return l;
	}
	@Transactional
	public List<Order> getSentMoreOneHour() {
		List<Order> l = repository.getSentMoreOneHour();
		return l;
	}
	@Transactional
	public List<Order> getDeliveredOrdersSameDay() {
		List<Order> l = repository.getDeliveredOrdersSameDay();
		return l;
	}
	@Transactional
	public List<User> get5LessDeliveryUsers() {
		List<User> l = repository.get5LessDeliveryUsers();
		return l;
	}
	@Transactional
	public Product getBestSellingProduct() {
		Product p = repository.getBestSellingProduct();
		return p;
	}
	@Transactional
	public List<Product> getProductsOnePrice() {
		List<Product> l = repository.getProductsOnePrice();
		return l;
	}
	@Transactional
	public List<Product> getProductIncreaseMoreThan100() {
		List<Product> l = repository.getProductIncreaseMoreThan100();
		return l;
	}
	@Transactional
	public Supplier getSupplierLessExpensiveProduct() {
		Supplier s = repository.getSupplierLessExpensiveProduct();
		return s;
	}
	@Transactional
	public List<Supplier> getSuppliersDoNotSellOn(Date day) {
		List<Supplier> l = repository.getSuppliersDoNotSellOn(day);
		return l;
	}
	@Transactional
	public List<Product> getSoldProductsOn(Date day) {
		List<Product> l = repository.getSoldProductsOn(day);
		return l;
	}
	@Transactional
	public List<Order> getOrdersCompleteMorethanOneDay() {
		List<Order> l = repository.getOrdersCompleteMorethanOneDay();
		return l;
	}
	@Transactional
	public List<Object[]> getProductsWithPriceAt(Date day) {
		List<Object[]> l = repository.getProductsWithPriceAt(day);
		return l;
	}
	@Transactional
	public List<Product> getProductsNotSold() {
		List<Product> l = repository.getProductsNotSold();
		return l;
	}
	@Transactional
	public List<Order> getOrderWithMoreQuantityOfProducts(Date day) {
		List<Order> l = repository.getOrderWithMoreQuantityOfProducts(day);
		return l;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}