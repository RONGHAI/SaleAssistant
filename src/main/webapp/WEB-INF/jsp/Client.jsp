<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="com.ecbeta.common.constants.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>
<c:set value='<%=request.getAttribute(Constants.REQUEST_WORKER_ATTRIBUTE_NAME)%>' var="worker" />
<c:set value="${worker.servicer}" var="servicer" /> 
<c:set value='${servicer.navigationBeans}' var='navigationBeans' />
<!DOCTYPE html>
<html>
    <head>
        <title>${worker.appName}</title>
        <sm:Head worker='${worker}' >
            
            
            <script type="text/javascript">
                $(function() {
                      $(document).ready(function() {
                             $('#grid').w2grid({ 
                                    name: 'grid', 
                                    //url : "${sm:url(worker, 'json', 'record')}"
                                    columns: [				
                                            { field: 'name', caption: 'Name', size: '20%', sortable: true },
                                            { field: 'wangwang', caption: 'Wangwang', size: '20%', sortable: true },
                                            { field: 'qq', caption: 'QQ', size: '20%', sortable: true },
                                            { field: 'birthday', caption: 'Birthday', size: '120px', sortable: true },
                                            { field: 'gender', caption: 'Gender', size: '120px', sortable: true },
                                            { field: 'phone', caption: 'Phone', size: '120px', sortable: true },
                                    ]
                            });	
                            w2ui['grid'].load('${worker.url}'+'fetchAll');   
                      });
                });
            </script>
        </sm:Head>
    </head>
    <body>
        
        <sm:Navigation navigationBeans="${servicer.navigationBeans}" /> 
        
        <sm:Form id='form' worker="${worker}"> 
            <div id="grid" style="width: 100%; height: 350px; overflow: hidden;"></div>
        </sm:Form> 
        
        <sm:Foot></sm:Foot>
    </body>
</html>

