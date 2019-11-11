package common;

import android.content.Context;
import android.graphics.Typeface;

public class SweetFonts {
	public static String bHoma="fonts/bhoma.ttf";
	
	public static String bRoya="fonts/broya.ttf";
	public static String bZar="fonts/bzar.ttf";
	public static String bYekan="fonts/byekan.ttf";
	public static String IranSans="fonts/IRANSansMobile.ttf";
	public static Typeface getFont(Context context,String FontName)
	{
		Typeface face=Typeface.createFromAsset(context.getAssets(), FontName);
		return face;
	}
}
