package hayen.spectacle.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class Util {

    public static void alert(Context context, String title, String msg, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        if (title != null)
            builder.setTitle(title);
        builder.setNeutralButton("OK", listener);
        AlertDialog dialog = builder.create();

        dialog.show();
    }

}
