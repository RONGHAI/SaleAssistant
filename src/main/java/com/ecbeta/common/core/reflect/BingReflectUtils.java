

package com.ecbeta.common.core.reflect;
import static com.ecbeta.common.core.reflect.BingReflectUtils.automaticBindingParamsByField;
import static com.ecbeta.common.core.reflect.BingReflectUtils.automaticBindingParamsByMethods;
import static com.ecbeta.common.core.reflect.ReflectUtils.findGetter;
import static com.ecbeta.common.core.reflect.ReflectUtils.findParser;
import static com.ecbeta.common.core.reflect.ReflectUtils.findPropertyType;
import static com.ecbeta.common.core.reflect.ReflectUtils.findSetter;
import static com.ecbeta.common.core.reflect.ReflectUtils.getDeclaredFields;
import static com.ecbeta.common.core.reflect.ReflectUtils.updateFieldValue;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ecbeta.common.constants.Constants;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.Action;
import com.ecbeta.common.core.annotation.Actions;
import com.ecbeta.common.core.annotation.ParseMethodType;
import com.ecbeta.common.core.annotation.RequestParse;
import com.ecbeta.common.core.bean.BaseServicerParaBean;
import com.ecbeta.common.core.reflect.bean.FieldSortableBeanParse;
import com.ecbeta.common.core.reflect.bean.MethodSortableBeanParse;
import com.ecbeta.common.util.GeneralUtil;
import com.ecbeta.common.util.SortBeanListUtil;

public class BingReflectUtils {
    
    public static boolean _SHOW_EXCEPTION = true;
    

    public static String __id__ (RequestParse annotation, Field field, Method setterMethod) {
        String name = annotation.id();
        if (StringUtils.isEmpty(name) && field != null) {
            name = field.getName();
        }
        if (StringUtils.isEmpty(name) && setterMethod != null) {
            String s = setterMethod.getName();
            s = s.replace("set", "");
            if (s.length() > 1) {
                if (s.charAt('1') >= 'A' && s.charAt('1') <= 'Z') {
                    return s;
                } else {
                    return s.toLowerCase().substring(0, 1) + s.substring(1);
                }
            } else {
                return s.toLowerCase();
            }
        }

        return name;

    }
    public static Action findAction (Actions actions, String action) {
        Action[] values = actions.value();
        if (values != null) {
            for (Action act : values) {
                if (ArrayUtils.indexOf(act.values(), action) >= 0) {
                    return act;
                }
            }
        }
        return null;

    }

    public static Method findActionMethod ( Class<?> ownerClass, String action) {
        List<Method> methods = Arrays.asList(ownerClass.getDeclaredMethods());
        for (Method method : methods) {
            if (method.isAnnotationPresent(Action.class)) {
                Action annotation = (Action) method.getAnnotation(Action.class);
                if (annotation.values() != null) {
                    if (ArrayUtils.indexOf(annotation.values(), action) >= 0) {
                        return method;
                    }
                }
            } else if (method.isAnnotationPresent(Actions.class)) {
                Actions actions = (Actions) method.getAnnotation(Actions.class);
                if (findAction(actions, action) != null) {
                    return null;
                }
            }
        }
        return null;
    }
    
    public static <T extends Object> void automaticBindingJsonParams (HttpServletRequest request, String btnClicked, String actionMethod, T ... servicers) {
        automaticBindingParamsByField(request, btnClicked, actionMethod, true, servicers);
        automaticBindingParamsByMethods(request, btnClicked, actionMethod, true, servicers);
    }

    public static <T extends Object> void automaticBindingParams (HttpServletRequest request, String btnClicked, String actionMethod, T ... servicers) {
        if (servicers == null) return;
        for (T servicer : servicers) {
            if (servicer instanceof AbstractServicer) {
                AbstractServicer ser = (AbstractServicer) servicer;
                BaseServicerParaBean baseServicerParameterBean = ser.getBaseServicerParameterBean();
                baseServicerParameterBean.setPanel((request.getParameter(Constants.PANEL_INDEX)));
            }
        }
        automaticBindingParamsByField(request, btnClicked, actionMethod, false, servicers);
        automaticBindingParamsByMethods(request, btnClicked, actionMethod, false, servicers);
    }

    public static <T extends Object> void automaticBindingParamsByField (HttpServletRequest request, String btnClicked, String actionMethod, boolean bindJson, T ... servicers) {
        if (servicers == null) return;

        for (T servicer : servicers) {
            try {
                Class<?> ownerClass = servicer.getClass();

                BeanInfo info = Introspector.getBeanInfo(ownerClass, Object.class);
                PropertyDescriptor[] props = info.getPropertyDescriptors();
                Map<String, PropertyDescriptor> fieldName2PropertyDescriptor = new HashMap<String, PropertyDescriptor>();
                for (PropertyDescriptor prop : props) {
                    fieldName2PropertyDescriptor.put(prop.getName(), prop);
                }
                Map<String, Field> allFields = new LinkedHashMap<String, Field>();
                allFields = getDeclaredFields(allFields, ownerClass, true);
                List<Field>  allFieldList = new ArrayList<Field>(allFields.values());
                try {
                    SortBeanListUtil.sort(allFieldList, new int[] { 0 , 1 }, new boolean[] {true,  true }, FieldSortableBeanParse.instance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for ( Field field : allFieldList) {
                    try {
                        if (field.isAnnotationPresent(RequestParse.class)) {
                            RequestParse annotation = (RequestParse) field.getAnnotation(RequestParse.class);
                            if (!needAutoBinding(annotation, btnClicked, actionMethod, bindJson)) {
                                continue;
                            }
                            Method getter = findGetter(servicer, field, fieldName2PropertyDescriptor, annotation);
                            Method setter = findSetter(servicer, field, fieldName2PropertyDescriptor, annotation);
                            Method parser = findParser(servicer, field, annotation);
                            if (_SHOW_EXCEPTION) {
                                System.out.println(String.format("Binding Filed %s with Getter (%s), Setter (%s) and Parser(%s) ", field.getName(), getter == null ? "" : getter.getName(),
                                        setter == null ? "" : setter.toString(), parser == null ? "" : parser.toString()));
                            }
                            bindingParams(request, servicer, field, getter, setter, parser, annotation, btnClicked);
                        }
                    } catch (Exception e) {
                        if (_SHOW_EXCEPTION) {
                            e.printStackTrace();
                            System.err.println( (field != null ?field.getName():"") + e);
                        }
                    }

                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static <T extends Object> void automaticBindingParamsByMethods (HttpServletRequest request, String btnClicked, String actionMethod, boolean json, T ... servicers) {
        if (servicers == null) return;
        for (T servicer : servicers) {
            try {
                List<Method> methods = Arrays.asList(servicer.getClass().getMethods());
                try {
                    methods = new ArrayList<Method>(methods);
                    SortBeanListUtil.sort(methods, new int[] { 0 , 1 }, new boolean[] {true,  true }, MethodSortableBeanParse.instance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (Method method : methods) {
                    try {
                        if (method.isAnnotationPresent(RequestParse.class)) {
                            RequestParse annotation = (RequestParse) method.getAnnotation(RequestParse.class);
                            if (!needAutoBinding(annotation, btnClicked, actionMethod, json)) {
                                continue;
                            }
                            if (_SHOW_EXCEPTION) {
                                System.out.println(String.format("Auto Process Binding Method %s ", method.toString()));
                            }
                            bindingParams(request, servicer, null, null, null, method, annotation, btnClicked);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
    public static void bindingBooleanBox (HttpServletRequest request, Object instance, Field field, Method setterMethod, RequestParse annotation) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        // boolean value = GenerateTagUtil.parseBooleanCheckBox(request, __id__(annotation, field, setterMethod));
       // updateFieldValue(instance, field, setterMethod, value);
        throw new UnsupportedOperationException();
    }
    public static void bindingMultipleCheckBox (HttpServletRequest request, Object instance, Field field, Method setterMethod, RequestParse annotation) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
      //  Set<String> value = GenerateTagUtil.parseMultiCheckBox(request, __id__(annotation, field, setterMethod));
        //updateFieldValue(instance, field, setterMethod, value);
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    private static void bindingParam (HttpServletRequest request, Object instance, Field field, Method setterMethod, RequestParse annotation) {
        String[] values = null;
        String _id = __id__(annotation, field, setterMethod);
        if (annotation.parseType() == ParseMethodType.Param) {
            values = request.getParameterValues(_id);
        } else {
            throw new UnsupportedOperationException();
            //values = new String[] { GenerateTagUtil.parseSingleBox(request, _id) };
        }
        Class<?> propertType = findPropertyType(field, setterMethod);
        if (propertType == null) return;
        try {
            Object args = null;
            Class<?> __newClazz = propertType;
            if (propertType.isArray()) {
                __newClazz = propertType.getComponentType();
            }
            if (__newClazz.isEnum()) {
                Object ar = Array.newInstance(__newClazz, values.length);
                for (int i = 0; i < values.length; i++) {
                    Array.set(ar, i, Enum.valueOf((Class<Enum>) __newClazz, values[i]));
                }
                if (propertType.isArray()) {
                    args = ar;
                } else {
                    args = Array.get(ar, 0);
                }
            } else {
                try {
                    @SuppressWarnings("unchecked")
                    ParamConverter converter = ConverterFactory.createConverter(propertType);
                    if (converter.clazz().isArray()) {
                        @SuppressWarnings("unchecked")
                        Object o = converter.convert(values);
                        args = o;
                    } else {
                        String value = null;
                        if (values != null && values.length > 0) {
                            value = values[0];
                        }
                        @SuppressWarnings("unchecked")
                        Object o = converter.convert(value);
                        args = o;
                    }
                } catch (ConverterException exs) {
                    Method method = __newClazz.getMethod("createInstance", new Class[] { HttpServletRequest.class, String.class, String.class });
                    Object ar = Array.newInstance(__newClazz, values.length);
                    for (int i = 0; i < values.length; i++) {
                        Array.set(ar, i, method.invoke(null, new Object[] { request, _id, values[i] }));
                    }
                    if (propertType.isArray()) {
                        args = ar;
                    } else {
                        args = Array.get(ar, 0);
                    }
                    // return;
                }
            }
            updateFieldValue(instance, field, setterMethod, args);

        } catch (Exception e) {
            if (_SHOW_EXCEPTION) {
                // e.printStackTrace();
                System.err.println(field + " > " + setterMethod);
                System.err.println(e);
            }
            return;
        }
    }

    private static void bindingParams (HttpServletRequest request, Object instance, Field field, Method getterMethod, Method setterMethod, Method method, RequestParse annotation, String btnClicked)
            throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        try {
            if (annotation.parseType() == ParseMethodType.StateBean) {
                bindingStateBean(request, instance, field, getterMethod == null ? method : getterMethod, setterMethod == null ? method : setterMethod, annotation);
            } else if (annotation.parseType() == ParseMethodType.ParseRequest) {
                if (method != null) {
                    method.invoke(instance, request);
                }
            } else if (annotation.parseType() == ParseMethodType.ParseRequestWithAction) {
                if (method != null) {
                    method.invoke(instance, request, btnClicked);
                }
            } else if (annotation.parseType() == ParseMethodType.BooleanCheckboxTag) {
                bindingBooleanBox(request, instance, field, setterMethod == null ? method : setterMethod, annotation);
            } else if (annotation.parseType() == ParseMethodType.SingleRadioTag || annotation.parseType() == ParseMethodType.Param) {
                bindingParam(request, instance, field, setterMethod == null ? method : setterMethod, annotation);
            } else if (annotation.parseType() == ParseMethodType.MultipleCheckboxTag) {
                bindingMultipleCheckBox(request, instance, field, setterMethod == null ? method : setterMethod, annotation);
            } else if (annotation.parseType() == ParseMethodType.AutoCall) {
                if (method != null) {
                    method.invoke(instance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void bindingStateBean (HttpServletRequest request, Object instance, Field field, Method getterMethod, Method setterMethod, RequestParse annotation) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        /*if (getterMethod != null) {
            Object stateBean = getterMethod.invoke(instance);
            if (stateBean == null) {
                return;
            }
            if (annotation != null && StringUtils.isNotEmpty(annotation.id())) {
                Method setIdMethod = stateBean.getClass().getMethod("setId", new Class[] { String.class });
                setIdMethod.invoke(stateBean, new Object[] { annotation.id() });
            }
            Method stateBeanParseMethod = stateBean.getClass().getMethod("parse", new Class[] { HttpServletRequest.class });
            stateBeanParseMethod.invoke(stateBean, request);
        } else {
            Class<?> propertType = findPropertyType(field, setterMethod);
            if (propertType == null) return;
            Object value = GenerateTagUtil.parseModuleStateBean2(propertType, __id__(annotation, field, setterMethod), request);
            updateFieldValue(instance, field, setterMethod, value);
        }*/
        throw new UnsupportedOperationException();
    }

    public static boolean needAutoBinding (RequestParse annotation, String btnClicked, String actionMethod, boolean bindJson) {
        if (annotation == null) return false;
        String actions = annotation.actions();
        if (btnClicked == null || actionMethod == null || StringUtils.isEmpty(actions)) {
            // TRUE
        } else if (GeneralUtil.isRegexExpression(actions)) {
            Pattern pattern = GeneralUtil.getPattern(actions);
            if (/* pattern.matcher(btnClicked).find() || */pattern.matcher(actionMethod).find()) {} else {
                return false;
            }
        } else if (actions.contains(btnClicked) || actions.contains(actionMethod)) {
            // TRUE
        } else {
            return false;
        }

        if (!annotation.canJson() && bindJson) {
            return false;
        }
        return true;
    }

}
