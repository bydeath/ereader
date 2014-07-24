package cn.dengzhiguo.eread.bo;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import cn.dengzhiguo.eread.db.DataHelper;
import cn.dengzhiguo.eread.db.Newword;

import com.j256.ormlite.dao.RuntimeExceptionDao;
@EBean
public class NewwordImpl implements INewword {
	@RootContext
	Context context;
	@Bean(FileUtil.class)
	IFile fileUtil;
	@OrmLiteDao(helper = DataHelper.class, model = Newword.class)
	RuntimeExceptionDao<Newword, String> newwordDao;
	@Override
	public Newword findNewword(String word) {
		return newwordDao.queryForId(word);
	}

	@Override
	public void addNewword(Newword newword) {
		newwordDao.createOrUpdate(newword);
	}

}
