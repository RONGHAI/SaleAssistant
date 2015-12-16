package com.weinyc.sa.core.engine;

import static com.weinyc.sa.core.reflect.BingReflectUtils.automaticBindingJsonParams;
import static com.weinyc.sa.core.reflect.BingReflectUtils.automaticBindingParams;
import static com.weinyc.sa.core.reflect.BingReflectUtils.findAction;
import static com.weinyc.sa.core.reflect.BingReflectUtils.findActionMethod;
import static com.weinyc.sa.common.constants.Constants.AA_REFRESH_ZONES_NAME;
import static com.weinyc.sa.common.constants.Constants.ACTION_NAME;
import static com.weinyc.sa.common.constants.Constants.BTN_OPTION;
import static com.weinyc.sa.common.constants.Constants.CALL_BACK;
import static com.weinyc.sa.common.constants.Constants.JSONP_REFRESH_TYPE;
import static com.weinyc.sa.common.constants.Constants.JSON_REFRESH_TYPE;
import static com.weinyc.sa.common.constants.Constants.PROGRESS_PAGE_POSTBACK;
import static com.weinyc.sa.common.constants.Constants.REFRESH_TYPE;
import static com.weinyc.sa.common.constants.Constants.UNDER_LINE;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.weinyc.sa.core.annotation.Action;
import com.weinyc.sa.core.annotation.Actions;
import com.weinyc.sa.core.servlet.CoreServlet;
import com.weinyc.sa.core.viewer.BaseViewer;
import com.weinyc.sa.core.viewer.bean.ExportInformationBean;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.core.viewer.bean.PanelTab;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.common.constants.Constants;

public abstract class AbstractController {
    public static boolean debug = false;
    public static String QUERY_CMD = "get-records",DELETE_CMD = "delete-records",SAVE_CMD = "save-records";

    public static final boolean _SHOW_EXCEPTION = true;
    private static final int _PROCESS_STATUS_IGNORE = 0, _PROCESS_STATUS_SUCCESS = 1,
    _PROCESS_STATUS_NOMETHOD = 2, _PROCESS_STATUS_FAIL = 3;
    private final static Class<?> [][] PARAMETER_TYPES_ARRAY = new Class[][]{null,  new Class[] { String.class },
                new Class[] {HttpServletRequest.class, String.class },  new Class[] { String.class , HttpServletRequest.class } };




    private static final Logger logger =   Logger.getLogger(AbstractController.class.getName()) ;

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


    public Object convert2JSONP(Object o){
        if(this.isJSONP()){
            String callback = this.callbackFunction(); 
            if(o instanceof JSONObject || o instanceof JSONArray){
                return new StringBuilder(callback).append("(").append( o.toString()).append(")").toString();
            }else{
                return new StringBuilder(callback).append("('").append( o.toString()).append("')").toString();
            }
        }
        return o;
    }
    
    public void updateAuthorizer(Object o){
        this.getRequest().getSession().setAttribute(CoreServlet.authorizer, o);
    }


    public boolean isJSONP(){
        String refresh = this.request.getParameter(REFRESH_TYPE);
        return  (refresh != null && (refresh.equals(JSONP_REFRESH_TYPE)) );
    }
    public String callbackFunction(){
        String callback = this.request.getParameter(CALL_BACK);
        if(StringUtils.isEmpty(callback)){
            callback = "callback";
        }
        return callback;
    }


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
            this.getServicer().beforeBinding(request, btnClicked, actionMethod);
        }
        automaticBindingParams(request, btnClicked, actionMethod, this.getServicer());
        automaticBindingParams(request, btnClicked, actionMethod, this.getOtherServicers());
        
        if (this.getServicer() != null) {
            this.getServicer().afterBinding(request, btnClicked, actionMethod);
        }
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
        this.getServletContext().getRequestDispatcher(this.jspPath+jsp).forward(this.getRequest(), this.getResponse());
    }






    public String getAppName () {
        if(this.navigationBean != null && StringUtils.isNotEmpty(this.navigationBean.getLabel())){
            return this.navigationBean.getLabel();
        }
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
        return this.btnClicked;
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
        return this.getJSP_TOGO();
    }

    /**
     * @return the navigationBean
     */
    public NavigationBean getNavigationBean () {
        return this.navigationBean;
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
        return this.request;
    }

    public HttpServletResponse getResponse () {
        return this.response;
    }

    /**
     * This is main servicer
     * @return
     */
    public abstract AbstractServicer getServicer ();

    public ServletContext getServletContext () {
        return this.servletContext;
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
        this.navigationBean = navigationBean;
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
        if(this.getServicer() != null){
            this.getServicer().beforeProcessing(this.request, this.btnClicked ,action);
        }
        try{
            Object[] _instance = this.isUseActionFromServicer() ?  new Object[]{this, this.getServicer()} : new Object[]{this};
            int []_processStatus = new int[_instance.length];
            Arrays.fill(_processStatus,  _PROCESS_STATUS_IGNORE);
            Object[][]  args = this.getArgs(this.getRequest(), this.btnClicked);

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
        if(this.getServicer() != null){
            this.getServicer().afterProcessing(this.request, this.btnClicked, action);
        }
    }

    
    protected void setJsonContentType(boolean isJSONP) {
        if(isJSONP){
            this.getResponse().setContentType("application/javascript; charset=UTF-8");
        }else{
            this.getResponse().setContentType("application/json; charset=UTF-8");
        }
        this.getResponse().setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");
        this.getResponse().addHeader("Cache-Control", "post-check=0, pre-check=0");
        this.getResponse().setHeader("Pragma", "no-cache");
    }
    
    public static String toString(Object o){
        if(o == null ){
            return null;
        }
        if(o instanceof JSONArray)
            return JSONUtils.toString((JSONArray)o);
        else if(o instanceof JSONObject)
            return JSONUtils.toString((JSONObject)o);
        
        return o.toString();
    }
    
    protected void returnJSON(Object o, boolean isJsonP) {
        this.setJsonContentType(isJsonP);
        if(o != null && StringUtils.isNotEmpty(o.toString())){
        }else{
            return;
        }
        try{
            if(o instanceof JSONObject || o instanceof  JSONArray){
                 this.getResponse().getWriter().write (o.toString());
            }
        }catch(IOException e){
            logger.log(Level.WARNING, null, e);
            try {
                this.getResponse().getWriter().write(JSONArray.fromObject(o).toString());
                return;
            }
            catch (IOException ex) {
               logger.log(Level.WARNING, null, ex);
            }
        }
    }


   private transient Object result;
   private int  processButtonClickedAction (String action, Class<?> ownerClass, Object instance , Class<?>[] _classTypes, Object[] args) {
        if (action == null) return _PROCESS_STATUS_IGNORE;
        if (this.enableActionAnnotation() && this.findAnnotationInClassLevel(ownerClass, action)) {
            return _PROCESS_STATUS_SUCCESS;
        }
        String key = ownerClass.getName() + "_" + action;
        Method method = null;
        try {
            if (this.enableActionAnnotation() && method == null) {
                method = findActionMethod(ownerClass, action);
            }
            boolean withBtnPara = false;
            if (null == method) {
                method = ownerClass.getMethod(action, _classTypes);
                withBtnPara = true;
            }
            logger.log(Level.INFO, action+" " + method.getName(), method);
            this.result = withBtnPara ? method.invoke(instance, args) : method.invoke(instance);
           
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
        String refresh = this.request.getParameter(REFRESH_TYPE);
       /* boolean isJson = (refresh != null && refresh.equals(JSON_REFRESH_TYPE) );*/
        boolean isJson = (refresh != null && (refresh.equals(JSON_REFRESH_TYPE) || refresh.equals(JSONP_REFRESH_TYPE)) );
        try{
            try {
                this.btnClicked = this.request.getParameter(BTN_OPTION);
                if(StringUtils.isEmpty(this.btnClicked)){
                    this.btnClicked = "";
                }

                this.needForwordToJsp = !(refresh != null && refresh.equals(JSON_REFRESH_TYPE) );
                String action = this.createAction(this.btnClicked);
                if (this.btnClicked.equals(PROGRESS_PAGE_POSTBACK)) {
                } else if (isJson) {
                    this.setJsonContentType( refresh.equals(JSONP_REFRESH_TYPE));
                    this.bindJsonParams(this.request, this.btnClicked, action);
                } else {
                    this.bindParams(this.request, this.btnClicked, action);
                }
                if (this.btnClicked != null && !"".equals(this.btnClicked) && (!this.btnClicked.equals("") && (!this.btnClicked.equals(PROGRESS_PAGE_POSTBACK) ) )) {
                    this.processButtonClickedAction(action);
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "", e);
            }
            String zones = this.request.getParameter(AA_REFRESH_ZONES_NAME);
            if (StringUtils.isNotEmpty(zones)) {
                this.addRefreshZone(zones);
            }
            if (StringUtils.isEmpty(this.request.getAttribute(AA_REFRESH_ZONES_NAME) + "")) {
                this.addRefreshZone("result");
            }
            this.addRefreshZone("excludeDateTimeZone,footerZone,alwaysRefreshZone");
            if (isJson) {
                this.needForwordToJsp = false;
            }
            if(isJson && this.result != null && StringUtils.isNotEmpty(this.result.toString())){
                this.returnJSON(this.result,  refresh.equals(JSONP_REFRESH_TYPE));
            }else if (this.needForwordToJsp && (!this.getResponse().isCommitted() )) {
                try {
                    this.forwardToJsp(this.getJSP_TOGO());
                } catch (IOException | ServletException e ) {
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
        AbstractController.debug = debug;
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
        if ( forceOut || _SHOW_EXCEPTION) {
            logger.log(Level.WARNING, "Show All Exception", e);
        }
    }

/*    public void sortRefreshAction () {
    }
 */
    public void submitAction () {
        this.submitAction(false, false);
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
          return JSONUtils.toJSON(this.request);
    }
    
    public static HashMap<String, String> getJSONError(String msg){
        HashMap<String, String>  map = new HashMap<>();
        map.put("status", "error");
        map.put("message", msg);
        return map;
    }
    
    public AbstractServicer getServicer (String swithServicer){
        return this.getServicer();
    }

    
    
    public Object recordAction() {
        JSONObject json = this.getJSONObject();
        String swithServicer = json == null ? null : (String)json.get("servicer");
        if(swithServicer == null){
            swithServicer = this.getRequest().getParameter("servicer");
        }
        String cmd = json == null ? null :(String)json.get("cmd");
        if (cmd == null) {
            return this.getRecordsAction(json, swithServicer);
        } else if (cmd.equals("get-records")) {
            return this.getRecordsAction(json, swithServicer);
        } else if (cmd.equals("delete-records")) {
            return this.deleteRecordsAction(json, swithServicer);
        } else if (cmd.equals("save-records")) {
            return this.saveRecordsAction(json, swithServicer);
        }
        return getJSONError("Internal Error!");
    }

    public Object getRecordsAction(JSONObject json, String swithServicer) {
        JSONArray array = this.getServicer(swithServicer).getJSONArray(json);
        JSONObject map = new JSONObject();
        map.put("status", "success");
        map.put("total", array.size());
        map.put("records", array);
        return map;
    }
    
    public Object listAction(){
        JSONObject json = this.getJSONObject();
        String swithServicer = json == null ? null : (String)json.get("servicer");
        if(swithServicer == null){
            swithServicer = this.getRequest().getParameter("servicer");
        }
        JSONArray array = this.getServicer(swithServicer).getJSONArray(new JSONObject());
        return array;
    }
    

    public Object deleteRecordsAction(JSONObject json, String swithServicer) {
        boolean st = this.getServicer(swithServicer).remove(JSONUtils.toCollection(json, "selected", Long.class));
        JSONObject map = new JSONObject();
        map.put("status", "success");
        logger.info("Delete status " + st);
        map.put("refresh", st);
        return map;
    }

    public Object saveRecordsAction(JSONObject json, String swithServicer) {
        JSONObject map = new JSONObject();
        boolean st = false;
        try{
            st = this.getServicer(swithServicer).saveOrUpdate(JSONUtils.getChanges(json));
            map.put("status", "success");
        }catch(Exception e){
            map.put("message", e.toString()); 
            logger.log(Level.SEVERE, null, e);
            map.put("status", "error");
        }
        map.put("refresh", st);
        return map;
    }
}
