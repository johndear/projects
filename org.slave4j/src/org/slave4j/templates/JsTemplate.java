package org.slave4j.templates;

import org.slave4j.bean.JsTemplateArgs;

public class JsTemplate
{

	public final String NL = System.getProperties().getProperty("line.separator");

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();

    JsTemplateArgs args = (JsTemplateArgs)argument;
    args.getEntityName();
    args.getJsName();

    stringBuffer.append("(function($){");
    stringBuffer.append(NL);
    stringBuffer.append(NL);
    stringBuffer.append("    function doAjax(url,type,params,callback){"+ NL );
    stringBuffer.append("	     $.ajax({" + NL );
    stringBuffer.append("	         type: type," + NL );
    stringBuffer.append("	         url: url," + NL );
    stringBuffer.append("	 		 data: params," + NL );
    stringBuffer.append("	 		 success: callback" + NL );
    stringBuffer.append("	 	});" + NL );
    stringBuffer.append("	 }");
    stringBuffer.append(NL);
    stringBuffer.append(NL);
    stringBuffer.append("     /** examples:"+ NL );
    stringBuffer.append("	   *" + NL );
    stringBuffer.append("	     	doAjax('','GET',{},function(msg){" + NL );
    stringBuffer.append("	         	alert('result: '+msg);" + NL );
    stringBuffer.append("	 	 	});" + NL );
    stringBuffer.append("	   */");
    stringBuffer.append(NL);
    stringBuffer.append(NL);
    stringBuffer.append("})(JQuery)");
    
    return stringBuffer.toString();
  }
}