package cn.dengzhiguo.eread.bo;

import java.io.File;
import java.util.List;

import cn.dengzhiguo.eread.db.Book;

public interface IBook {

	public void initBooks() throws Exception;
	public List<Book> getAll();
	public void addBook(File file,String name);
	public void saveBook(Book book);
}
