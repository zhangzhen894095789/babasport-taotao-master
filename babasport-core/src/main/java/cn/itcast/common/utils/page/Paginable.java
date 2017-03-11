/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package cn.itcast.common.utils.page;

public abstract interface Paginable {
	public abstract int getTotalCount();

	public abstract int getTotalPage();

	public abstract int getPageSize();

	public abstract int getPageNo();

	public abstract boolean isFirstPage();

	public abstract boolean isLastPage();

	public abstract int getNextPage();

	public abstract int getPrePage();
}