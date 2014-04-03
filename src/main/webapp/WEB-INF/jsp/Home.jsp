<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="com.ecbeta.common.constants.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>
<c:set value='<%=request.getAttribute(Constants.REQUEST_WORKER_ATTRIBUTE_NAME)%>' var="worker" />
<c:set value="${worker.servicer}" var="servicer" />
<c:set var='controller' value='${worker}' />
<c:set value='${servicer.navigationBeans}' var='navigationBeans' />
<!DOCTYPE html>
<html>
    <head>
        <title>${worker.appName}</title>
        <sm:Head controller='${worker}' > 
        </sm:Head>
    </head>
    <body>
        
        
        <sm:Navigation id='navigationbar' navigationBeans="${servicer.navigationBeans}" w2ui='true' navTier='${worker.navigationBean.navTier}'/> 
         
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
                                { type: 'main', style: pstyle, content: '' }
                                //,{ type: 'right', size: 200, resizable: true, style: pstyle, content: 'right' }
                        ]
                });
                
                w2ui.layout.content('left', $().w2sidebar(sa.navigationbar));
         });
        //]]>
        </script> 
    </body>
</html>

