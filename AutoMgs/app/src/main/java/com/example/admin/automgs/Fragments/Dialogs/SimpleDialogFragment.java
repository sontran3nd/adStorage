package com.example.admin.automgs.Fragments.Dialogs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.automgs.R;

/**
 * Created by Admin on 10/13/2017.
 */

public class SimpleDialogFragment extends BaseDialogFragment {

    @Override
    protected int setUpLayout() {
        return R.layout.fragment_dialog_simple;
    }

    @Override
    protected void setUpWindowsSize(View rootView, DisplayMetrics metrics) {
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        rootView.setMinimumWidth(width);
        rootView.setMinimumHeight(height);
//        rootView.setMinimumWidth((int) (width * 0.85));
    }

    private BroadcastReceiver broadcastReceiver;
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(SMS_RECEIVED)) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        final SmsMessage[] messages = new SmsMessage[pdus.length];
                        for (int i = 0; i < pdus.length; i++) {
                            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        }
                        if (messages.length > -1) {
                            Toast.makeText(context, "Message recieved: " + messages[0].getMessageBody(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        };

        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    private TextView txtContent;

    @Override
    protected void setupViews(View view) {
        TextView txtSimple = view.findViewById(R.id.fragment_dialog_simple_txt_sample);
        txtSimple.setText("Txt Data Simple!");

        txtContent = view.findViewById(R.id.fragment_dialog_simple_txt_content);
        txtSimple.setText(Html.fromHtml(getResources().getString(R.string.sample)));
    }
}
