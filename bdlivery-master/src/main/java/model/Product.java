package model;

import java.util.List;
import java.util.Date;

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

import java.util.ArrayList;
import java.util.Calendar;

@Entity 
@Table(name="Product")
public class Product{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long idProduct;
	private String name;
	private Float weight;
	private Float price;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Supplier supplier;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Price> priceHistory;
	
	private Date startDatePrice;
	
	
	public Product(){}
	
	public Product(String aName, Float aPrice, Float aWeight, Supplier aSupplier){
		this.setName(aName);
		this.setPrice(aPrice);
		this.setWeight(aWeight);
		this.setSupplier(aSupplier);
		Calendar cal=Calendar.getInstance();
		this.setStartDatePrice( cal.getTime()); 
		Price p = new Price(aPrice, getStartDatePrice());
		priceHistory = new ArrayList<Price>();
		(this.getPrices()).add(p);
	}
	public void setId(Long aId) {
		this.idProduct=aId;}
	
	public void setPrice(Float aPrice) {
		this.price=aPrice;}
	
	public void setName(String aName) {
		this.name=aName;}
	
	public void setWeight(Float aWeight) {
		this.weight=aWeight;}
	
	public void setSupplier(Supplier aSupplier) {
		this.supplier=aSupplier;}
	
	public void setStartDatePrice(Date aDate) {
		this.startDatePrice=aDate;
	}
	
	
	
	@Column(name="ID")
	public Long getId() {
		return this.idProduct;}
	
	@Column(name="PRICE")
	public Float getPrice() {
		return this.price;}
	
	@Column(name="NAME")
	public String getName() {
		return this.name;}
	
	@Column(name="WEIGHT")
	public Float getWeight() {
		return this.weight;}
	
	@Column(name="SUPPLIER")
	public Supplier getSupplier() {
		return this.supplier;}
	
	@Column(name="START_DATE_PRICE")
	public Date getStartDatePrice() {
		return this.startDatePrice;}
	
	@Column(name="PRICE_HISTORY")
	public List<Price> getPrices(){
		return this.priceHistory;}
	
	public void changePrice(Float aNewPrice, Date d) {
		this.setPrice(aNewPrice);
		this.setStartDatePrice(d);
		Price p = new Price(aNewPrice, d);
		(this.getPrices()).add(p);
		
		
	}
	
}