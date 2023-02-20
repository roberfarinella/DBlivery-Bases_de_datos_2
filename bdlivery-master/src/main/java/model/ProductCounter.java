package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity 
@Table(name="ProductCounter")
public class ProductCounter {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long idProductCounter;
	private Long quantity ;
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Product product;
	
	public ProductCounter(){}
	public ProductCounter(Product p, Long q) {
		this.setProduct(p);
		this.setQuantity(q);
	}
	public void setId(Long aId) {
		this.idProductCounter=aId;}
	private void setQuantity(Long q) {
		this.quantity=q;}
	private void setProduct(Product p) {
		this.product=p;}
	
	@Column(name="ID")
	public Long getId() {
		return this.idProductCounter;}
	@Column(name="QUANTITY")
	public Long getQuantity() {
		return this.quantity;}
	@Column(name="PRODUCT")
	public Product getProduct() {
		return this.product;}
}
