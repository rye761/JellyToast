package ryebread761.jellytoast;

import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class Main implements IXposedHookZygoteInit{
    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        XModuleResources modRes = XModuleResources.createInstance(startupParam.modulePath, (XResources)XResources.getSystem());
        final Drawable toastBackground = modRes.getDrawable(R.drawable.toast_bg_jellybean);
        XposedHelpers.findAndHookMethod(Toast.class, "show", new XC_MethodHook() {
        	@Override
        	protected void beforeHookedMethod(MethodHookParam param)
        			throws Throwable {
        		Toast toast = (Toast) param.thisObject;
        		LinearLayout layout = (LinearLayout) toast.getView();
        		layout.setBackground(toastBackground);
        		TextView message = (TextView) layout.findViewById(android.R.id.message);
        		message.setPadding(10, 10, 10, 10);
        		message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                message.setTypeface(Typeface.DEFAULT);
        	}
		});
    }
}
