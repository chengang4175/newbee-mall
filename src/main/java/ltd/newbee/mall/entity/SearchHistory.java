package ltd.newbee.mall.entity;

import java.util.Date;

public class SearchHistory {
	private Long id;
	private Long userId;
	private String keyWord;
	private Date date;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "SearchHistory [id=" + id + ", userId=" + userId + ", keyWord=" + keyWord + ", date=" + date + "]";
	}
	

}
