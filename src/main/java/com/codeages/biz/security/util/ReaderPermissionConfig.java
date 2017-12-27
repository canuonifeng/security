package com.codeages.biz.security.util;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeages.biz.security.entity.Permission;
import com.codeages.biz.security.entity.PermissionConfig;


public class ReaderPermissionConfig {
	private static final Logger logger = LoggerFactory
			.getLogger(ReaderPermissionConfig.class);
	
	public static PermissionConfig readerConfig() {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(
					ReaderPermissionConfig.class
							.getResourceAsStream("/config/permissionConfig.xml"),
					"UTF-8");
			JAXBContext jaxbContext = JAXBContext.newInstance(PermissionConfig.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			PermissionConfig permissionConfig = (PermissionConfig) jaxbUnmarshaller.unmarshal(inputStreamReader);
			return permissionConfig;
		} catch (Exception e) {
			logger.error("读取权限配置出错：" + e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static void setParentPermission(List<Permission> permissions,Permission parentPermission){
		for(Permission permission:permissions){
			permission.setParentPermission(parentPermission);
			List<Permission> subPermissions = permission.getSubpermissions();
			if(null != subPermissions && subPermissions.size()>0){
				setParentPermission(subPermissions, permission);
			}
		}
	}
	
	private static List<Permission> getPermissions(List<Permission> list){
		List<Permission> permissions = new ArrayList<Permission>();		
		for(Permission permission:list){			
			List<Permission> subPermissions = permission.getSubpermissions();
			if(null != subPermissions && subPermissions.size()>0){
				permissions.addAll(subPermissions);
				permissions.addAll(getPermissions(subPermissions));
			}
		}
		return permissions;
	}
	
	public static List<Permission> getPermissions(PermissionConfig config){
		List<Permission> list = config.getPermissions();
		List<Permission> permissions =new ArrayList<Permission>();
		permissions.addAll(list);
		permissions.addAll(getPermissions(list));
		return permissions;
	}
	
	public static String[] getPermissionCodes(PermissionConfig config){
		List<Permission> permissions =  getPermissions(config);
		String[] codes = new String[permissions.size()];
		for(int i = 0;i<permissions.size();i++){
			codes[i] = permissions.get(i).getCode();
		}		
		return codes;
	}
	
	public static void main(String args[]) {
		try {
			PermissionConfig config = ReaderPermissionConfig.readerConfig();
			String[] enames = ReaderPermissionConfig.getPermissionCodes(config);
			for (String e : enames) {
				System.out.println(e);
			}
			logger.debug(config.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
