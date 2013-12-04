package ryebread761.jellytoast;

import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

public class Main implements IXposedHookZygoteInit{
    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        XModuleResources modRes = XModuleResources.createInstance(startupParam.modulePath, (XResources)XResources.getSystem());
        final Drawable toastBackground = modRes.getDrawable(R.drawable.toast_frame_holo);
        XResources.hookSystemWideLayout("android", "layout", "transient_notification", new XC_LayoutInflated() {

            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                XposedBridge.log("layout inflate handler thingy ran");
                TextView toast = (TextView) liparam.view.findViewById(android.R.id.message);
                toast.setTextAppearance(toast.getContext(), android.R.style.TextAppearance_Small);
                LinearLayout toastLayout = (LinearLayout) liparam.view;
                toastLayout.setBackground(toastBackground);
            }

        });
    }
}
