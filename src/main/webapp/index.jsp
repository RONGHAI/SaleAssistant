<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="com.ecbeta.common.constants.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>
<c:set value='<%=application.getAttribute(Constants.ALL_NAVIGATIONBEANS)%>' var='navigationBeans' />
<c:set value="${param.forward}" var="navTier" />
<c:set value='level' var="sidebarLevelPrefix"/>
<c:set value='navigationbar' var="sidebarID"/>

<!DOCTYPE html>
<html>
    <head>
        <title>主页</title>
        <sm:Head> 
            <script type="text/javascript"> //<![CDATA[ 
                sale_assistant.loadContent = function(url, title){
                    //w2ui['layout'].load('main', url);
                    $("#mainFrame")[0].src = url;
                };
            //]]>
            </script> 
        </sm:Head>
    </head>
    <body>
        
        <sm:Navigation id='${sidebarID}' navPrefix='${sidebarLevelPrefix}' navigationBeans="${navigationBeans}" w2ui='true' /> 
         
        <div id="layout" style="width: 100%; height: 100%; min-height: 800px"></div>
        <script type="text/javascript"> //<![CDATA[ 
        $(function () {
                var pstyle = 'border: 1px solid #dfdfdf; padding: 5px;';
                $('#layout').w2layout({
                        name: 'layout',
                        padding: 4,
                        panels: [
                                { type: 'top', size: 50, resizable: false, style: pstyle, content: '' },
                                { type: 'left', size: 180, resizable: false, style: pstyle, content: '' },
                                { type: 'main', style: pstyle, content: '<iframe name="mainFrame" style="width:100%;height:100%;" id="mainFrame" frameborder="0"></iframe>' }
                                //,{ type: 'right', size: 200, resizable: true, style: pstyle, content: 'right' }
                        ]
                });
                w2ui.layout.content('left', $().w2sidebar(sa.navigationbar));
                //$("#mainFrame")[0].src = "http://www.google.com";
         });
         
         $(document).ready(function () {
             var sisid = sale_assistant.generateSidebarID("${sidebarLevelPrefix}", "${navTier}");
             if(sisid){
                 sale_assistant.selectSidebar("${sidebarID}" , sisid);
                 sale_assistant.runApp(w2ui.navigationbar.get(sisid));
             }
         });
         
        //]]>
        </script> 
    </body>
</html>

