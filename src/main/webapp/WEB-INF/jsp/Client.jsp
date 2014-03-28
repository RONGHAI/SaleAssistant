<?xml version='1.0' encoding='UTF-8' ?>

<%@ page language="java"  import="com.ecbeta.common.constants.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<c:set value='<%=request.getAttribute(Constants.REQUEST_WORKER_ATTRIBUTE_NAME)%>' var="worker" />
<c:set value="${worker.servicer}" var="servicer" /> 
<c:set value='${servicer.navigationBeans}' var='navigationBeans' />
<!DOCTYPE html>
<html>
    <head>
        <title>${worker.appName}</title>
    </head>
    <body>
        
        
        <div>
            <form class="formnomargin" name="${worker.FORM_NAME}"  id="${worker.FORM_NAME}"  action="<%=Constants.CORE_SERVLET%>" method="post">
                <input type="hidden" name="<%=Constants.REQUEST_WORKER%>" id="<%=Constants.REQUEST_WORKER%>" value="${worker.WORKER_NAME}" />
                <input type="hidden" name="<%=Constants.SRC_JSP%>" id="<%=Constants.SRC_JSP%>" value="${worker.jspGoto}" />
                <input type="hidden" name="<%=Constants.BTN_OPTION%>" id="<%=Constants.BTN_OPTION%>"  value="" /> 
                <input type="hidden" name="<%=Constants.NAV_TIERS%>" id="<%=Constants.NAV_TIERS%>" value="${worker.navTier}" />
                
                
                
                
                <input type='submit' />
            </form>
        </div>
        
    </body>
</html>

