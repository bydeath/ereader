package cn.dengzhiguo.eread.bo;

import cn.dengzhiguo.eread.db.Newword;

public interface INewword {

	public Newword findNewword(String word);

	public void addNewword(Newword newword);
}
