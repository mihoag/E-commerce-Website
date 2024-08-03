package com.hcmus.admin.category.controller;

public class CategoryPageInfo {
   private long  totalElement;
   private long totalPage;

   public CategoryPageInfo() {
	super();
	// TODO Auto-generated constructor stub
   }

public long getTotalElement() {
	return totalElement;
}

public void setTotalElement(long totalElement) {
	this.totalElement = totalElement;
}

public long getTotalPage() {
	return totalPage;
}

public void setTotalPage(long totalPage) {
	this.totalPage = totalPage;
}

}
