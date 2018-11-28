package cn.itcast.core.bean.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BuyerCart implements Serializable{

	private List<BuyerItem> items = new ArrayList<>();
	
	public void addItem(BuyerItem item){
		if(items.contains(item)){
			for (BuyerItem bi : items) {
				if(bi.equals(item)){
					bi.setAmount(bi.getAmount() + item.getAmount());
				}
			}
		}else{
			
			items.add(item);
		}
	}

	public List<BuyerItem> getItems() {
		return items;
	}

	public void setItems(List<BuyerItem> items) {
		this.items = items;
	}
	
	@JsonIgnore
	public Integer getProductAmount(){
		Integer result = 0;
		for (BuyerItem buyerItem : items) {
			result += buyerItem.getAmount();
		}
		
		return result;
	}
	@JsonIgnore
	public Float getProductPrice(){
		Float result = 0f;
		for (BuyerItem buyerItem : items) {
			result += buyerItem.getAmount()*buyerItem.getSku().getPrice();
		}
		return result;
	}
	
	@JsonIgnore
	public Float getFee(){
		Float result = 0f;
		if(getProductPrice() < 88){
			result = 5f;
		}
			
		return result;
	}
	@JsonIgnore
	public Float getTotalPrice(){
		return getFee() + getProductPrice();
	}
}
