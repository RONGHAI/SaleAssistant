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
            
            
            <script type="text/javascript">
                $(function() {
                      $(document).ready(function() {
                             $('#grid').w2grid({ 
                                    name: 'grid', 
                                    show : {
                                        toolbar : true,
                                        toolbarColumns  : true,
                                        toolbarSearch   : true,
                                        toolbarDelete   : true,
                                        toolbarAdd      : true,
                                        toolbarSave     : true
                                    },
                                    url : "${sm:url(worker, 'json', 'record')}",
                                    columns: ${worker.columns},
                                    searches : ${worker.columns}
                            });
                            //w2ui['grid'].load("${sm:url(worker, 'json', 'record')}");
                         
                      });
                });
            </script>
        </sm:Head>
    </head>
    <body>
        
        <sm:Form id='form' controller="${worker}"> 
            <div id="grid" style="width: 100%; height: 350px; overflow: hidden;"></div>
        </sm:Form> 
        
        <sm:Foot></sm:Foot>
    </body>
</html>

