package Common;

public enum Country {
	US("US", "Mỹ"), KR("KR", "Hàn Quốc"), GB("GB", "Anh"), FR("FR", "Pháp"), CA("CA", "Canada"), HK("HK", "Hồng Kông"),
	JP("JP", "Nhật Bản"), CN("CN", "Trung Quốc"), TW("TW", "Đài Loan"), IN("IN", "Ấn Độ"), TH("TH", "Thái Lan"),
	AU("AU", "Úc"), VN("VN", "Việt Nam"), DE("DE", "Đức"), SE("SE", "Thụy Điển"), IT("IT", "Ý"),
	HU("HU", "Hungary"), IE("IE", "Ai-len");

	private String code;
	private String text;

	private Country(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public static String getCountryByCode(String code) {
		for (Country country : Country.values()) {
			if (country.code.equals(code)) {
				return country.text;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
