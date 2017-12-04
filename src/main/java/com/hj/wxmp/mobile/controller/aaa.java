package com.hj.wxmp.mobile.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.wxmp.mobile.dao.UserDao;
import com.hj.wxmp.mobile.entity.AccessRecord01;
import com.hj.wxmp.mobile.entity.AccessRecord02;
import com.hj.wxmp.mobile.entity.AccessRecord03;
import com.hj.wxmp.mobile.entity.AuditRecord;
import com.hj.wxmp.mobile.entity.DayRecep;
import com.hj.wxmp.mobile.entity.ProjCustRef;
import com.hj.wxmp.mobile.entity.TabDictRef;
import com.hj.wxmp.mobile.mapping.UserInfoMapper;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
import com.hj.wxmp.mobile.services.AuditRecordService;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.DayRecepService;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjCustRefService;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.TabDictRefService;
import com.hj.wxmp.mobile.services.UserInfoService;

@Controller
public class aaa {
	@Autowired
	UserInfoMapper sysUserMapper;
	@Autowired
	UserDao userDao;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	AccessRecord01Service accessRecord01Service;
	@Autowired
	AccessRecord02Service accessRecord02Service;
	@Autowired
	AccessRecord03Service accessRecord03Service;
	@Autowired
	ProjectService projectService;
	@Autowired
	CustomerService customerService;
	@Autowired
	ProjCustRefService projCustRefService;
	@Autowired
	ProjUserRoleService projUserRoleService;
	@Autowired
	DayRecepService dayRecepService;
	@Resource
	IKeyGen key;
	@Autowired
	TabDictRefService tabDictRefService;
	@Autowired
	AuditRecordService auditRecordService;
	
	
	
	@RequestMapping("/test/updateAuditRecord")
	public void updateAuditRecord() {
		try{
			List<AccessRecord01> accessRecord01s = accessRecord01Service.selectNotInAuditRecord();
			for(AccessRecord01 accessRecord01:accessRecord01s){
				String id = accessRecord01.getId();
				Date ctime = accessRecord01.getCtime();
				//添加审核信息
				AuditRecord auditRecord = new AuditRecord();
				auditRecord.setId(key.getUUIDKey());
				auditRecord.setAudittype(1);
				auditRecord.setarid(id);
				auditRecord.setAudittype(1);
				auditRecord.setCtime(ctime);
				auditRecordService.insert(auditRecord);
			}
			System.out.println("-------------------");
			System.out.println("|    THIS IS OK   |");
			System.out.println("-------------------");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping("/test/updateErrorData")
	public void update() {
		try {
			//认知本案渠道
			List<TabDictRef> tabDictRefs = tabDictRefService.selectCognitiveCaseChannel();
			if(tabDictRefs!=null){
				for(TabDictRef tabDictRef : tabDictRefs){
					String tabid = tabDictRef.getTabid();
					if(tabid!=null){
						ProjCustRef projCustRef = projCustRefService.selctByCustId(tabid);
						if(projCustRef!=null){
							String id = projCustRef.getId();
							tabDictRef.setTabid(id);
							tabDictRefService.update(tabDictRef);
						}
					}
				}
			}
			//本案关注点
			List<TabDictRef> tabDictRef1s = tabDictRefService.selectConcern();
			if(tabDictRefs!=null){
				for(TabDictRef tabDictRef : tabDictRef1s){
					String tabid = tabDictRef.getTabid();
					if(tabid!=null){
						ProjCustRef projCustRef = projCustRefService.selctByCustId(tabid);
						if(projCustRef!=null){
							String id = projCustRef.getId();
							tabDictRef.setTabid(id);
							tabDictRefService.update(tabDictRef);
						}
					}
				}
			}
			//资金筹备期
			List<TabDictRef> tabDictRef2s = tabDictRefService.leadTime();
			if(tabDictRefs!=null){
				for(TabDictRef tabDictRef : tabDictRef2s){
					String tabid = tabDictRef.getTabid();
					if(tabid!=null){
						ProjCustRef projCustRef = projCustRefService.selctByCustId(tabid);
						if(projCustRef!=null){
							String id = projCustRef.getId();
							tabDictRef.setTabid(id);
							tabDictRefService.update(tabDictRef);
						}
					}
				}
			}
			//本案抗拒点
			List<TabDictRef> tabDictRef3s = tabDictRefService.selectResistPoint();
			if(tabDictRefs!=null){
				for(TabDictRef tabDictRef : tabDictRef3s){
					String tabid = tabDictRef.getTabid();
					if(tabid!=null){
						ProjCustRef projCustRef = projCustRefService.selctByCustId(tabid);
						if(projCustRef!=null){
							String id = projCustRef.getId();
							tabDictRef.setTabid(id);
							tabDictRefService.update(tabDictRef);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	//统计
	@RequestMapping("/test/count")
	@ResponseBody
	public void count123123(String datetime) {
		try {
			System.out.println(datetime);
			TestCount x01=new TestCount(datetime,1);
			x01.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	//线程
	class TestCount extends Thread {
		String date="";
		int type=1;
		public TestCount(String date,int type) {
			this.date=date;
			this.type=type;
		}
		@Override
        public synchronized void run() {
			try{
				//当天所有首访记录
				List<AccessRecord01> accessRecord01s = accessRecord01Service.selectByRecepTime(date);
				//当天所有复访记录
				List<AccessRecord02> accessRecord02s = accessRecord02Service.selectByRecepTime(date);
				//当天所有成交记录
				List<AccessRecord03> accessRecord03s = accessRecord03Service.selectByRecepTime(date);
				
				
				
				//总数据
				for(AccessRecord01 accessRecord:accessRecord01s){
					//项目总次数
					int projRecord01Total = 0;
					int projRecord02Total = 0;
					int projRecord03Total = 0;
					//用户总次数
					int userRecord01Total = 0;
					int userRecord02Total = 0;
					int userRecord03Total = 0;
					//客户总次数
					int custRecord01Total = 0;
					int custRecord02Total = 0;
					int custRecord03Total = 0;
					//项目客户总次数
					int projCustRecord01Total = 0;
					int projCustRecord02Total = 0;
					int projCustRecord03Total = 0;
					//项目用户总次数
					int projUserInfoRecord01Total = 0;
					int projUserInfoRecord02Total = 0;
					int projUserInfoRecord03Total = 0;
					//用户客户总次数
					int userCustRecord01Total = 0;
					int userCustRecord02Total = 0;
					int userCustRecord03Total = 0;
					String userId = accessRecord.getAuthorid();
					String custId = accessRecord.getCustid();
					String projId = accessRecord.getProjid();
					//首访
					for(AccessRecord01 accessRecord01:accessRecord01s){
						String record01UserId = accessRecord01.getAuthorid();
						String record01CustId = accessRecord01.getCustid();
						String record01ProjId = accessRecord01.getProjid();
						if(userId.equals(record01UserId)){
							userRecord01Total+=1;
						}
						if(custId.equals(record01CustId)){
							custRecord01Total+=1;
						}
						if(projId.equals(record01ProjId)){
							projRecord01Total+=1;
						}
						if(projId.equals(record01ProjId) && userId.equals(record01UserId)){
							projUserInfoRecord01Total+=1;
						}
						if(projId.equals(record01ProjId) && custId.equals(record01CustId)){
							projCustRecord01Total+=1;
						}
						if(projId.equals(record01ProjId) && custId.equals(record01CustId) && userId.equals(record01UserId)){
							userCustRecord01Total+=1;
						}
					}
					//复访
					for(AccessRecord02 accessRecord02:accessRecord02s){
						String record02UserId = accessRecord02.getAuthorid();
						String record02CustId = accessRecord02.getCustid();
						String record02ProjId = accessRecord02.getProjid();
						if(userId.equals(record02UserId)){
							userRecord02Total+=1;
						}
						if(custId.equals(record02CustId)){
							custRecord02Total+=1;
						}
						if(projId.equals(record02ProjId)){
							projRecord02Total+=1;
						}
						if(projId.equals(record02ProjId) && userId.equals(record02UserId)){
							projUserInfoRecord02Total+=1;
						}
						if(projId.equals(record02ProjId) && custId.equals(record02CustId)){
							projCustRecord02Total+=1;
						}
						if(projId.equals(record02ProjId) && custId.equals(record02CustId) && userId.equals(record02UserId)){
							userCustRecord02Total+=1;
						}
					}
					//成交
					for(AccessRecord03 accessRecord03:accessRecord03s){
						String record03UserId = accessRecord03.getAuthorid();
						String record03CustId = accessRecord03.getCustid();
						String record03ProjId = accessRecord03.getProjid();
						if(userId.equals(record03UserId)){
							userRecord03Total+=1;
						}
						if(custId.equals(record03CustId)){
							custRecord03Total+=1;
						}
						if(projId.equals(record03ProjId)){
							projRecord03Total+=1;
						}
						if(projId.equals(record03ProjId) && userId.equals(record03UserId)){
							projUserInfoRecord03Total+=1;
						}
						if(projId.equals(record03ProjId) && custId.equals(record03CustId)){
							projCustRecord03Total+=1;
						}
						if(projId.equals(record03ProjId) && custId.equals(record03CustId) && userId.equals(record03UserId)){
							userCustRecord03Total+=1;
						}
					}
					//添加项目统计
					DayRecep dayRecep = new DayRecep();
					dayRecep.setDaystr(date);
					dayRecep.setMiiprojid(projId);
					dayRecep.setAr01count(projRecord01Total);
					dayRecep.setAr02count(projRecord02Total);
					dayRecep.setAr03count(projRecord03Total);
					if(type==1){
						dayRecep.setId(key.getUUIDKey());
						//添加
						dayRecepService.insert(dayRecep);
					}else{
						//更新
						dayRecepService.updateMsg(dayRecep);
					}
					//添加用户统计
					DayRecep dayRecep1 = new DayRecep();
					dayRecep1.setDaystr(date);
					dayRecep1.setMiiuserid(userId);
					dayRecep1.setAr01count(userRecord01Total);
					dayRecep1.setAr02count(userRecord02Total);
					dayRecep1.setAr03count(userRecord03Total);
					if(type==1){
						dayRecep1.setId(key.getUUIDKey());
						//添加
						dayRecepService.insert(dayRecep1);
					}else{
						//更新
						dayRecepService.updateMsg(dayRecep1);
					}
					//添加客户统计
					DayRecep dayRecep2 = new DayRecep();
					dayRecep2.setDaystr(date);
					dayRecep2.setMiicustid(custId);
					dayRecep2.setAr01count(custRecord01Total);
					dayRecep2.setAr02count(custRecord02Total);
					dayRecep2.setAr03count(custRecord03Total);
					if(type==1){
						dayRecep2.setId(key.getUUIDKey());
						//添加
						dayRecepService.insert(dayRecep2);
					}else{
						//更新
						dayRecepService.updateMsg(dayRecep2);
					}
					//添加项目客户统计
					DayRecep dayRecep3 = new DayRecep();
					dayRecep3.setDaystr(date);
					dayRecep3.setMiicustid(custId);
					dayRecep3.setMiiprojid(projId);
					dayRecep3.setAr01count(projCustRecord01Total);
					dayRecep3.setAr02count(projCustRecord02Total);
					dayRecep3.setAr03count(projCustRecord03Total);
					if(type==1){
						dayRecep3.setId(key.getUUIDKey());
						//添加
						dayRecepService.insert(dayRecep3);
					}else{
						//更新
						dayRecepService.updateMsg(dayRecep3);
					}
					//添加项目用户统计
					DayRecep dayRecep4 = new DayRecep();
					dayRecep4.setDaystr(date);
					dayRecep4.setMiiuserid(userId);
					dayRecep4.setMiiprojid(projId);
					dayRecep4.setAr01count(projUserInfoRecord01Total);
					dayRecep4.setAr02count(projUserInfoRecord02Total);
					dayRecep4.setAr03count(projUserInfoRecord03Total);
					if(type==1){
						dayRecep4.setId(key.getUUIDKey());
						//添加
						dayRecepService.insert(dayRecep4);
					}else{
						//更新
						dayRecepService.updateMsg(dayRecep4);
					}
					//添加用户客户统计
					DayRecep dayRecep5 = new DayRecep();
					dayRecep5.setDaystr(date);
					dayRecep5.setMiiuserid(userId);
					dayRecep5.setMiiprojid(projId);
					dayRecep5.setMiicustid(custId);
					dayRecep5.setAr01count(userCustRecord01Total);
					dayRecep5.setAr02count(userCustRecord02Total);
					dayRecep5.setAr03count(userCustRecord03Total);
					if(type==1){
						dayRecep5.setId(key.getUUIDKey());
						//添加
						dayRecepService.insert(dayRecep5);
					}else{
						//更新
						dayRecepService.updateMsg(dayRecep5);
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
