package ltd.newbee.mall.entity;

import java.util.Date;

public class GoodsSale {
	private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String campaign;
	private String cotent1;
	private String cotent2;
	private String cotent3;
	private String cotent4;
	private String cotent5;
	private String flag;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCampaign() {
		return campaign;
	}
	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}
	public String getCotent1() {
		return cotent1;
	}
	public void setCotent1(String cotent1) {
		this.cotent1 = cotent1;
	}
	public String getCotent2() {
		return cotent2;
	}
	public void setCotent2(String cotent2) {
		this.cotent2 = cotent2;
	}
	public String getCotent3() {
		return cotent3;
	}
	public void setCotent3(String cotent3) {
		this.cotent3 = cotent3;
	}
	public String getCotent4() {
		return cotent4;
	}
	public void setCotent4(String cotent4) {
		this.cotent4 = cotent4;
	}
	public String getCotent5() {
		return cotent5;
	}
	public void setCotent5(String cotent5) {
		this.cotent5 = cotent5;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "GoodsSale [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", campaign=" + campaign + ", cotent1=" + cotent1 + ", cotent2=" + cotent2 + ", cotent3=" + cotent3
				+ ", cotent4=" + cotent4 + ", cotent5=" + cotent5 + ", flag=" + flag + "]";
	}
	

}
