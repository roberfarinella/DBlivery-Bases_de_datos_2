package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity 
@Table (name="Orden")
public class Order{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long idOrder;
	private String address;
	private Float coordX;
	private Float coordY;
	private Date dateOfOrder;
	
	@OneToOne(cascade= CascadeType.ALL,fetch=FetchType.LAZY )
	private User client;
	
	@OneToMany(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private List<ProductCounter> products;
	
	@OneToOne(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private OrderStatus state;
	
	@OneToMany(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private List<OrderStatus> stateHistory;
	
	@OneToOne(cascade= CascadeType.ALL,fetch=FetchType.LAZY) 
	private User deliveryUser;

	public Order(){}
	
	public Order(Date aDateOfOrder, String anAddress, Float aCoordX, Float aCoordY,User aUser) {
		this.setDateOfOrder(aDateOfOrder);
		this.setAddress(anAddress);
		this.setCoordX(aCoordX);
		this.setCoordY(aCoordY);
		this.setClient(aUser);
		this.products= new ArrayList<ProductCounter>();
		this.stateHistory= new ArrayList<OrderStatus>();
		this.setState(new StatePending());
		this.stateHistory.add(this.getState());
	}
	
	public void setId(Long aId) {
		this.idOrder=aId;}
	
	public void setAddress(String anAddress) {
		this.address=anAddress;}
	
	public void setClient(User aUser) {
		this.client=aUser;}
	
	public void setCoordX(Float aCoordX) {
		this.coordX=aCoordX;}
	
	public void setCoordY(Float aCoordY) {
		this.coordY=aCoordY;}
	
	public void setDateOfOrder(Date aDate) {
		this.dateOfOrder=aDate;}
	
	public void setDeliveryUser(User aUser) {
		this.deliveryUser=aUser;}
	
	public void setState(OrderStatus aState) {
		this.state=aState;}

	@Column(name="ID")
	public Long getId() {
		return this.idOrder;}
	
	@Column(name="ADDRESS")
	public String getAddress() {
		return this.address;}
	
	@Column(name="COORDX")
	public Float getCoordX() {
		return this.coordX;}
	
	@Column(name="COORDY")
	public Float getCoordY() {
		return this.coordY;}
	
	@Column(name="CLIENT")
	public User getClient() {
		return this.client;}
	
	@Column(name="DATE_OF_ORDER")
	public Date getDateOfOrder(){
		return this.dateOfOrder;}
	
	@Column(name="PRODUCTS", nullable=true)
	public List<ProductCounter> getProducts(){
		return this.products;}
	
	@Column(name="DELIVERY_USER", nullable=true)
	public User getDeliveryUser() {
		return this.deliveryUser;}
	
	@Column(name="STATE")
	public OrderStatus getState(){
		return this.state;}
	
	@Column(name="STATE_HISTORY")
	public List<OrderStatus> getStatus(){
		return this.stateHistory;}
	
	public void addProduct(Product p, Long quantity) {
		ProductCounter pc = new ProductCounter(p,quantity);
		(this.getProducts()).add(pc);
	}
	
	
	public Order deliverOrder(User aDeliveryUser) {
		if ((this.getState()).canDeliver()) {
			this.setDeliveryUser(aDeliveryUser);
			OrderStatus OS = new StateSend();
			this.setState(OS);
			(this.getStatus()).add(OS);
			}
		return this;
	}
	
	public Order cancelOrder(){
		if ((this.getState()).canCancel()) {
			OrderStatus OS = new StateCancel();
			this.setState(OS);
			(this.getStatus()).add(OS);
		}
		return this;
	}

	public Order finishOrder() {
		if ((this.getState()).canFinish()) {
			OrderStatus OS = new StateDelivered();
			this.setState(OS);
			(this.getStatus()).add(OS);
		}
		return this;
	}
	
}