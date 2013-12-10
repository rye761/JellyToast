package ryebread761.jellytoast;

import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

public class Main implements IXposedHookZygoteInit{
    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        XModuleResources modRes = XModuleResources.createInstance(startupParam.modulePath, (XResources)XResources.getSystem());
        final Drawable toastBackground = modRes.getDrawable(R.drawable.toast_frame_holo);
        XResources.hookSystemWideLayout("android", "layout", "transient_notification", new XC_LayoutInflated() {

            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                TextView toast = (TextView) liparam.view.findViewById(android.R.id.message);
                toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                toast.setTypeface(Typeface.DEFAULT);
                LinearLayout toastLayout = (LinearLayout) liparam.view;
                toastLayout.setBackground(toastBackground);
            }

        });
    }
}
