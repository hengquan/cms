package com.hj.wxmp.mobile.quartzjob;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hj.utils.DateTimeUtil;
import com.hj.wxmp.mobile.dao.UserDao;
import com.hj.wxmp.mobile.entity.AccessRecord01;
import com.hj.wxmp.mobile.entity.AccessRecord02;
import com.hj.wxmp.mobile.entity.AccessRecord03;
import com.hj.wxmp.mobile.entity.DayRecep;
import com.hj.wxmp.mobile.mapping.UserInfoMapper;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.DayRecepService;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjCustRefService;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.UserInfoService;

public class DoQuarzt {

	private final static Logger logger = LoggerFactory.getLogger(DoQuarzt.class);

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
	
	
	public void doWork() {
		logger.info("开始执行定时任务");
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//多线程
			//今天
			String today = format.format(DateTimeUtil.getdate(-1));
			ManyStatistics x01=new ManyStatistics(today,1);
			x01.start();
			//昨天
			String yesterday = format.format(DateTimeUtil.getdate(-2));
			ManyStatistics x02=new ManyStatistics(yesterday,2);
			x02.start();
			//前天
			String yesterdayBeFor = format.format(DateTimeUtil.getdate(-3));
			ManyStatistics x03=new ManyStatistics(yesterdayBeFor,2);
			x03.start();
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	}
	
	
	
	
	//线程
	class ManyStatistics extends Thread {
		String date="";
		int type=1;
		public ManyStatistics(String date,int type) {
			this.date=date;
			this.type=type;
		}
		@Override
        public void run() {
			try{
				//当天所有首访记录
				List<AccessRecord01> accessRecord01s = accessRecord01Service.selectByRecepTime(date);
				//当天所有复访记录
				List<AccessRecord02> accessRecord02s = accessRecord02Service.selectByRecepTime(date);
				//当天所有成交记录
				List<AccessRecord03> accessRecord03s = accessRecord03Service.selectByRecepTime(date);
//				//所有项目
//				List<Project> projects = projectService.findAll();
//				//所有用户
//				List<UserInfo> userInfos = userInfoService.findAll();
//				//所有客户
//				List<Customer> customers = customerService.findAll();
//				//所有项目客户
//				List<ProjCustRef> projCustRefs = projCustRefService.findAll();
//				//所有项目用户
//				List<ProjUserRole> projUserRoles = projUserRoleService.findAll();
				
				
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
				
				
				//总数据
				for(AccessRecord01 accessRecord:accessRecord01s){
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
						dayRecepService.update(dayRecep2);
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
						dayRecepService.update(dayRecep3);
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
						dayRecepService.update(dayRecep4);
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
						dayRecepService.update(dayRecep5);
					}
					
				}
				
				
				
				
				
				
				
				
				
				
//				//项目总次数
//				for(Project proj : projects){
//					DayRecep dayRecep = new DayRecep();
//					String projId = proj.getId();
//					//项目首访总次数
//					for(AccessRecord01 accessRecord01 : accessRecord01s){
//						String record01ProjId = accessRecord01.getProjid();
//						if(projId.equals(record01ProjId)){
//							projRecord01Total+=1;
//						}
//					}
//					//项目复访总次数
//					for(AccessRecord02 accessRecord02 : accessRecord02s){
//						String record02ProjId = accessRecord02.getProjid();
//						if(projId.equals(record02ProjId)){
//							projRecord02Total+=2;
//						}
//					}
//					//项目成交总次数
//					for(AccessRecord03 accessRecord03 : accessRecord03s){
//						String record03ProjId = accessRecord03.getProjid();
//						if(projId.equals(record03ProjId)){
//							projRecord03Total+=3;
//						}
//					}
//					//添加统计
//					dayRecep.setDaystr(date);
//					dayRecep.setMiiprojid(projId);
//					dayRecep.setAr01count(projRecord01Total);
//					dayRecep.setAr02count(projRecord02Total);
//					dayRecep.setAr03count(projRecord03Total);
//					if(type==1){
//						dayRecep.setId(key.getUUIDKey());
//						//添加
//						dayRecepService.insert(dayRecep);
//					}else{
//						//更新
//						dayRecepService.updateMsg(dayRecep);
//					}
//				}
//				
//				//用户总次数
//				for(UserInfo userInfo : userInfos){
//					DayRecep dayRecep = new DayRecep();
//					String userInfoId = userInfo.getId();
//					//用户首访总次数
//					for(AccessRecord01 accessRecord01 : accessRecord01s){
//						String authorid = accessRecord01.getAuthorid();
//						if(userInfoId.equals(authorid)){
//							userRecord01Total+=1;
//						}
//					}
//					//用户复访总次数
//					for(AccessRecord02 accessRecord02 : accessRecord02s){
//						String authorid = accessRecord02.getAuthorid();
//						if(userInfoId.equals(authorid)){
//							userRecord02Total+=1;
//						}
//					}
//					//用户成交总次数
//					for(AccessRecord03 accessRecord03 : accessRecord03s){
//						String authorid = accessRecord03.getAuthorid();
//						if(userInfoId.equals(authorid)){
//							userRecord03Total+=1;
//						}
//					}
//					//添加统计
//					dayRecep.setDaystr(date);
//					dayRecep.setMiiuserid(userInfoId);
//					dayRecep.setAr01count(userRecord01Total);
//					dayRecep.setAr02count(userRecord02Total);
//					dayRecep.setAr03count(userRecord03Total);
//					if(type==1){
//						dayRecep.setId(key.getUUIDKey());
//						//添加
//						dayRecepService.insert(dayRecep);
//					}else{
//						//更新
//						dayRecepService.updateMsg(dayRecep);
//					}
//				}
//				
//				//客户总次数
//				for(Customer cust : customers){
//					DayRecep dayRecep = new DayRecep();
//					String custId = cust.getId();
//					//客户首访总次数
//					for(AccessRecord01 accessRecord01 : accessRecord01s){
//						String record01CustId = accessRecord01.getCustid();
//						if(custId.equals(record01CustId)){
//							custRecord01Total+=1;
//						}
//					}
//					//客户复访总次数
//					for(AccessRecord02 accessRecord02 : accessRecord02s){
//						String record02CustId = accessRecord02.getCustid();
//						if(custId.equals(record02CustId)){
//							custRecord02Total+=1;
//						}
//					}
//					//客户成交总次数
//					for(AccessRecord03 accessRecord03 : accessRecord03s){
//						String record03CustId = accessRecord03.getCustid();
//						if(custId.equals(record03CustId)){
//							custRecord03Total+=1;
//						}
//					}
//					//添加统计
//					dayRecep.setDaystr(date);
//					dayRecep.setMiicustid(custId);
//					dayRecep.setAr01count(custRecord01Total);
//					dayRecep.setAr02count(custRecord02Total);
//					dayRecep.setAr03count(custRecord03Total);
//					if(type==1){
//						dayRecep.setId(key.getUUIDKey());
//						//添加
//						dayRecepService.insert(dayRecep);
//					}else{
//						//更新
//						dayRecepService.updateMsg(dayRecep);
//					}
//				}
//				
//				//项目客户总次数
//				for(ProjCustRef projCustRef : projCustRefs){
//					DayRecep dayRecep = new DayRecep();
//					String custid = projCustRef.getCustid();
//					String projid = projCustRef.getProjid();
//					//项目客户首访总次数
//					for(AccessRecord01 accessRecord01 : accessRecord01s){
//						String record01ProjId = accessRecord01.getProjid();
//						String record01CustId = accessRecord01.getCustid();
//						if(projid.equals(record01ProjId) && custid.equals(record01CustId)){
//							projCustRecord01Total+=1;
//						}
//					}
//					//项目客户复访总次数
//					for(AccessRecord02 accessRecord02 : accessRecord02s){
//						String record02ProjId = accessRecord02.getProjid();
//						String record02CustId = accessRecord02.getCustid();
//						if(projid.equals(record02ProjId) && custid.equals(record02CustId)){
//							projCustRecord02Total+=1;
//						}
//					}
//					//项目客户成交总次数
//					for(AccessRecord03 accessRecord03 : accessRecord03s){
//						String record03ProjId = accessRecord03.getProjid();
//						String record03CustId = accessRecord03.getCustid();
//						if(projid.equals(record03ProjId) && custid.equals(record03CustId)){
//							projCustRecord03Total+=1;
//						}
//					}
//					//添加统计
//					dayRecep.setDaystr(date);
//					dayRecep.setMiicustid(custid);
//					dayRecep.setMiiprojid(projid);
//					dayRecep.setAr01count(projCustRecord01Total);
//					dayRecep.setAr02count(projCustRecord02Total);
//					dayRecep.setAr03count(projCustRecord03Total);
//					if(type==1){
//						dayRecep.setId(key.getUUIDKey());
//						//添加
//						dayRecepService.insert(dayRecep);
//					}else{
//						//更新
//						dayRecepService.update(dayRecep);
//					}
//				}
//				
//				//项目用户总次数
//				for(ProjUserRole projUserRole : projUserRoles){
//					DayRecep dayRecep = new DayRecep();
//					String projId = projUserRole.getProjid();
//					String userId = projUserRole.getUserid();
//					//项目用户首访总次数
//					for(AccessRecord01 accessRecord01 : accessRecord01s){
//						String Record01ProjId = accessRecord01.getProjid();
//						String authorid = accessRecord01.getAuthorid();
//						if(projId.equals(Record01ProjId) && userId.equals(authorid)){
//							projUserInfoRecord01Total+=1;
//						}
//					}
//					//项目用户复访总次数
//					for(AccessRecord02 accessRecord02 : accessRecord02s){
//						String Record02ProjId = accessRecord02.getProjid();
//						String authorid = accessRecord02.getAuthorid();
//						if(projId.equals(Record02ProjId) && userId.equals(authorid)){
//							projUserInfoRecord02Total+=1;
//						}
//					}
//					//项目用户成交总次数
//					for(AccessRecord03 accessRecord03 : accessRecord03s){
//						String Record03ProjId = accessRecord03.getProjid();
//						String authorid = accessRecord03.getAuthorid();
//						if(projId.equals(Record03ProjId) && userId.equals(authorid)){
//							projUserInfoRecord03Total+=1;
//						}
//					}
//					//添加统计
//					dayRecep.setDaystr(date);
//					dayRecep.setMiiuserid(userId);
//					dayRecep.setMiiprojid(projId);
//					dayRecep.setAr01count(projUserInfoRecord01Total);
//					dayRecep.setAr02count(projUserInfoRecord02Total);
//					dayRecep.setAr03count(projUserInfoRecord03Total);
//					if(type==1){
//						dayRecep.setId(key.getUUIDKey());
//						//添加
//						dayRecepService.insert(dayRecep);
//					}else{
//						//更新
//						dayRecepService.update(dayRecep);
//					}
//				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
}
