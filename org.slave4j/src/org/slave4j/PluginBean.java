package org.slave4j;

import java.io.IOException;
import java.net.URL;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class PluginBean
{
  private static String getPluginResourcePath(String filename)
  {
    String path = null;
    URL url = Platform.getBundle("org.slave4j").getEntry("resource/" + filename);
    try
    {
      path = FileLocator.toFileURL(url).getPath();
      
      System.out.println("debug----------" + path);

      path = path.substring(path.indexOf("/") + 1, path.length());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return path;
  }

  public static String getWebRoot()
  {
    return getPluginResourcePath("WebRoot");
  }

  public static String getSrc()
  {
    return getPluginResourcePath("src");
  }
}