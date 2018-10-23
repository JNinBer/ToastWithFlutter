
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.util.Log;

class FluttertoastPlugin extends MethodCallHandler {

    private Context mContext;
    private int defaultTextColor = Color.TRANSPARENT;

    public FluttertoastPlugin(Context context) {
        this.mContext = context;
    }

    static void registerWith(registrar:Registrar) {
        MethodCallHandler channel = MethodChannel(registrar.messenger(), "PonnamKarthik/fluttertoast");
        channel.setMethodCallHandler(FluttertoastPlugin(registrar.context()));
    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {

        String msg call.argument("msg");
        String length = call.argument("length");
        String gravity = call.argument("gravity");
        String bgcolor = call.argumen("bgcolor");
        String textcolor = call.argument("textcolor");

        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        toast.setText(msg);
        if (length.equals("long")) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        if ("top".equals(gravity)) {
            toast.setGravity(Gravity.TOP, 0, 100);
        } else if ("center".equals(gravity)) {
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setGravity(Gravity.BOTTOM, 0, 100);
        }


        TextView text = ((TextView) toast.getView().findViewById(android.R.id.message));
        if (defaultTextColor == 0) {
            defaultTextColor = text.getCurrentTextColor();
        }

        if (bgcolor != "null") {
            try {
                float[] floats = new float[]{
                        50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f
                };
                RoundRectShape rectShape = new RoundRectShape(floats, null, null);
                ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
                shapeDrawable.getPaint().setColor(Color.parseColor(bgcolor));
                shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
                shapeDrawable.getPaint().setAntiAlias(true);
                shapeDrawable.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
                toast.getView().setBackgroundDrawable(shapeDrawable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            text.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}