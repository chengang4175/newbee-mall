package ltd.newbee.mall.entity;

public class HelpNum {
	private Integer reviewId;
	private Long userId;
	public Integer getReviewId() {
		return reviewId;
	}
	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "HelpNum [reviewId=" + reviewId + ", userId=" + userId + "]";
	}

}
