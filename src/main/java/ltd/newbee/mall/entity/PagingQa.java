package ltd.newbee.mall.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class PagingQa {
	private String orderBy;
	private String configName;
	private Integer configRank;
	private Byte cofigType;
	private String Page;
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public Integer getConfigRank() {
		return configRank;
	}
	public void setConfigRank(Integer configRank) {
		this.configRank = configRank;
	}
	public Byte getCofigType() {
		return cofigType;
	}
	public void setCofigType(Byte cofigType) {
		this.cofigType = cofigType;
	}
	public String getPage() {
		return Page;
	}
	public void setPage(String page) {
		Page = page;
	}
	@Override
	public String toString() {
		return "PagingQa [orderBy=" + orderBy + ", configName=" + configName + ", configRank=" + configRank
				+ ", cofigType=" + cofigType + ", Page=" + Page + "]";
	}
	

}
