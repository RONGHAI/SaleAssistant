package com.ecbeta.common.util.email;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import sun.misc.BASE64Encoder;

public class HtmlHelper {
	public enum ResourceType {
		JAVASCRIPT("js"), CSS("css");
		private String jsType; 
		private ResourceType(String jsType) {
			this.jsType = jsType;
		}
		public String jsType(){
			return jsType;
		}
	};
	
	public final static String  loadResource(ResourceType resourceType, String content){
		StringBuilder sb = new StringBuilder();  
		if(resourceType == ResourceType.JAVASCRIPT){ 
			sb.append("<script language=\"javascript1.2\">").append("\n");
		}else if(resourceType == ResourceType.CSS ){
			sb.append("<style type=\"text/css\">").append("\n");
		}  
		 
		sb.append(content);
         
		if(resourceType == ResourceType.JAVASCRIPT){
			sb.append("</script>\n");   
		}else if(resourceType == ResourceType.CSS){
			sb.append("</style>\n");   
		} 
		return sb.toString();
		
		
	}
	/**
	 
	 * @param reader
	 * @return
	 * @throws IOException
	 * 
 
	 */
	public final static String encode(String file) throws IOException{ 
	     return encode(new File(file));
	}
	
	
	
 

	
	
	/**
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 * 
	  
	 */
	private final static String encode( byte[] array ) throws IOException{ 
	/*	BASE64Encoder encoder = new BASE64Encoder();
		String line = null;
		
		StringBuffer sb  = new StringBuffer();
		
	 while( (line = reader.readLine()) != null){
			sb.append(encoder.encode(line.getBytes()) ); 
		} 
		
		return sb.toString(); */
		
		return  new BASE64Encoder().encode(array);
		
		 
	/*	while( (line = reader.readLine()) != null){
			sb.append(line ); 
		} 
		return encoder.encode(sb.toString().getBytes()); 
*/	}
	/**
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 * 
	  
	 */
	public final static String encode(File file) throws IOException{ 
		 BufferedImage image = ImageIO.read(file);   
	     ByteArrayOutputStream baos = new ByteArrayOutputStream();   
	     ImageIO.write(image, "png", baos);  
	     return encode(baos.toByteArray());
	}
	
	public static void main(String args[]){
		
		try {
			System.out.println(encode("C:/1297912793593_line60003221.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public final static String  loadResource(String path,ResourceType resourceType){
		StringBuilder sb = new StringBuilder();  
		 
		try
        {
            ReadFile rf = new ReadFile(path);
            ArrayList<String> lines = rf.read();
            for(String x : lines){
            	if(resourceType == ResourceType.CSS ){
         			if(x.contains("@charset \"utf-8\";"))continue; 
         			if( x.startsWith("Q_") && !x.startsWith(".")){
             			x += ".";
             		} 
         		}  
            	sb.append(x).append("\n");
            }
        }
        catch (Exception e)
        {
            System.out.println (e.getMessage());    
        }
     
		return loadResource(resourceType, sb.toString() );
	}
	//<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	
	public static String  header(){
		StringBuilder sb = new StringBuilder();  
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> ");
		sb.append("<html><head>\n");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");		
		
	 
		
	 	return sb.toString();
	} 
	
	public static String changePanelJavascript(){
		StringBuilder sb = new StringBuilder();  
		sb.append("function hasAttribute(_input, attri){\n");
		sb.append("  	 if((!!_input) && _input.getAttribute(attri) != undefined){\n");
		sb.append("       return true;\n");
		sb.append("   	 } \n");
		sb.append("   	 return false;\n");
		sb.append("}\n");

		sb.append("function changePanel(index){\n");
		sb.append("		var all_divs = document.getElementsByTagName('div');\n");
		sb.append("		var headers = [];\n");
		sb.append("	var contents = [];\n");
		sb.append("	for(var i = 0 ; i<all_divs.length; i++){\n");
		sb.append("		if(hasAttribute(all_divs[i],'data-type')){\n");
		sb.append("			var x =  all_divs[i].getAttribute('data-type');\n");
		sb.append("			if(x == 'header'){\n");
		sb.append("				headers.push(all_divs[i]);\n");
		sb.append("			}else if(x == 'content'){\n");
		sb.append("				contents.push(all_divs[i]);\n");
		sb.append("			}\n");
		sb.append("		}\n");
		sb.append("	}\n");
			
		sb.append("		for(var i = 0 ;i< headers.length; i++){\n");
		sb.append("		if(i == index ){\n");
		sb.append("			headers[i].className   = 'focus';\n");
		sb.append("			contents[i].style.display = '';\n");
		sb.append("		}else{\n");
		sb.append("			headers[i].className   = 'base';\n");
		sb.append("			contents[i].style.display = 'none';\n");
		sb.append("		} \n");
		sb.append("		} \n");

		sb.append("	}\n");
		return sb.toString();
	}
	
	public static String title(String title){ 
		return "<title>"+title+" </title>"; 
	} 
	public static String endHeader(){
		return "</head><body>";
	} 
	public static String endHtml(){
		return "</body></html>";
	}
	
	public static String content(String[] panels, String[] content , int panelIndex , boolean tabs){
		StringBuilder sb = new StringBuilder();    
		sb.append("<div width=\"100%\"  class=\"Q_reportBox\"> \n");	
		sb.append("	<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"reportTable\">");
		sb.append("		 <tr>\n");
		sb.append("			 <td align=\"left\" valign=\"top\" width=\"99%\">");
		sb.append("            <div width=\"100%\"  class=\"reportTopBox\">");
		
		if(tabs){
			for(int i = 0 ; i< content.length ; i++){
				sb.append(" <div data-type='header' id='header_"+i+"' onclick=\"javascript:changePanel('"+i+"');\" class='"+(panelIndex== i ?"focus":"base")+"'>");
				sb.append(panels[i]);			
				sb.append(" </div>");
			}
			sb.append("			<div class=\"last\">&nbsp;</div>");
		} 
		sb.append(" </div><!-- end  reportTopBox -->");
 
		for(int i = 0 ; i< content.length ; i++){ 
			sb.append(" <div data-type='content' style='width:100%;clear:both;display:"+(panelIndex== i ?"block":"none")+"'  id='content_"+i+"'  >");
			sb.append(content[i]);			
			sb.append(" </div>"); 
		}  
		sb.append("  </td></tr></table>");
		sb.append("</div>");
		return sb.toString();
	}
	
	private static String appHomeDir = System.getProperty("catalina.home") ;// + AppConfig.getProperty("appPath");
	 
	public static String filePath(String compchartimage){ 
			return appHomeDir + compchartimage;
	}
		
	/*public static String getParentPath(){
		AppConfig.getInstance();
		String path = appHomeDir + AppConfig.getProperty("curvePath");
		return path;
	}*/
	
	public static String html(String title, String resoupath[], HtmlHelper.ResourceType [] types, String[] panles, String[] contents){
		 
		return  html(title, resoupath, types, "", panles, contents, true);
		
	}
	
	public static String html(String title, String resoupath[],  
							HtmlHelper.ResourceType [] types, 
							String  content,
							String[] panles, String[] contents , boolean tabs){
		 
		StringBuilder sb = new StringBuilder();  
		sb.append(HtmlHelper.header()); 
		sb.append(HtmlHelper.title(title));
		if(types ==null ){
			types = new HtmlHelper.ResourceType[0];
		} 
		if(resoupath == null){
			resoupath = new String[0];
		}
		if(tabs){
			sb.append(loadResource(ResourceType.JAVASCRIPT, changePanelJavascript())); 
		} 
		sb.append(loadResource(resoupath,types ));
		
	 	sb.append(HtmlHelper.endHeader());  
	 	sb.append(content); 
		sb.append(HtmlHelper.content(panles  , contents ,  0  , tabs)); 
		sb.append(HtmlHelper.endHtml()) ;
		
		//System.out.println(sb);
		return sb.toString();
		
	}

	public static String loadResource(  String resoupath[],  
			HtmlHelper.ResourceType [] types ){
		if(types ==null ){
			types = new HtmlHelper.ResourceType[0];
		} 
		if(resoupath == null){
			resoupath = new String[0];
		}
		StringBuilder sb = new StringBuilder();  
		for(int i = 0 ; i<resoupath.length ;  i++){
			sb.append(HtmlHelper.loadResource(resoupath[i], i>=types.length? null : types[i])); 
		} 
		return sb.toString();
	}
	
	public static String html(String title,String resource,  String  content  ){

		StringBuilder sb = new StringBuilder();  
		sb.append(HtmlHelper.header()); 
		sb.append(HtmlHelper.title(title));
	 
		sb.append(resource == null ?"":resource); 
		
		sb.append(HtmlHelper.endHeader());  
		sb.append(content);  
		sb.append(HtmlHelper.endHtml()) ;  
		return sb.toString();

	}	
	
	
	public static String htmlWithoutTabs(String title, String resoupath[], HtmlHelper.ResourceType [] types, String[] panles, String[] contents){
		  
		return  html(title, resoupath, types,  "",panles, contents, false);
		
	}
	
/*	public static ByteArrayDataSource createDataSource(PDFExporter exporter){ 
		 exporter.setUsePersonalOutputStream(true); 
		 exporter.export();
		 try {
			ByteArrayDataSource dataSource  = new ByteArrayDataSource(exporter.getOut().getBout().toByteArray(), ByteArrayDataSource.APP_PDF );
			return dataSource;
		 } catch (IOException e) { 
			e.printStackTrace();
		 } 
		return null;
	}*/
	
	/*public static ByteArrayDataSource createDataSource(JXLExcelExporter exporter){ 
		 exporter.setUsePersonalOutputStream(true); 
		 exporter.export();
		 try {
			ByteArrayDataSource dataSource  = new ByteArrayDataSource(exporter.getOut().getBout().toByteArray(), ByteArrayDataSource.APP_EXCEL );
			return dataSource;
		 } catch (IOException e) { 
			e.printStackTrace();
		 } 
		return null;
	}
	
	public static ByteArrayDataSource createDataSource(ExcelExporter exporter){ 
		 exporter.setUsePersonalOutputStream(true); 
		 exporter.export();
		 try {
			ByteArrayDataSource dataSource  = new ByteArrayDataSource(exporter.getOut().getBout().toByteArray(), ByteArrayDataSource.APP_EXCEL );
			return dataSource;
		 } catch (IOException e) { 
			e.printStackTrace();
		 } 
		return null;
	}
	
	public static ByteArrayDataSource createDataSource(XLSXExporter exporter,boolean isXML){ 
		 exporter.setUsePersonalOutputStream(true); 
		 exporter.export();
		 try {
			ByteArrayDataSource dataSource  = new ByteArrayDataSource(exporter.getOut().getBout().toByteArray(), isXML? ByteArrayDataSource.APP_EXCEL_XML:ByteArrayDataSource.APP_EXCEL );
			return dataSource;
		 } catch (IOException e) { 
			e.printStackTrace();
		 } 
		return null;
	}*/
}
