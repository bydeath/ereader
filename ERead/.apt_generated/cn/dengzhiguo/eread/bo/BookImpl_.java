//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package cn.dengzhiguo.eread.bo;

import java.sql.SQLException;
import android.content.Context;
import android.util.Log;
import cn.dengzhiguo.eread.db.Book;
import cn.dengzhiguo.eread.db.DataHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public final class BookImpl_
    extends BookImpl
{

    private Context context_;
    private DataHelper dataHelper_;

    private BookImpl_(Context context) {
        context_ = context;
        init_();
    }

    public static BookImpl_ getInstance_(Context context) {
        return new BookImpl_(context);
    }

    private void init_() {
        dataHelper_ = OpenHelperManager.getHelper(context_, DataHelper.class);
        try {
            bookDao = RuntimeExceptionDao.createDao(dataHelper_.getConnectionSource(), Book.class);
        } catch (SQLException e) {
            Log.e("BookImpl_", "Could not create DAO bookDao", e);
        }
        context = context_;
        fileUtil = FileUtil_.getInstance_(context_);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
