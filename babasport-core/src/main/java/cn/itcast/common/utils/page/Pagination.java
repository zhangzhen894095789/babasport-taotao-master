/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package cn.itcast.common.utils.page;

import java.util.ArrayList;
import java.util.List;

public class Pagination extends SimplePage {
	private List<?> list;
	private List<String> pageView;

	public Pagination() {
	}

	public Pagination(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	public Pagination(int pageNo, int pageSize, int totalCount, List<?> list) {
		super(pageNo, pageSize, totalCount);
		this.list = list;
	}

	public int getFirstResult() {
		return ((this.pageNo - 1) * this.pageSize);
	}

	public List<?> getList() {
		return this.list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public List<String> getPageView() {
		return this.pageView;
	}

	public void setPageView(List<String> pageView) {
		this.pageView = pageView;
	}

	public void pageView(String url, String params) {
		this.pageView = new ArrayList();

		if (this.pageNo != 1) {
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=1'\"><font size=2>��ҳ</font></a>");
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + (this.pageNo - 1) + "'\"><font size=2>��һҳ</font></a>");
		} else {
			this.pageView.add("<font size=2>��ҳ</font>");
			this.pageView.add("<font size=2>��һҳ</font>");
		}

		if (getTotalPage() <= 10) {
			for (int i = 0; i < getTotalPage(); ++i) {
				if (i + 1 == this.pageNo) {
					this.pageView.add("<strong>" + this.pageNo + "</strong>");
					++i;
					if (this.pageNo == getTotalPage())
						break label1667;
				}
				this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url
						+ "?" + params + "&pageNo=" + (i + 1) + "'\">" + (i + 1) + "</a>");
			}
		} else if (getTotalPage() <= 20) {
			int l = 0;
			int r = 0;
			if (this.pageNo < 5) {
				l = this.pageNo - 1;
				r = 10 - l - 1;
			} else if (getTotalPage() - this.pageNo < 5) {
				r = getTotalPage() - this.pageNo;
				l = 9 - r;
			} else {
				l = 4;
				r = 5;
			}
			int tmp = this.pageNo - l;
			for (int i = tmp; i < tmp + 10; ++i) {
				if (i == this.pageNo) {
					this.pageView.add("<strong>" + this.pageNo + "</strong>");
					++i;
					if (this.pageNo == getTotalPage())
						break label1667;
				}
				this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url
						+ "?" + params + "&pageNo=" + i + "'\">" + i + "</a>");
			}
		} else if (this.pageNo < 7) {
			for (int i = 0; i < 8; ++i) {
				if (i + 1 == this.pageNo) {
					this.pageView.add("<strong>" + this.pageNo + "</strong>");
					++i;
				}
				this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url
						+ "?" + params + "&pageNo=" + (i + 1) + "'\">" + (i + 1) + "</a>");
			}
			this.pageView.add("...");
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + (getTotalPage() - 1) + "'\">" + (getTotalPage() - 1) + "</a>");
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + getTotalPage() + "'\">" + getTotalPage() + "</a>");
		} else if (this.pageNo > getTotalPage() - 6) {
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + 1 + "'\">" + 1 + "</a>");
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + 2 + "'\">" + 2 + "</a>");
			this.pageView.add("...");
			for (int i = getTotalPage() - 8; i < getTotalPage(); ++i) {
				if (i + 1 == this.pageNo) {
					this.pageView.add("<strong>" + this.pageNo + "</strong>");
					++i;
					if (this.pageNo == getTotalPage())
						break label1667;
				}
				this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url
						+ "?" + params + "&pageNo=" + (i + 1) + "'\">" + (i + 1) + "</a>");
			}
		} else {
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + 1 + "'\">" + 1 + "</a>");
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + 2 + "'\">" + 2 + "</a>");
			this.pageView.add("...");

			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + (this.pageNo - 2) + "'\">" + (this.pageNo - 2) + "</a>");
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + (this.pageNo - 1) + "'\">" + (this.pageNo - 1) + "</a>");
			this.pageView.add("<strong>" + this.pageNo + "</strong>");
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + (this.pageNo + 1) + "'\">" + (this.pageNo + 1) + "</a>");
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + (this.pageNo + 2) + "'\">" + (this.pageNo + 2) + "</a>");

			this.pageView.add("...");
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + (getTotalPage() - 1) + "'\">" + (getTotalPage() - 1) + "</a>");
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + getTotalPage() + "'\">" + getTotalPage() + "</a>");
		}
		if (this.pageNo != getTotalPage()) {
			label1667: this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='"
					+ url + "?" + params + "&pageNo=" + (this.pageNo + 1) + "'\"><font size=2>��һҳ</font></a>");
			this.pageView.add("<a href=\"javascript:void(0);\" onclick=\"javascript:window.location.href='" + url + "?"
					+ params + "&pageNo=" + getTotalPage() + "'\"><font size=2>βҳ</font></a>");
		} else {
			this.pageView.add("<font size=2>��һҳ</font>");
			this.pageView.add("<font size=2>βҳ</font>");
		}
		this.pageView.add("��<var>" + getTotalPage()
				+ "</var>ҳ ����<input type='text' id='PAGENO'  size='3' />ҳ <input type='button' id='skip' class='hand btn60x20' value='ȷ��' onclick=\"javascript:window.location.href = '"
				+ url + "?" + params + "&pageNo=' + $('#PAGENO').val() \"/>");
	}
}