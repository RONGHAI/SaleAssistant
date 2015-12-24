<%@ page language="java"
         
         pageEncoding="UTF-8" import="com.weinyc.sa.common.constants.Constants,
         com.weinyc.sa.core.engine.AbstractController"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>
<c:set value='<%=request.getAttribute(Constants.REQUEST_WORKER_ATTRIBUTE_NAME)%>' var="worker" />
<c:set value="${worker.servicer}" var="servicer" />
<c:set var='controller' value='${worker}' />
<c:set value='${servicer.navigationBeans}' var='navigationBeans' />
<link rel="stylesheet" type="text/css" href="${worker.request.contextPath}/resources/css/sa-add.css"/>
<!DOCTYPE html>
<html>
    <head>
        <title>${worker.appName} </title> <!-- ${worker.navigationBean.i18n} --> 
        <sm:Head controller='${worker}' >
            ${javascript} 
            
            <script type="text/javascript"> 
                if(w2utils){
                    w2utils.settings.currencyPrefix="";
                }
                $(function() {
                      $(document).ready(function() {
                            sales_assistant.initGrid("grid", "grid", '${worker.appName}', "${sm:url(worker, 'json', 'record')}", {
                                unshift: true,
                                clear: false,
                                highlight_new : true,
                                blank_data: {}
                            }, {
                                columns:  sales_assistant.find_columns(${worker.columns}, '${worker.navigationBean.i18n}'),
                                searches: sales_assistant.find_search_columns( ${worker.columns},  '${worker.navigationBean.i18n}')
                            } ,  "${sm:url(worker, 'json', 'max')}");
                      });
                    
                });
            </script>
        </sm:Head>
    </head>
    <body class="sa-add">
        
        <sm:Form id='form' controller="${worker}"> 
            <div id="hiddenInputZone">
               
            </div>
            
            <div id="addNewZone" style="display:none;" class="addnew">
            </div>
            <div id="grid" style="width: 100%; height: 350px; overflow: hidden;"></div>
            
            ${html}
            
        </sm:Form> 
        
        <sm:Foot></sm:Foot>
    </body>
</html>

