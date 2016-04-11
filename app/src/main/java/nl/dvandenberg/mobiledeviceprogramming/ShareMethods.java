package nl.dvandenberg.mobiledeviceprogramming;

import android.content.Context;
import android.content.Intent;

class ShareMethods {
    public static void shareString(Context context, String title, String stringToShare) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, stringToShare);
        context.startActivity(Intent.createChooser(shareIntent, title));
    }
}