package com.mbds.livreenfolie.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mbds.livreenfolie.R;

public class Helpers {

    public static void showAbout(AppCompatActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new AboutDialog().show(ft, "dialog_about");
    }


    public static void showSettings(AppCompatActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_settings");
        if (prev != null) {
            ft.remove(prev);
        }

        ft.addToBackStack(null);

        new SettingsDialog().show(ft, "dialog_settings");
    }

    public static void showAlert(Context context, String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.setMessage(content)
                .setTitle(title);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static class AboutDialog extends DialogFragment {

        String facebook = "https://web.facebook.com/imedyahaiti";
        String twitter = "https://twitter.com/imedyahaiti";
        String youtube = "https://www.youtube.com/channel/UCFPdwBldy4DTkpZ4qzdrKpw";
        String contact = " imedyagroup@gmail.com";

        public AboutDialog() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout aboutBodyView = (LinearLayout) layoutInflater.inflate(R.layout.about_dialog, null);

            TextView appversion = (TextView) aboutBodyView.findViewById(R.id.app_version_name);

            TextView facebookv = (TextView) aboutBodyView.findViewById(R.id.facebook);
            TextView twitterv = (TextView) aboutBodyView.findViewById(R.id.twitter);
            TextView youtubev = (TextView) aboutBodyView.findViewById(R.id.youtube);
            Button contactv = (Button) aboutBodyView.findViewById(R.id.contact);

            TextView dismiss = (TextView) aboutBodyView.findViewById(R.id.dismiss_dialog);
            dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            facebookv.setPaintFlags(facebookv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            twitterv.setPaintFlags(twitterv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            youtubev.setPaintFlags(youtubev.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            facebookv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(facebook));
                    startActivity(i);
                }

            });

            twitterv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(twitter));
                    startActivity(i);
                }

            });

            youtubev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(String.valueOf(youtube)));
                    startActivity(i);
                }
            });

            contactv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse("mailto:imedyagroup@gmail.com?subject=" + "Contact/Feedback" + "&body=");
                    intent.setData(data);
                    startActivity(intent);
                }
            });

            try {
                PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                String version = pInfo.versionName;
                int versionCode = pInfo.versionCode;
                appversion.setText("Livre En Folie " + version);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return new AlertDialog.Builder(getActivity())
                    .setView(aboutBodyView)
                    .create();
        }

    }

    public static class SettingsDialog extends DialogFragment {
        public SettingsDialog() {

        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout settingView = (LinearLayout) layoutInflater.inflate(R.layout.settings_dialog, null);

            return new AlertDialog.Builder(getActivity())
                    .setView(settingView)
                    .create();
        }
    }

}