package com.sperkins.soapui.icon;

import java.lang.reflect.Field;
import java.util.Map;

import javax.swing.ImageIcon;

import com.eviware.soapui.support.UISupport;

public class IconManager {

	private static IconManager instance;
	protected Map<String, ImageIcon> iconCache;

	private IconManager() {
		try {
			Field iconCacheField = UISupport.class.getDeclaredField("iconCache");
			iconCacheField.setAccessible(true);
			Object value = iconCacheField.get(null);
			iconCache = (Map<String, ImageIcon>) value;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public void addIcon(String path, ImageIcon icon) {
		iconCache.put(path, icon);
	}

	public ImageIcon getIcon(String path) {
		return iconCache.get(path);
	}

	public static IconManager getInstance() {
		if (null == instance) {
			instance = new IconManager();
		}
		return instance;
	}
}
