package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="Supplier")
public class Supplier{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long idSupplier;
	private String name;
	private String cuil;
	private String address;
	private Float coordX;
	private Float coordY;
	
	public Supplier(){}
	public Supplier(String aName, String aCuil, String aAddress, Float aCoordX, Float aCoordY){
		this.setName(aName);
		this.setCuil(aCuil);
		this.setAddress(aAddress);
		this.setCoordX(aCoordX);
		this.setCoordY(aCoordY);
	}
	public void setId(Long aId) {
		this.idSupplier=aId;}
	public void setName(String aName) {
		this.name=aName;}
	public void setCuil(String aCuil) {
		this.cuil=aCuil;}
	public void setAddress(String aAddress) {
		this.address=aAddress;}
	public void setCoordX(Float aCoordX) {
		this.coordX=aCoordX;}
	public void setCoordY(Float aCoordY) {
		this.coordY=aCoordY;}
	
	@Column(name="ID")
	public Long getId() {
		return this.idSupplier;}
	@Column(name="NAME")
	public String getName() {
		return this.name;}
	@Column(name="CUIL")
	public String getCuil() {
		return this.cuil;}
	@Column(name="ADDRESS")
	public String getAddress() {
		return this.address;}
	@Column(name="COORDX")
	public Float getCoordX() {
		return this.coordX;}
	@Column(name="COORDY")
	public Float getCoordY() {
		return this.coordY;}
	
}