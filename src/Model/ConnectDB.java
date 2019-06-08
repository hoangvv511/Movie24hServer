package Model;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class ConnectDB {
	public static String loginWithUsernameAndPassword(String username, String password) {
		String getPassword = getPasswordByUsername(username);
		Document userDocument = new Document();
		if (getPassword.isEmpty()) {
			userDocument.append("code", "404");
			userDocument.append("user", "");
			userDocument.append("message", "Tài khoản không tồn tại");
		} else {
			if (getPassword.equals(password)) {
				userDocument.append("code", "200");
				userDocument.append("user", getUserByUsername(username));
				userDocument.append("message", "Đăng nhập thành công");
			} else {
				userDocument.append("code", "404");
				userDocument.append("user", "");
				userDocument.append("message", "Mật khẩu không chính xác");
			}
		}
		return userDocument.toJson();
	}

	public static String registerUser(String username, String password, String name) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("WebPhim");
		MongoCollection<Document> mongoCollection = database.getCollection("Users");
		List<String> listUser = getAllUsername();
		List<Integer> favorite = new ArrayList<Integer>();
		Document userDocument = new Document();
		if (listUser.contains(username)) {
			userDocument.append("code", "404");
			userDocument.append("message", "Tài khoản tồn tại");
		} else {
			Document user = new Document().append("Username", username).append("Password", password)
					.append("Name", name).append("Favorite", favorite);

			mongoCollection.insertOne(user);
			userDocument.append("code", "200");
			userDocument.append("message", "Đăng ký thành công");
		}
		mongoClient.close();
		return userDocument.toJson();
	}

	public static String changePassword(String username, String password, String newPassword) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("WebPhim");
		MongoCollection<Document> mongoCollection = database.getCollection("Users");
		Bson query = Filters.eq("Username", username);
		Document userDocument = new Document();
		String oldPassword = getPasswordByUsername(username);
		if (oldPassword.equals(password)) {
			mongoCollection.updateOne(query, Updates.set("Password", newPassword));
			userDocument.append("code", "200");
			userDocument.append("message", "Đổi mật khẩu thành công");
		} else {
			userDocument.append("code", "404");
			userDocument.append("message", "Mật khẩu không chính xác");
		}
		mongoClient.close();
		return userDocument.toJson();
	}

	@SuppressWarnings("unchecked")
	public static String getUserByUsername(String username) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Bson query = Filters.eq("Username", username);
		Iterator<Document> its = ConnectMongo(mongoClient, "Users", query, null, 0, 0);
		String userString = null;
		while (its.hasNext()) {
			Document it = its.next();
			String name = it.getString("Name");
			List<Integer> favorite = (List<Integer>) it.get("Favorite");
			Document user = new Document().append("Username", username).append("Name", name).append("Favorite",
					favorite);
			userString = user.toJson();
		}
		mongoClient.close();
		return userString;
	}

	public static List<String> getAllUsername() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Iterator<Document> its = ConnectMongo(mongoClient, "Users", null, null, 0, 0);
		List<String> listUser = new ArrayList<String>();
		while (its.hasNext()) {
			Document it = its.next();
			String username = it.getString("Username");
			listUser.add(username);
		}
		mongoClient.close();
		return listUser;
	}

	public static String getPasswordByUsername(String username) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Bson query = Filters.eq("Username", username);
		Iterator<Document> its = ConnectMongo(mongoClient, "Users", query, null, 0, 0);
		String password = "";
		while (its.hasNext()) {
			Document it = its.next();
			password = it.getString("Password");
		}
		mongoClient.close();
		return password;
	}

	@SuppressWarnings("unchecked")
	public static List<Integer> updateFavorite(String username, int insert, int delete) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("WebPhim");
		MongoCollection<Document> mongoCollection = database.getCollection("Users");
		Bson query = Filters.eq("Username", username);
		Iterator<Document> its = ConnectMongo(mongoClient, "Users", query, null, 0, 0);
		List<Integer> favorite = new ArrayList<Integer>();
		while (its.hasNext()) {
			Document it = its.next();
			favorite = (List<Integer>) it.get("Favorite");
			if (insert != -1) {
				favorite.add(insert);
			}
			if (delete != -1) {
				favorite.remove(new Integer(delete));
			}
			mongoCollection.updateOne(query, Updates.set("Favorite", favorite));
		}
		mongoClient.close();
		return favorite;
	}

	public static List<String> getAllFilm(String sortString, int skip, int limit) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		BasicDBObject sort = new BasicDBObject(sortString, -1);
		Iterator<Document> its = ConnectMongo(mongoClient, "Films", null, sort, skip, limit);
		List<String> listFilm = new ArrayList<String>();
		while (its.hasNext()) {
			Document it = its.next();
			int Id = it.getInteger("Id");
			String Name = it.getString("Name");
			String Thumnail = it.getString("Thumnail");
			String Subtitle = it.getString("Subtitle");
			Document film = new Document().append("Id", Id).append("Name", Name).append("Thumnail", Thumnail)
					.append("Subtitle", Subtitle);
			listFilm.add(film.toJson());
		}
		mongoClient.close();
		return listFilm;
	}

	public static int getCountAllFilm() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Iterator<Document> its = ConnectMongo(mongoClient, "Films", null, null, 0, 0);
		int count = 0;
		while (its.hasNext()) {
			its.next();
			count++;
		}
		mongoClient.close();
		return count;
	}

	public static String parseStringToDate(Date date) {
		SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy");
		formattedDate.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateString = formattedDate.format(date);
		return dateString;
	}

	public static List<String> getFilmByCountry(String countryString, String sortString, int skip, int limit) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Bson query = Filters.regex("Country", countryString);
		BasicDBObject sort = new BasicDBObject(sortString, -1);
		Iterator<Document> its = ConnectMongo(mongoClient, "Films", query, sort, skip, limit);
		List<String> listFilm = new ArrayList<String>();
		while (its.hasNext()) {
			Document it = its.next();
			int Id = it.getInteger("Id");
			String Name = it.getString("Name");
			String Thumnail = it.getString("Thumnail");
			String Subtitle = it.getString("Subtitle");
			Document film = new Document().append("Id", Id).append("Name", Name).append("Thumnail", Thumnail)
					.append("Subtitle", Subtitle);
			listFilm.add(film.toJson());
		}
		mongoClient.close();
		return listFilm;
	}

	public static int getCountFilmByCountry(String countryString) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Bson query = Filters.regex("Country", countryString);
		Iterator<Document> its = ConnectMongo(mongoClient, "Films", query, null, 0, 0);
		int count = 0;
		while (its.hasNext()) {
			its.next();
			count++;
		}
		mongoClient.close();
		return count;
	}

	public static List<String> getFilmByType(String typeString, String sortString, int skip, int limit) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Bson query = Filters.regex("Type", typeString);
		BasicDBObject sort = new BasicDBObject(sortString, -1);
		Iterator<Document> its = ConnectMongo(mongoClient, "Films", query, sort, skip, limit);
		List<String> listFilm = new ArrayList<String>();
		while (its.hasNext()) {
			Document it = its.next();
			int Id = it.getInteger("Id");
			String Name = it.getString("Name");
			String Thumnail = it.getString("Thumnail");
			String Subtitle = it.getString("Subtitle");
			Document film = new Document().append("Id", Id).append("Name", Name).append("Thumnail", Thumnail)
					.append("Subtitle", Subtitle);
			listFilm.add(film.toJson());
		}
		mongoClient.close();
		return listFilm;
	}

	public static int getCountFilmByType(String typeSring) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Bson query = Filters.regex("Type", typeSring);
		Iterator<Document> its = ConnectMongo(mongoClient, "Films", query, null, 0, 0);
		int count = 0;
		while (its.hasNext()) {
			its.next();
			count++;
		}
		mongoClient.close();
		return count;
	}

	public static String getFilmById(int Id) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Bson query = Filters.eq("Id", Id);
		Iterator<Document> its = ConnectMongo(mongoClient, "Films", query, null, 0, 0);
		String filmById = null;
		while (its.hasNext()) {
			Document film = its.next();
			Date date = film.getDate("Date");
			String dateString = parseStringToDate(date);
			film = film.append("Date", dateString);
			filmById = film.toJson();
		}
		mongoClient.close();
		return filmById;
	}

	public static String getListFilmByListId(List<Integer> listId) {
		if (listId.size() == 0) {
			return "";
		}
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Bson query = null;
		if (listId.size() == 1) {
			query = Filters.eq("Id", listId.get(0));
		} else {
			List<Bson> listFilter = new ArrayList<Bson>();
			for (int Id : listId) {
				Bson filter = Filters.eq("Id", Id);
				listFilter.add(filter);
			}
			query = Filters.or(listFilter);
		}
		Iterator<Document> its = ConnectMongo(mongoClient, "Films", query, null, 0, 0);
		List<String> listFilm = new ArrayList<String>();
		for (int i = 0; i < listId.size(); i++) {
			listFilm.add("");
		}
		while (its.hasNext()) {
			Document it = its.next();
			int Id = it.getInteger("Id");
			String Name = it.getString("Name");
			String Thumnail = it.getString("Thumnail");
			String Subtitle = it.getString("Subtitle");
			Document film = new Document().append("Id", Id).append("Name", Name).append("Thumnail", Thumnail)
					.append("Subtitle", Subtitle);

			int index = listId.indexOf(Id);
			listFilm.remove(index);
			listFilm.add(index, film.toJson());
		}
		mongoClient.close();
		return listFilm.toString();
	}

	public static List<String> getFilmByName(String name, String sortString, int skip, int limit) {
		name = replaceName(name);
		name = removeAccent(name);
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Pattern regex = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
		List<Bson> listFilter = new ArrayList<Bson>();

		String[] arr = name.split("\\s");
		for (int i = 0; i < arr.length; i++) {
			regex = Pattern.compile(arr[i], Pattern.CASE_INSENSITIVE);
			Bson filter = Filters.or(Filters.regex("Name", regex), Filters.regex("NameNoTone", regex));
			listFilter.add(filter);
		}

		Bson query = Filters.and(listFilter);
		BasicDBObject sort = new BasicDBObject(sortString, -1);
		Iterator<Document> its = ConnectMongo(mongoClient, "Films", query, sort, skip, limit);

		List<String> listFilm = new ArrayList<String>();
		while (its.hasNext()) {
			Document it = its.next();
			int Id = it.getInteger("Id");
			String Name = it.getString("Name");
			String Thumnail = it.getString("Thumnail");
			String Subtitle = it.getString("Subtitle");
			Document film = new Document().append("Id", Id).append("Name", Name).append("Thumnail", Thumnail)
					.append("Subtitle", Subtitle);
			listFilm.add(film.toJson());
		}
		mongoClient.close();
		return listFilm;
	}

	public static int getCountFilmByName(String name) {
		name = replaceName(name);
		name = removeAccent(name);
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Pattern regex = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
		List<Bson> listFilter = new ArrayList<Bson>();

		String[] arr = name.split("\\s");
		for (int i = 0; i < arr.length; i++) {
			regex = Pattern.compile(arr[i], Pattern.CASE_INSENSITIVE);
			Bson filter = Filters.or(Filters.regex("Name", regex), Filters.regex("NameNoTone", regex));
			listFilter.add(filter);
		}

		Bson query = Filters.and(listFilter);
		Iterator<Document> its = ConnectMongo(mongoClient, "Films", query, null, 0, 0);

		int count = 0;
		while (its.hasNext()) {
			its.next();
			count++;
		}
		mongoClient.close();
		return count;
	}

	public static List<Integer> getListIdByName(Pattern name) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Bson query = Filters.regex("Name", name);
		Iterator<Document> it = ConnectMongo(mongoClient, "Films", query, null, 0, 0);
		List<Integer> listId = new ArrayList<Integer>();
		while (it.hasNext()) {
			Document film = it.next();
			int Id = film.getInteger("Id");
			listId.add(Id);
		}
		mongoClient.close();
		return listId;
	}

	public static List<Integer> getListIdByNameNoTone(Pattern name) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Bson query = Filters.regex("NameNoTone", name);
		Iterator<Document> it = ConnectMongo(mongoClient, "Films", query, null, 0, 0);
		List<Integer> listId = new ArrayList<Integer>();
		while (it.hasNext()) {
			Document film = it.next();
			int Id = film.getInteger("Id");
			listId.add(Id);
		}
		mongoClient.close();
		return listId;
	}

	public static String replaceName(String name) {
		char[] keys = { '-', '_', '&', '%' };
		for (char key : keys) {
			name = name.replace(key, ' ');
		}
		name = name.trim();
		return name;
	}

	public static List<Integer> deleteDuplicateListId(List<Integer> listIdOld) {
		ArrayList<Integer> listIdNew = new ArrayList<>();
		for (int i = 0; i < listIdOld.size(); i++) {
			if (!listIdNew.contains(listIdOld.get(i))) {
				listIdNew.add(listIdOld.get(i));
			}
		}
		return listIdNew;
	}

	public static String removeAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	@SuppressWarnings("unchecked")
	public static List<String> getAllComment(int id) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		Bson query = Filters.eq("Id", id);
		Iterator<Document> it = ConnectMongo(mongoClient, "Films", query, null, 0, 0);
		List<String> listComment = new ArrayList<String>();
		while (it.hasNext()) {
			Document document = it.next();
			List<Document> commentDocuments = (List<Document>) document.get("Comment");
			for (Document commentDocument : commentDocuments) {
				listComment.add(commentDocument.toJson());
			}
		}
		mongoClient.close();
		return listComment;
	}

	@SuppressWarnings("unchecked")
	public static String addComment(int id, String name, String content, String date) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("WebPhim");
		MongoCollection<Document> mongoCollection = database.getCollection("Films");
		Bson query = Filters.eq("Id", id);
		Iterator<Document> it = ConnectMongo(mongoClient, "Films", query, null, 0, 0);
		while (it.hasNext()) {
			Document document = it.next();
			List<Document> commentDocuments = (List<Document>) document.get("Comment");
			int Id = 0;
			for (Document commentDocument : commentDocuments) {
				Id = commentDocument.getInteger("Id");
			}
			Id++;
			Document commentDocument = new Document();
			commentDocument.append("Id", Id);
			commentDocument.append("Name", name);
			commentDocument.append("Content", content);
			commentDocument.append("Date", date);
			commentDocuments.add(commentDocument);
			mongoCollection.updateOne(Filters.eq("Id", id), Updates.set("Comment", commentDocuments));
		}
		mongoClient.close();
		Document deleteComment = new Document();
		deleteComment.append("code", "200");
		deleteComment.append("message", "Thêm comment thành công");
		return deleteComment.toJson();
	}

	@SuppressWarnings("unchecked")
	public static String deleteComment(int id, int idComment) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("WebPhim");
		MongoCollection<Document> mongoCollection = database.getCollection("Films");
		Bson query = Filters.eq("Id", id);
		Iterator<Document> it = ConnectMongo(mongoClient, "Films", query, null, 0, 0);
		while (it.hasNext()) {
			Document document = it.next();
			List<Document> commentDocuments = (List<Document>) document.get("Comment");
			for (Document commentDocument : commentDocuments) {
				if (commentDocument.getInteger("Id") == idComment) {
					commentDocuments.remove(commentDocument);
					break;
				}
			}
			mongoCollection.updateOne(Filters.eq("Id", id), Updates.set("Comment", commentDocuments));
		}
		mongoClient.close();
		Document deleteComment = new Document();
		deleteComment.append("code", "200");
		deleteComment.append("message", "Xóa comment thành công");
		return deleteComment.toJson();

	}

	public static Iterator<Document> ConnectMongo(MongoClient mongoClient, String collection, Bson query,
			BasicDBObject sort, int skip, int limit) {
		MongoDatabase database = mongoClient.getDatabase("WebPhim");
		MongoCollection<Document> mongoCollection = database.getCollection(collection);
		FindIterable<Document> iterDoc = null;
		if (query != null) {
			iterDoc = mongoCollection.find(query).sort(sort).skip(skip).limit(limit);
		} else {
			iterDoc = mongoCollection.find().sort(sort).skip(skip).limit(limit);
		}
		Iterator<Document> it = iterDoc.iterator();
		return it;
	}

	public static void main(String[] args) {
//		List<Integer> listId = new ArrayList<Integer>();
//		listId.add(43);
//		listId.add(3589);
//		listId.add(18);
//		listId.add(17);
//		listId.add(19);
//		System.out.println(getListFilmByListId(listId));
	}
}