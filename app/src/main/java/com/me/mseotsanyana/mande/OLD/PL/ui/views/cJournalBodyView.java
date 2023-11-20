package com.me.mseotsanyana.mande.OLD.PL.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.me.mseotsanyana.mande.domain.entities.models.wpb.cJournalModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.OLD.cConstant;
import com.me.mseotsanyana.mande.framework.utils.CFontManager;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Layout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Resolve;
import com.me.mseotsanyana.placeholderview.annotationlibrary.View;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ChildPosition;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ParentPosition;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

@Layout(R.layout.journal_entry_list)
public class cJournalBodyView {
    private static final String TAG = cJournalBodyView.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.TIMESTAMP_FORMAT_DATE;
    private static NumberFormat cf = cConstant.SA_CURRENCY_FORMAT;

    @ParentPosition
    public int parentPosition;

    @ChildPosition
    public int childPosition;

    @View(R.id.appCompatTextViewDateIcon)
    public TextView appCompatTextViewDateIcon;

    @View(R.id.appCompatTextViewDate)
    public TextView appCompatTextViewDate;

    @View(R.id.appCompatTextViewDescription)
    public TextView appCompatTextViewDescription;

    @View(R.id.appCompatTextViewAmount)
    public TextView appCompatTextViewAmount;

    @View(R.id.appCompatTextViewActionIcon)
    public TextView appCompatTextViewActionIcon;

    private Context context;
    private cJournalModel journalModel;

    private String date;
    private String description;
    private Double amount;

    public cJournalBodyView(Context context, cJournalModel journalModel) {
        this.context = context;
        this.journalModel = journalModel;
        this.date = sdf.format(journalModel.getCreatedDate());
        this.description = journalModel.getDescription();
        this.amount = journalModel.getAmount();
    }

    @Resolve
    public void onResolved() {
        appCompatTextViewDate.setText(date);
        appCompatTextViewDescription.setText(description);
        appCompatTextViewAmount.setText(cf.format(amount));

        /* icon for clock */
        this.appCompatTextViewDateIcon.setTypeface(null, Typeface.NORMAL);
        this.appCompatTextViewDateIcon.setTypeface(
                CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
        //this.appCompatTextViewDateIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.appCompatTextViewDateIcon.setText(context.getResources().getString(R.string.fa_clock));

        /* icon for action */
        this.appCompatTextViewActionIcon.setTypeface(null, Typeface.NORMAL);
        this.appCompatTextViewActionIcon.setTypeface(
                CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
        //this.appCompatTextViewDateIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.appCompatTextViewActionIcon.setText(context.getResources().getString(R.string.fa_ellipsis_h));
    }
}