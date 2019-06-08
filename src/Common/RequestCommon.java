package Common;

public class RequestCommon {
	// page : trang sẽ chuyển đến, với limit = 15
	// sort : sắp xếp giảm dần theo:  Id: ngày đăng; Date: ngày phát hành; Imbd: điểm đánh giá
	
    // Trả về tất cả phim
    public static final String GET_ALL_FILM = "http://localhost:8080/WebPhim/GetAllFilm?page=1&sort=Id";
    
    // Trả về phim theo id : chi tiết phim
    public static final String GET_FILM_BY_ID = "http://localhost:8080/WebPhim/GetFilmById?id=3589";
    
    // Trả về danh sáchphim theo danh sách id
    // Truyền vào listId : danh sách Id phim yêu thích
    public static final String GET_LIST_FILM_BY_LIST_ID = "http://localhost:8080/WebPhim/GetListFilmByListId?listId=43&listId=44&listId=100";
    
    // Trả về danh sách phim theo name hoặc subtitle
    public static final String GET_FILM_BY_NAME = "http://localhost:8080/WebPhim/GetFilmByName?name=conan_movie&page=1&sort=Id";
    
    // Trả về tất cả phim theo quốc gia
    // country : quốc gia theo ENUM Country
    public static final String GET_FILM_BY_COUNTRY = "http://localhost:8080/WebPhim/GetFilmByCountry?country=US&page=1&sort=Id";
    
    // Trả về tất cả phim theo thể loại
    // type : thể loại theo ENUM Type
    public static final String GET_FILM_BY_TYPE = "http://localhost:8080/WebPhim/GetFilmByType?type=am-nhac&page=1&sort=Id";
    
    // Trả về số lượng phim, truyền type hoặc country hoặc name hoặc không truyền gì
    // ?type= | ?country= | ?name=
    public static final String GET_COUNT_FILM = "http://localhost:8080/WebPhim/GetCountFilm";
    
    // Đăng nhập user với username và password
    // Trả về user code 200 nếu đăng nhập thành công
    // Trả về thông báo lỗi code 404 nếu sai mật khẩu hoặc tài khoản không tồn tại
    public static final String LOGIN = "http://localhost:8080/WebPhim/Login?username=binhnv22121997&password=123456";
    
    // Đăng ký user với username, password và name
    // Trả về thông báo code 200 nếu đăng ký thành công
    // Trả về thông báo code 404 nếu tài khoản tồn tại
    public static final String REGISTER = "http://localhost:8080/WebPhim/Register?username=binhnv22121997&password=123456&name=abc";
    
    // Đổi mật khẩu user với username, password cũ và password mới
    // Trả về thông báo code 200 nếu đổi mật khẩu thành công
    // Trả về thông báo code 404 nếu password sai
    public static final String CHANGE_PASSWORD = "http://localhost:8080/WebPhim/ChangePassword?username=binhnv22121997&password=123456&newPassword=abc";
    
    // Update mảng Id phim yêu thích của user
    // ?insert= : thêm Id vào mảng, ?delete= xóa Id có trong mảng
    // Trả về mảng Id sau khi thêm hoặc xóa
    //public static final String UPDATE_FAVORITE = "http://localhost:8080/WebPhim/UpdateFavorite?username=binhnv22121997&delete=10";
    public static final String UPDATE_FAVORITE = "http://localhost:8080/WebPhim/UpdateFavorite?username=binhnv22121997&insert=10";
    
    // Lấy toàn bộ comment
    // ?id= truyền vào Id của phim
    public static final String GET_ALL_COMMENT = "http://localhost:8080/WebPhim/GetAllComment?id=43";
    
    // Thêm 1 comment
    // ?id= truyền vào Id của phim, ?name = truyền vào tên người comment
    // ?content= truyền vào nội dung comment, ?date= truyền vào ngày tháng năm giờ phút giây kiểu String
    public static final String ADD_COMMENT = "http://localhost:8080/WebPhim/AddComment?id=43&name=binh&content=ok&date=22/12";
    
    // Xóa 1 comment
    // ?id= truyền vào Id của phim, ?idComment = truyền vào id comment muốn xóa
    public static final String DELETE_COMMENT = "http://localhost:8080/WebPhim/DeleteComment?id=43&idComment=5";
    
    
}