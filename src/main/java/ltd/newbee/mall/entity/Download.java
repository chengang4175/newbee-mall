package ltd.newbee.mall.entity;
import java.util.Date;
import java.util.Arrays;

public class Download {
	private Integer[] ids;
    private String format;
	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	@Override
	public String toString() {
		return "Download [ids=" + Arrays.toString(ids) + ", format=" + format + "]";
	}


}
