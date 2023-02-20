package ar.edu.unlp.info.bd2.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity 
@Table(name="Price")
public class Price {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long idPrice;
	private Date startDate;
	private Date endDate;
	private Float oldPrice;
	
	public Price(){}
	//public Price(Float aPrice, Date aStartDate, Date aEndDate) {
	public Price(Float aPrice, Date aStartDate) {
		this.setOldPrice(aPrice);
		//this.setEndDate(aEndDate);
		this.setStartDate(aStartDate);
		this.setEndDate(aStartDate);
	}
	public void setId(Long aId) {
		this.idPrice=aId;}
	public void setStartDate(Date aDate) {
		this.startDate=aDate;}
	public void setEndDate(Date aDate) {
		this.endDate=aDate;}
	public void setOldPrice(Float aPrice) {
		this.oldPrice=aPrice;}
	
	@Column(name="ID")
	public Long getId() {
		return this.idPrice;}
	@Column(name="START_DATE")
	public Date getStartDate() {
		return this.startDate;}
	@Column(name="END_DATE")
	public Date getEndDate() {
		return this.endDate;}
	@Column(name="OLD_PRICE")
	public Float getOldPrice() {
		return this.oldPrice;}
}
