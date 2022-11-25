package com.kevsz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sale_detail")
public class SaleDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;	
	
	private Integer quantity;
	private Double salePrice;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale")
	private Sale sale;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product")
	private Product product;

	public SaleDetail(Integer quantity, Double salePrice, Sale sale, Product product) {
		super();
		this.quantity = quantity;
		this.salePrice = salePrice;
		this.sale = sale;
		this.product = product;
	}
	
	public SaleDetail() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
}
