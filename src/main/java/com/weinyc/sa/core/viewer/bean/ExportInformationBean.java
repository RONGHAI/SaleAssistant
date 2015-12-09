package com.weinyc.sa.core.viewer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExportInformationBean implements Cloneable, Serializable{
	 
	private static final long serialVersionUID = 1523393211466859402L;

	@Override
	public Object clone() throws CloneNotSupportedException {
		ExportInformationBean  bean = (ExportInformationBean) super.clone();
		if(this.footerList  != null){
			bean.footerList = new ArrayList<List<String>>(this.footerList.size());
			for(List<String> list : this.footerList){ 
				bean.footerList.add( new ArrayList<String>(list));
			}
		}
		return bean;
	}
	 
	protected List<List<String>> footerList;

    public List<List<String>> getFooterList () {
        return footerList;
    }

    public void setFooterList (List<List<String>> footerList) {
        this.footerList = footerList;
    } 
}
