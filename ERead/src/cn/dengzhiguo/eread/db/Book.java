package cn.dengzhiguo.eread.db;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="t_bookstore")
public class Book implements  Serializable{
	@DatabaseField(allowGeneratedIdInsert =true,generatedId=true)
	private int id;
	@DatabaseField
	private String file;
	@DatabaseField
	private String name;
	@DatabaseField
	private int lastread;
	@DatabaseField
	private int lastreadpage;
	@DatabaseField
	private int page;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public int getLastread() {
		return lastread;
	}
	public void setLastread(int lastread) {
		this.lastread = lastread;
	}
	public int getLastreadpage() {
		return lastreadpage;
	}
	public void setLastreadpage(int lastreadpage) {
		this.lastreadpage = lastreadpage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
