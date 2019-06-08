package Common;

public enum Type {
	music("am-nhac", "Âm nhạc"), mystery("bi-an", "Bí ẩn"), war("chien-tranh", "Chiến tranh"),
	war_politics("chien-tranh-chinh-tri", "Chiến tranh, Chính kịch"), drama("chinh-kich", "Chính kịch"),
	family("gia-dinh", "Gia đình"), splash("giat-gan", "Giật gân"), comedy("hai", "Hài"),
	action("hanh-dong", "Hành động"), action_adventure("hanh-dong-phieu-luu", "Hành động, Phiêu lưu"),
	cartoon("hoat-hinh", "Hoạt hình"), horror("kinh-di", "Kinh dị"), virtual("ky-ao", "Kỳ ảo"),
	romantic("lang-man", "Lãng mạn"), historical("lich-su", "Lịch sử"), talkies("noi-chuyen", "Nói chuyện"),
	adventure("phieu-luu", "Phiêu lưu"), period("phim-dai-ky", "Phim dài kỳ"), documentary("tai-lieu", "Tài liệu"),
	reality("thuc-te", "Thực tế"), news("tin-tuc", "Tin tức"), crime("toi-pham", "Tội phạm"),
	children("tre-em", "Trẻ em"), TV("truyen-hinh", "Truyền hình"), western("vien-tay", "Viễn Tây"),
	fantasy("vien-tuong", "Viễn tưởng"), fiction_mythology("vien-tuong-than-thoai", "Viễn tưởng & Thần thoại");

	private String code;
	private String text;

	private Type(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public static String getTypeByCode(String code) {
		for (Type type : Type.values()) {
			if (type.code.equals(code)) {
				return type.text;
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
