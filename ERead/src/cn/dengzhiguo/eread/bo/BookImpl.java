package cn.dengzhiguo.eread.bo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import cn.dengzhiguo.eread.db.Book;
import cn.dengzhiguo.eread.db.DataHelper;

import com.j256.ormlite.dao.RuntimeExceptionDao;
@EBean
public class BookImpl implements IBook {
	@RootContext
	Context context;
	@Bean(FileUtil.class)
	IFile fileUtil;
	@OrmLiteDao(helper = DataHelper.class, model = Book.class)
	RuntimeExceptionDao<Book, Integer> bookDao;
	@Override
	public void initBooks() throws Exception{
		String[] books=context.getAssets().list("books");
		for(String book:books)
			copyAsset(book);
	}
	private void copyAsset(String filename)throws Exception{
		InputStream input=context.getAssets().open("books/"+filename);
		byte[] data=new byte[input.available()];
		input.read(data);
		File file=fileUtil.writeFile(data, filename);
		StringBuffer bookName=new StringBuffer();
		for(int i=0;i<100&&i<data.length;i++){
			char c=(char)data[i];
			if(c=='\n')
				break;
			bookName.append(c);
		}
		this.addBook(file, bookName.toString());
	}
	
	@Override
	public List<Book> getAll() {
		// TODO Auto-generated method stub
		return bookDao.queryForAll();
	}

	@Override
	public void addBook(File file,String name) {
		if(bookDao.queryForEq("name", name.replaceAll("'", "''")).size()==0){
			Book book=new Book();
			try {
				book.setFile(file.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			book.setLastread(0);
			book.setLastreadpage(0);
			book.setName(name);
			book.setPage(0);
			bookDao.create(book);
		}
	}
	@Override
	public void saveBook(Book book) {
		bookDao.createOrUpdate(book);
	}

}
