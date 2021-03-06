//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package cn.dengzhiguo.eread.bo;

import java.sql.SQLException;
import android.content.Context;
import android.util.Log;
import cn.dengzhiguo.eread.db.DataHelper;
import cn.dengzhiguo.eread.db.Newword;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public final class NewwordImpl_
    extends NewwordImpl
{

    private Context context_;
    private DataHelper dataHelper_;

    private NewwordImpl_(Context context) {
        context_ = context;
        init_();
    }

    public static NewwordImpl_ getInstance_(Context context) {
        return new NewwordImpl_(context);
    }

    private void init_() {
        dataHelper_ = OpenHelperManager.getHelper(context_, DataHelper.class);
        try {
            newwordDao = RuntimeExceptionDao.createDao(dataHelper_.getConnectionSource(), Newword.class);
        } catch (SQLException e) {
            Log.e("NewwordImpl_", "Could not create DAO newwordDao", e);
        }
        context = context_;
        fileUtil = FileUtil_.getInstance_(context_);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
