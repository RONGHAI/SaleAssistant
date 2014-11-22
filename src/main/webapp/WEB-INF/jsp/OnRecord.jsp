<%@ page language="java"
          import="com.ecbeta.common.constants.Constants,
         com.ecbeta.common.core.AbstractController"%>
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
                                        toolbarSave     : true,
                                        footer: true
                                    },
                                    url : "${sm:url(worker, 'json', 'record')}",
                                    columns: ${worker.columns},
                                    searches : ${worker.columns},
                                    onAdd: function (target,data) {
                                        this.add({ recid: -1 });
                                    },
                                    onSave: function(target, data){
                                      //    alert(eventData);
                                    }
                            });
                            
                            
                      });
                    
                });
            </script>
        </sm:Head>
        <!-- -->
    </head>
    <body class="sa-add">
        
        <sm:Form id='form' controller="${worker}"> 
            <div id="hiddenInputZone">
               
            </div>
            
            <div id="addNewZone" style="display:none;" class="addnew">
                
                
            </div>
            <div id="grid" style="width: 100%; height: 350px; overflow: hidden;"></div>
           
            
        </sm:Form> 
        
        <sm:Foot></sm:Foot>
    </body>
</html>

