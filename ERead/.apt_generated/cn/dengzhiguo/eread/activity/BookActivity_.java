//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package cn.dengzhiguo.eread.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dengzhiguo.eread.R.id;
import cn.dengzhiguo.eread.R.layout;
import cn.dengzhiguo.eread.bo.BookImpl_;
import cn.dengzhiguo.eread.bo.FileUtil_;
import cn.dengzhiguo.eread.widget.EBook;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class BookActivity_
    extends BookActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_book);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        fileUtil = FileUtil_.getInstance_(this);
        bookBo = BookImpl_.getInstance_(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static BookActivity_.IntentBuilder_ intent(Context context) {
        return new BookActivity_.IntentBuilder_(context);
    }

    public static BookActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new BookActivity_.IntentBuilder_(fragment);
    }

    public static BookActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new BookActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        imgPlayam = ((ImageView) hasViews.findViewById(id.imgVoiceAm));
        txtPham = ((TextView) hasViews.findViewById(id.txtPham));
        ebook = ((EBook) hasViews.findViewById(id.ebook));
        layoutTrans = ((RelativeLayout) hasViews.findViewById(id.layoutTranslate));
        txtPhen = ((TextView) hasViews.findViewById(id.txtPhen));
        txtParts = ((TextView) hasViews.findViewById(id.txtParts));
        txtWord = ((TextView) hasViews.findViewById(id.txtWord));
        imgPlayen = ((ImageView) hasViews.findViewById(id.imgVoiceEn));
        {
            View view = hasViews.findViewById(id.imgVoiceEn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BookActivity_.this.clickVoiceEn();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.imgVoiceAm);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BookActivity_.this.clickVoiceAm();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.layoutTranslate);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BookActivity_.this.bookClick();
                    }

                }
                );
            }
        }
        afterView();
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, BookActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, BookActivity_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, BookActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public BookActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent_, requestCode);
            } else {
                if (fragment_!= null) {
                    fragment_.startActivityForResult(intent_, requestCode);
                } else {
                    if (context_ instanceof Activity) {
                        ((Activity) context_).startActivityForResult(intent_, requestCode);
                    } else {
                        context_.startActivity(intent_);
                    }
                }
            }
        }

    }

}
