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
        <title>${worker.appName} ${worker.navigationBean.i18n}</title>
        <sm:Head controller='${worker}' >
            <script type="text/javascript"> 
                var  sa = sales_assistant;
                sa.login = function(){
                    sales_assistant.post("login", "form",  function(data, state){
                            if(data.user){
                                window.location.reload();
                                window.parent.location.reload();
                            }else{
                                alert("no user");
                            }
                        }, 
                        function(data, state){
                           alert("no user");
                        }
                    );
                }
            </script>
        </sm:Head>
    </head>
    <body class="sa-add">
        <sm:Form id='form' controller="${worker}"> 
            <input type="text" name="user_name" />
            <input type="password" name="password" />
            <br/>
            <button type="button"  onclick="javascript:sa.login()">Login</button>
        </sm:Form> 
        <sm:Foot></sm:Foot>
    </body>
</html>

