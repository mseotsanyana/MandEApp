package com.me.mseotsanyana.mande.OLD.PL.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.OLD.PL.ui.listeners.logframe.iViewInputListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.OLD.cConstant;
import com.me.mseotsanyana.mande.framework.utils.CFontManager;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Click;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Layout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Resolve;
import com.me.mseotsanyana.placeholderview.annotationlibrary.View;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ChildPosition;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ParentPosition;

import java.text.SimpleDateFormat;

import static com.me.mseotsanyana.mande.R.color.gray;

@Layout(R.layout.user_less_detail_list)
public class cUserBodyView {
    private static final String TAG = cUserBodyView.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    @ParentPosition
    public int parentPosition;

    @ChildPosition
    public int childPosition;

    @View(R.id.circleImageViewUser)
    public de.hdodenhof.circleimageview.CircleImageView circleImageViewUser;

    @View(R.id.textViewName)
    public TextView textViewName;

    @View(R.id.textViewPhone)
    public TextView textViewPhone;

    @View(R.id.textViewEmail)
    public TextView textViewEmail;

    @View(R.id.textViewDeleteIcon)
    public TextView textViewDeleteIcon;

    private Context context;
    private iViewInputListener listener;
    private CUserProfileModel userProfileModel;

    private String name;
    private String surname;
    private String phone;
    private String email;


    public cUserBodyView(Context context, iViewInputListener listener,
                         CUserProfileModel userProfileModel) {
        this.context = context;
        this.listener = listener;
        this.userProfileModel = userProfileModel;
        this.name = userProfileModel.getName();
        this.surname = userProfileModel.getSurname();
        this.phone = userProfileModel.getPhone();
        this.email = userProfileModel.getEmail();
    }

    @Resolve
    public void onResolved() {
        circleImageViewUser.setImageResource(gray);
        textViewName.setText(name +" "+surname);
        textViewPhone.setText(phone);
        textViewEmail.setText(email);

        /* icon for deleting a record */
        this.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewDeleteIcon.setTypeface(
                CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
        this.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));

    }

    @Click(R.id.textViewDeleteIcon)
    void onDeleteIconClick(){
        //listener.onClickDeleteInput(childPosition, userModel.getInputID());
    }

    public void setPlaceHolderViewInputListener(iViewInputListener listener) {
        this.listener = listener;
    }
}