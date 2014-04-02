package com.ecbeta.common.core;

import static com.ecbeta.common.constants.Constants.AA_REFRESH_ZONES_NAME;
import static com.ecbeta.common.constants.Constants.ACTION_NAME;
import static com.ecbeta.common.constants.Constants.BTN_OPTION;
import static com.ecbeta.common.constants.Constants.JSON_REFRESH_TYPE;
import static com.ecbeta.common.constants.Constants.PROGRESS_PAGE_POSTBACK;
import static com.ecbeta.common.constants.Constants.REFRESH_TYPE;
import static com.ecbeta.common.constants.Constants.UNDER_LINE;
import static com.ecbeta.common.core.reflect.BingReflectUtils.automaticBindingJsonParams;
import static com.ecbeta.common.core.reflect.BingReflectUtils.automaticBindingParams;
import static com.ecbeta.common.core.reflect.BingReflectUtils.findAction;
import static com.ecbeta.common.core.reflect.BingReflectUtils.findActionMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONObject;

import com.ecbeta.common.constants.Constants;
import com.ecbeta.common.core.annotation.Action;
import com.ecbeta.common.core.annotation.Actions;
import com.ecbeta.common.core.servlet.CoreServlet;
import com.ecbeta.common.core.viewer.BaseViewer;
import com.ecbeta.common.core.viewer.JsonModel;
import com.ecbeta.common.core.viewer.bean.ExportInformationBean;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import com.ecbeta.common.core.viewer.bean.PanelTab;
import java.io.BufferedReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractWorker {
    public static boolean debug = false;

    public static final boolean _SHOW_EXCEPTION = true;
    private static final int _PROCESS_STATUS_IGNORE = 0, _PROCESS_STATUS_SUCCESS = 1,
    _PROCESS_STATUS_NOMETHOD = 2, _PROCESS_STATUS_FAIL = 3;
    private final static Class<?> [][] PARAMETER_TYPES_ARRAY = new Class[][]{null,  new Class[] { String.class },
                new Class[] {HttpServletRequest.class, String.class },  new Class[] { String.class , HttpServletRequest.class } };




    private static final Logger logger =   Logger.getLogger(AbstractWorker.class.getName()) ;

    public static void main (String[] rags) {
    }




    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext servletContext;
    private NavigationBean navigationBean;
    private String jspPath;
    public String btnClicked;
    public boolean needForwordToJsp = true;
    // private static Map<String, Boolean> autoBindingFlagCache = new
    // HashMap<String, Boolean>();








    public void addRefreshZone (String zones) {
        if (zones == null || zones.trim().equals("")) return;
        String s = (String) this.getRequest().getAttribute(AA_REFRESH_ZONES_NAME);
        if (s == null) {
            this.setRefreshZones(zones);
            return;
        }
        if (s.endsWith(",")) {
            this.setRefreshZones(s + zones);
        } else {
            this.setRefreshZones(s + "," + zones);
        }
    }

    public String allRefreshZone () {
        return "result,sourcePanelZone,topNavigationZone";
    }

    protected void bindJsonParams (HttpServletRequest request, String btnClicked, String actionMethod) {
        automaticBindingJsonParams(request, btnClicked, actionMethod, this.getServicer());
        automaticBindingJsonParams(request, btnClicked, actionMethod, this.getOtherServicers());
    }

    protected void bindParams (HttpServletRequest request, String btnClicked, String actionMethod) {
        if (this.getServicer() != null) {
            this.getServicer().beforeBinding(btnClicked);
        }
        automaticBindingParams(request, btnClicked, actionMethod, this.getServicer());
        automaticBindingParams(request, btnClicked, actionMethod, this.getOtherServicers());
        // TODO;
    }
/*
    public void bindParamsDisplayAndSort (HttpServletRequest request) {
    //    this.getServicer().getBaseServicerParameterBean().bindTopBart(request, this.getServicer().getBaseServicerParameterBean().getTopBarID("TopBar"));
    }

    public void changeDecimalAction () {
        this.setRefreshZones("result");
        this.getServicer().setReportDecimalControlList(TopBar2StateBean.parseOnlyDecimal(this.getRequest(), this.getServicer().getBaseServicerParameterBean().getTopBarID("TopBar")).getValue());

    }

    public void changePaginatorAction () {
        PaginatorStateBean stateBean = this.getServicer().getPaginatorStateBean();
        stateBean.parse(this.getRequest());
        this.getServicer().handlePaginator(this.getServicer().getPanelIndex());
        this.setRefreshZones("panelZone,result");

    }*/

/*    public void changePanelAction () {
        this.setRefreshZones("result,topNavigationZone,sortDialogZone,filterDialogZone,displayDialogZone");

    }

    public void changeSortDisplayModeAction () {
        this.setRefreshZones("result,sourcePanelZone");

    }*/

/*    public void changeTimeAction () {

    }*/
    public void clearResultAction () {
        this.getServicer().destory();
        this.getServicer().getBaseServicerParameterBean().setDisplayStage(false);
        this.setRefreshZones(this.allRefreshZone());
    }

    /**
     * this method only dispatch btnClicked action, don't write another
     * logic in this method. all method should be use Action as suffix w/o
     * or with btnClick as parameter as neccersy.
     *
     * @param btnClicked
     * @return
     */

    public String createAction (String btnClicked) {
        if (btnClicked == null || btnClicked.trim().equals("")) return null;
        String methodName;
        if ("resetDialog".equals(btnClicked)) {
            methodName = "resetDialog" + ACTION_NAME;
        } else if (btnClicked.endsWith("Dialog")) {
            methodName = "popupDialog" + ACTION_NAME;
        } else if (btnClicked.startsWith("rspModule")) {
            methodName = "changeRSPModule" + ACTION_NAME;
        } else {
            methodName = btnClicked.replaceAll(UNDER_LINE, "");
            if (methodName.endsWith(ACTION_NAME)) {} else {
                methodName = methodName + ACTION_NAME;
            }
        }
        if (btnClicked.startsWith("refresh")) {
            String s = btnClicked.replace("refresh", "");
            if (s.length() >= 1) {
                s = s.substring(0, 1).toLowerCase() + s.substring(1);
                this.addRefreshZone(s + "Zone");
            }
        }
        if (methodName.charAt(1) >= 'A' && methodName.charAt(1) <= 'Z') {
            return methodName;
        }
        return methodName.substring(0, 1).toLowerCase() + methodName.substring(1);

    }


    public void dispatchAction (String btnClicked) {

    }

  /*  @SuppressWarnings("unchecked")
    public void displayRefreshAction () {
        this.getServicer().updateDisplay(this.btnClicked);
        this.setRefreshZones("result");
    }
*/
    private void doActionAnnotation (Action annotation) {
        if (StringUtils.isNotEmpty(annotation.refreshZones())) {
            this.addRefreshZone(annotation.refreshZones());
        }
        this.needForwordToJsp = annotation.needForwordToJsp();
        if (annotation.clearResult()) {
            this.clearResultAction();
        }
    }

    private void doActionAnnotation (Method method, String action) {
        if (method == null) return;
        if (method.isAnnotationPresent(Action.class)) {
            Action annotation = (Action) method.getAnnotation(Action.class);
            this.doActionAnnotation(annotation);
        } else if (method.isAnnotationPresent(Actions.class)) {
            Actions actions = (Actions) method.getAnnotation(Actions.class);
            Action act = findAction(actions, action);
            if (act != null) {
                this.doActionAnnotation(act);
            }
        }
    }


    protected boolean enableActionAnnotation () {
        return false;
    }

/*    public void exportExcelAction () {


    }*/
/*
    public void exportPDFAction () {
        this.needForwordToJsp = false;
    }*/

/*    public void filterRefreshAction () {
        this.getServicer().processFilter();
        this.setRefreshZones("result");
        //
    }
*/
    private boolean findAnnotationInClassLevel ( Class<?> ownerClass, String action) {
        if (ownerClass.isAnnotationPresent(Actions.class)) {
            Actions actions = (Actions) ownerClass.getAnnotation(Actions.class);
            Action act = findAction(actions, action);
            if (act != null) {
                this.doActionAnnotation(act);
                return true;
            }
        }
        return false;
    }

   /* public void downloadExcelAction(){

    }*/

    protected void forwardToJsp(String jsp) throws IOException, ServletException{
        this.getServletContext().getRequestDispatcher(jspPath+jsp).forward(getRequest(), getResponse());
    }






    public String getAppName () {
        String workerName = this.getWORKER_NAME();
        return workerName.substring(0, workerName.length() - "Worker".length());
    }

    private Object[][] getArgs(HttpServletRequest request, String btnClicked){
        return new Object[][]{
                null,
                new Object[]{btnClicked},
                new Object[]{request, btnClicked},
                new Object[]{btnClicked, request},
        };
    }

    /**
     * @return the btnClicked
     */
    public String getBtnClicked () {
        return btnClicked;
    }

    public String getClearResultActions () {
        return "";
    }

    public ExportInformationBean getExportInformationBean(){
        return null;
    }

    /**
     * @return the fORM_NAME
     */
    public abstract String getFORM_NAME ();


    /**
     * @return the jSP_TOGO
     */
    public String getJSP_TOGO(){
        return this.getJSP_TOGO_PERIX()+".jsp";
    }

    public abstract String getJSP_TOGO_PERIX ();

    public String getJspGoto () {
        return getJSP_TOGO();
    }

    /**
     * @return the navigationBean
     */
    public NavigationBean getNavigationBean () {
        return navigationBean;
    }

    public String getNavTier(){
        if(this.navigationBean != null){
            return this.navigationBean.getNavTier("_");
        }
        return "";
    }

    public Object[] getOtherServicers () {
        return null;
    }


    public PanelTab[] getPanels(){
        return this.getServicer().getPanels();
    }

    public String getRenderHTML () {
        BaseViewer viewer = this.getViewer(true);
        if (viewer != null) {
            if (!this.hasData()) {
                return "";
            }
            try {
                viewer.export(new org.apache.commons.io.output.NullOutputStream(), this.getJSP_TOGO_PERIX() + ".html");
                String s = viewer.toHTML();
                return s;
            } catch (Exception e) {
                 logger.log(Level.WARNING, "Render HTML", e);
            }
        }
        return "";
    }

    public HttpServletRequest getRequest () {
        return request;
    }

    public HttpServletResponse getResponse () {
        return response;
    }

    /**
     * This is main servicer
     * @return
     */
    public abstract AbstractServicer getServicer ();

    public ServletContext getServletContext () {
        return servletContext;
    }


    public String getUrl () {
        StringBuilder url = new StringBuilder();
        if(this.navigationBean == null){
            this.navigationBean = new NavigationBean();
        }
        url.append(this.navigationBean.getUrl(this.request.getContextPath()));
        url.append("&");
        //url.append(Constants.SRC_JSP).append("=").append(this.getJSP_TOGO()).append("&");
        url.append(REFRESH_TYPE).append("=").append(JSON_REFRESH_TYPE).append("&");
        url.append(Constants.BTN_OPTION).append("=");
        return url.toString();
    }


     public String getUrl (String type, String action) {
        StringBuilder url = new StringBuilder();
        if(this.navigationBean == null){
            this.navigationBean = new NavigationBean();
        }
        url.append(this.navigationBean.getUrl(this.request.getContextPath()));
        url.append("&");
        //url.append(Constants.SRC_JSP).append("=").append(this.getJSP_TOGO()).append("&");
        url.append(REFRESH_TYPE).append("=").append(type).append("&");
        url.append(Constants.BTN_OPTION).append("=").append(action);
        return url.toString();
    }

    public BaseViewer getViewer (boolean isHTML) {
        return null;
    }

    /**
     * @return the wORKER_NAME
     */
    public String getWORKER_NAME () {
        return this.getClass().getSimpleName();
    }

    public boolean hasData (){
        return true;
    }

    public void init (HttpServletRequest request, HttpServletResponse response,  NavigationBean navigationBean, String jspPath, CoreServlet servlet, ServletContext servletContext) {
        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
        this.btnClicked = request.getParameter(BTN_OPTION);
        this.jspPath = jspPath;
    }

    public boolean isDebug () {
        return debug;
    }
    public boolean isUseActionFromServicer () {
        return true;
    }
    public void otherClickedAction () {
        this.setRefreshZones("result");
    }
/*    public void popupDialogAction () {
        this.setRefreshZones(this.btnClicked + "Zone," + this.btnClicked + "BottomZone");
    }*/

    public void processButtonClickedAction (String action) {
        try{
            Object[] _instance = this.isUseActionFromServicer() ?  new Object[]{this, this.getServicer()} : new Object[]{this};
            int []_processStatus = new int[_instance.length];
            Arrays.fill(_processStatus,  _PROCESS_STATUS_IGNORE);
            Object[][]  args = this.getArgs(this.getRequest(), btnClicked);

            outer: for(int c = 0; c < _instance.length; c++){
                for(int  i = 0 ; i < PARAMETER_TYPES_ARRAY.length && i < args.length; i++){
                    _processStatus[c] = this.processButtonClickedAction(action, _instance[c].getClass() , _instance[c], PARAMETER_TYPES_ARRAY[i], args[i]);
                    if( _processStatus[c] != _PROCESS_STATUS_NOMETHOD){
                        break outer;
                    }
                }
            }
            if(_processStatus[0] != _PROCESS_STATUS_SUCCESS && _processStatus[0] != _PROCESS_STATUS_IGNORE){
                this.otherClickedAction();
                this.dispatchAction(this.btnClicked);
            }
            if ( !"clearResultAction".equals(action) && StringUtils.isNotEmpty(this.getClearResultActions()) && (this.getClearResultActions().contains(this.btnClicked) || this.getClearResultActions().contains(action) )) {
                this.clearResultAction();
            }
        }catch(Exception e){
            logger.log(Level.WARNING, null, e);
        }
    }

    protected void setJsonContentType() {
        this.getResponse().setContentType("application/json; charset=UTF-8");
        this.getResponse().setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");
        this.getResponse().addHeader("Cache-Control", "post-check=0, pre-check=0");
        this.getResponse().setHeader("Pragma", "no-cache");
    }

    protected void returnJSON(Object o) {
        this.setJsonContentType();
        try{
            if(o instanceof JsonModel){
                 this.getResponse().getWriter().write( ((JsonModel)o).toJson());
            }

          //  this.getResponse().getWriter().write( new Gson.toJson(o).toString());
        }catch(Exception e){
            logger.log(Level.WARNING, null, e);
            //this.getResponse().getWriter().write(JSONArray.fromObject(o).toString());
        }
    }

   private transient Object result;
   private int  processButtonClickedAction (String action, Class<?> ownerClass, Object instance , Class<?>[] _classTypes, Object[] args) {
        if (action == null) return _PROCESS_STATUS_IGNORE;
        if (enableActionAnnotation() && findAnnotationInClassLevel(ownerClass, action)) {
            return _PROCESS_STATUS_SUCCESS;
        }
        String key = ownerClass.getName() + "_" + action;
        Method method = null;
        try {
            if (enableActionAnnotation() && method == null) {
                method = findActionMethod(ownerClass, action);
            }
            boolean withBtnPara = false;
            if (null == method) {
                method = ownerClass.getMethod(action, _classTypes);
                withBtnPara = true;
            }
            this.result = withBtnPara ? method.invoke(instance, args) : method.invoke(instance);
            logger.log(Level.INFO, action+" " + method.getName(), method);
            this.doActionAnnotation(method, action);
            return _PROCESS_STATUS_SUCCESS;
        } catch (SecurityException e) {
            this.showException(key + " " + (method ==  null ? "": method.toString() ) , e, true);
        } catch (NoSuchMethodException e) {
            this.showException(key + " " + (method ==  null ? "": method.toString() ) , e, false);
            return _PROCESS_STATUS_NOMETHOD;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            this.showException(key + " " + (method ==  null ? "": method.toString() ) , e, false);
        }
        return _PROCESS_STATUS_FAIL;
    }

    public void processRequest () {
        this.getResponse().setContentType("text/html");
        this.needForwordToJsp = true;
        this.result = null;
        this.setRefreshZones("");
        //HttpServletRequest request = this.getRequest();
        try {
            if (this.getServicer().getExportInformationBean() == null) {
                this.getServicer().setExportInformationBean(this.getExportInformationBean());
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "", e);
        }
        String refresh = request.getParameter(REFRESH_TYPE);
        boolean isJson = (refresh != null && refresh.equals(JSON_REFRESH_TYPE) );
        try{
            try {
                this.btnClicked = request.getParameter(BTN_OPTION);
                if(StringUtils.isEmpty(this.btnClicked)){
                    this.btnClicked = "";
                }

                this.needForwordToJsp = !(refresh != null && refresh.equals(JSON_REFRESH_TYPE) );
                String action = this.createAction(btnClicked);
                if (this.btnClicked.equals(PROGRESS_PAGE_POSTBACK)) {
                } else if (isJson) {
                     this.setJsonContentType();
                    this.bindJsonParams(request, btnClicked, action);
                } else {
                    this.bindParams(request, btnClicked, action);
                }
                if (this.btnClicked != null && !"".equals(this.btnClicked) && (!this.btnClicked.equals("") && (!this.btnClicked.equals(PROGRESS_PAGE_POSTBACK) ) )) {
                    this.processButtonClickedAction(action);
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "", e);
            }
            String zones = request.getParameter(AA_REFRESH_ZONES_NAME);
            if (StringUtils.isNotEmpty(zones)) {
                this.addRefreshZone(zones);
            }
            if (StringUtils.isEmpty(request.getAttribute(AA_REFRESH_ZONES_NAME) + "")) {
                this.addRefreshZone("result");
            }
            this.addRefreshZone("excludeDateTimeZone,footerZone,alwaysRefreshZone");
            if (isJson) {
                this.needForwordToJsp = false;
            }
            if(isJson && this.result != null && StringUtils.isNotEmpty(this.result.toString())){
                returnJSON(this.result);
            }else if (this.needForwordToJsp && (!getResponse().isCommitted() )) {
                try {
                    forwardToJsp(getJSP_TOGO());
                } catch (IOException e) {
                    logger.log(Level.WARNING, "", e);
                }
            }
        }finally{
        }
    }

/*    public void reloadFromSnapshotAction(){
        this.clearResultAction();
    }*/

    public void resetDialogAction () {
        this.setRefreshZones("sourcePanelZone,topNavigationZone,sortDialogZone,filterDialogZone,displayDialogZone");
    }

    public void setDebug (boolean debug) {
        AbstractWorker.debug = debug;
    }
    public void setRefreshZones (String zones) {
        this.getRequest().setAttribute(AA_REFRESH_ZONES_NAME, zones);
    }
    public void setRequest (HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse (HttpServletResponse response) {
        this.response = response;
    }

    public void setServletContext (ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    private void showException(String acc, Exception e, boolean forceOut){
        if (_SHOW_EXCEPTION || forceOut) {
            logger.log(Level.WARNING, "", e);
        }
    }

/*    public void sortRefreshAction () {
    }
 */
    public void submitAction () {
        submitAction(false, false);
    }

    public void submitAction (boolean withOutSort, boolean withOutDisplay) {
        final AbstractServicer servicer = this.getServicer();
        servicer.destory();
        try {
            this.getServicer().retrieveData();
        } catch (Exception e) {
            logger.log(Level.WARNING, "", e);
        }
        if(!withOutSort){
            servicer.sort();
        }
        if(!withOutDisplay){
            servicer.updateDisplay(this.btnClicked);
        }
        servicer.getBaseServicerParameterBean().setDisplayStage(true);
        servicer.getBaseServicerParameterBean().setSelectionPanelStatusOpen(false);
        this.setRefreshZones("");
    }


    public void updateExportInformationBean () {
        this.getServicer().updateExportInformationBean();
    }

    public JSONObject getJSONObject() {
        StringBuilder jb = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "", e);
        }
        try {
            JSONObject jsonObject = JSONObject.fromObject(jb.toString());
            return jsonObject;
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }
        return null;
    }


    public static HashMap<String, String> getJSONError(String msg){
        HashMap<String, String>  map = new HashMap<>();
        map.put("status", "error");
        map.put("message", msg);
        return map;
    }

    public Object recordAction() {
        JSONObject json = this.getJSONObject();
        String cmd = json.get("cmd").toString();
        if (cmd != null) {

        } else if (cmd.equals("get-records")) {
            return getRecordsAction(json);
        } else if (cmd.equals("delete-records")) {
            return deleteRecordsAction(json);
        } else if (cmd.equals("save-records")) {
            return saveRecordsAction(json);
        }
        return getJSONError("Internal Error!");
    }

    public Object getRecordsAction(JSONObject json) {
        return getJSONError("Internal Error!");
    }

    public Object deleteRecordsAction(JSONObject json) {
        return getJSONError("Internal Error!");
    }

    public Object saveRecordsAction(JSONObject json) {
        return getJSONError("Internal Error!");
    }
    
    //public Collection<Long> to


}
