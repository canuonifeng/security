package com.edu.biz.security.util;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.biz.security.entity.Permission;
import com.edu.biz.security.entity.Permission2;
import com.edu.biz.security.entity.PermissionConfig;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class ReaderPermissionConfig {
	private static final Logger logger = LoggerFactory
			.getLogger(ReaderPermissionConfig.class);
	
	public static PermissionConfig readerConfig() {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(
					ReaderPermissionConfig.class
							.getResourceAsStream("/config/permissionConfig.xml"),
					"UTF-8");
			XStream xStream = new XStream(new DomDriver());
			xStream.processAnnotations(PermissionConfig.class);
			PermissionConfig permissionConfig = (PermissionConfig) xStream
					.fromXML(inputStreamReader);
			setParentPermission(permissionConfig.getPermissions(), null);
			return permissionConfig;
		} catch (Exception e) {
			logger.error("读取权限配置出错：" + e);
			return null;
		}
	}
	
	private static void setParentPermission(List<Permission2> permissions,Permission2 parentPermission){
		for(Permission2 permission:permissions){
			permission.setParentPermission(parentPermission);
			List<Permission2> subPermissions = permission.getSubpermissions();
			if(null != subPermissions && subPermissions.size()>0){
				setParentPermission(subPermissions, permission);
			}
		}
	}
	
	private static List<Permission2> getPermissions(List<Permission2> list){
		List<Permission2> permissions = new ArrayList<Permission2>();		
		for(Permission2 permission:list){			
			List<Permission2> subPermissions = permission.getSubpermissions();
			if(null != subPermissions && subPermissions.size()>0){
				permissions.addAll(subPermissions);
				permissions.addAll(getPermissions(subPermissions));
			}
		}
		return permissions;
	}
	
	public static List<Permission2> getPermissions(PermissionConfig config){
		List<Permission2> list = config.getPermissions();
		List<Permission2> permissions =new ArrayList<Permission2>();
		permissions.addAll(list);
		permissions.addAll(getPermissions(list));
		return permissions;
	}
	
	public static String[] getPermissionCodes(PermissionConfig config){
		List<Permission2> permissions =  getPermissions(config);
		String[] codes = new String[permissions.size()];
		for(int i = 0;i<permissions.size();i++){
			codes[i] = permissions.get(i).getCode();
		}		
		return codes;
	}
	
	public static void main(String[] args) {
		try {
			PermissionConfig config = readerConfig();
			String[] enames = getPermissionCodes(config);
			for (String e : enames) {
				System.out.println(e);
			}
			logger.debug(config.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
