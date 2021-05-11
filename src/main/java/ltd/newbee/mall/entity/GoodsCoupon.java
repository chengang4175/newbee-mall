package ltd.newbee.mall.entity;

public class GoodsCoupon {
	private Long couponId;
	private String couponName;
	private String flag;
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "GoodsCoupon [couponId=" + couponId + ", couponName=" + couponName + ", flag=" + flag + "]";
	}
	
}
