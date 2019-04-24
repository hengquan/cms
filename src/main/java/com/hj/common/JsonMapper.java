package com.hj.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.springframework.core.annotation.AnnotationUtils;

public class JsonMapper {
	private ObjectMapper objectMapper = new ObjectMapper();

	public JsonMapper() {
		// 设置默认日期格式
		// objectMapper.setDateFormat(Config.DEFAULT_DATE_FORMATTER);
		// 提供其它默认设置
		objectMapper
				.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
				false);
		objectMapper.setFilters(new SimpleFilterProvider()
				.setFailOnUnknownId(false));
	}

	public static void main(String[] args) {
		//String json="{\"result\":{\"resultCode\":\"\",\"message\":\"\"},\"page\":{\"currentPageNum\":\"0\",\"rowsOfPage\":\"15\",\"totalPageCount\":\"6\",\"totalRowCount\":\"85\"},\"rightsList\":[{\"rightsId\":\"120d89deb9264d7496ec437e25f8a5ca\",\"rightsCode\":\"090179860\",\"sts\":\"02\",\"createTime\":\"2014-09-30 10:02:04\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"1174\",\"price\":\"1174\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"999\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"1\",\"childNum\":\"0\",\"source\":\"55BBS\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"175\",\"validStart\":\"2014-09-30\",\"validEnd\":\"2016-09-30\",\"productList\":[{\"code\": \"090179870\",\"deptDate\": \"\",\"deptPlace\": \"北京\",\"sts\": \"0\",\"prodTourId\": \"089765180\",\"prodTourCode\": \"089765180\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"北京-杭州3天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"李君测试\",\"certNo\": \"1\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"e426a95e3d9c491595ced37273b90896\",\"rightsCode\":\"090179790\",\"sts\":\"02\",\"createTime\":\"2014-09-30 09:55:15\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"1345\",\"price\":\"1345\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"1175\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"1\",\"childNum\":\"0\",\"source\":\"lotour\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"170\",\"validStart\":\"2014-09-30\",\"validEnd\":\"2016-09-30\",\"productList\":[{\"code\": \"090179800\",\"deptDate\": \"\",\"deptPlace\": \"北京\",\"sts\": \"0\",\"prodTourId\": \"089150970\",\"prodTourCode\": \"089150970\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"北京-海口5天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"张佳佳\",\"certNo\": \"111111\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"449b022a22ff42e9ab1293170c3e0c37\",\"rightsCode\":\"090179660\",\"sts\":\"02\",\"createTime\":\"2014-09-30 09:43:43\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"1619\",\"price\":\"1619\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"1429\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"1\",\"childNum\":\"0\",\"source\":\"Travelzoo\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"190\",\"validStart\":\"2014-09-30\",\"validEnd\":\"2016-09-30\",\"productList\":[{\"code\": \"090179670\",\"deptDate\": \"\",\"deptPlace\": \"北京\",\"sts\": \"0\",\"prodTourId\": \"089151120\",\"prodTourCode\": \"089151120\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"北京-三亚5天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"李君测试\",\"certNo\": \"1\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"92ec63f5cf59415c9908f5c5075f9212\",\"rightsCode\":\"090179450\",\"sts\":\"02\",\"createTime\":\"2014-09-30 09:27:28\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"854\",\"price\":\"854\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"779\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"1\",\"childNum\":\"0\",\"source\":\"shuaipiao\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"175\",\"validStart\":\"2014-09-30\",\"validEnd\":\"2016-09-30\",\"productList\":[{\"code\": \"090179460\",\"deptDate\": \"\",\"deptPlace\": \"济南\",\"sts\": \"0\",\"prodTourId\": \"089151020\",\"prodTourCode\": \"089151020\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"济南-西安4天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"张佳佳\",\"certNo\": \"111111\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"4af0f4911aec4173ab39ed96ffea6896\",\"rightsCode\":\"090179280\",\"sts\":\"02\",\"createTime\":\"2014-09-30 09:10:06\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"1619\",\"price\":\"1619\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"1429\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"1\",\"childNum\":\"0\",\"source\":\"jdair\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"190\",\"validStart\":\"2014-09-30\",\"validEnd\":\"2016-09-30\",\"productList\":[{\"code\": \"090179290\",\"deptDate\": \"\",\"deptPlace\": \"北京\",\"sts\": \"0\",\"prodTourId\": \"089151120\",\"prodTourCode\": \"089151120\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"北京-三亚5天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"税票\",\"certNo\": \"1\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"4bfc543e3d504b0fa7f66658c87805d9\",\"rightsCode\":\"090179260\",\"sts\":\"02\",\"createTime\":\"2014-09-30 09:07:54\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"1179\",\"price\":\"1179\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"989\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"1\",\"childNum\":\"0\",\"source\":\"b2c\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"190\",\"validStart\":\"2014-09-30\",\"validEnd\":\"2016-09-30\",\"productList\":[{\"code\": \"090179270\",\"deptDate\": \"\",\"deptPlace\": \"福州\",\"sts\": \"0\",\"prodTourId\": \"089151070\",\"prodTourCode\": \"089151070\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"福州-三亚5天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"张佳佳\",\"certNo\": \"111111\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"0f009c0ab71d4b09bf79db93da9eeb9f\",\"rightsCode\":\"090179230\",\"sts\":\"02\",\"createTime\":\"2014-09-30 09:06:33\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"2119\",\"price\":\"2119\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"2174\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"2\",\"childNum\":\"0\",\"source\":\"b2c\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"345\",\"validStart\":\"2014-09-30\",\"validEnd\":\"2016-09-30\",\"productList\":[{\"code\": \"090179240\",\"deptDate\": \"\",\"deptPlace\": \"北京\",\"sts\": \"0\",\"prodTourId\": \"089150970\",\"prodTourCode\": \"089150970\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"北京-海口5天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"李君\",\"certNo\": \"230822198310017328\",\"certType\": \"0\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]},{\"code\": \"090179250\",\"deptDate\": \"\",\"deptPlace\": \"北京\",\"sts\": \"0\",\"prodTourId\": \"089765180\",\"prodTourCode\": \"089765180\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"北京-杭州3天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"张佳佳\",\"certNo\": \"111111\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"bb045ed9562b465290edfa53eab230b8\",\"rightsCode\":\"090179190\",\"sts\":\"02\",\"createTime\":\"2014-09-30 09:05:31\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"1145\",\"price\":\"1145\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"975\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"1\",\"childNum\":\"0\",\"source\":\"b2c\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"170\",\"validStart\":\"2014-09-30\",\"validEnd\":\"2016-09-30\",\"productList\":[{\"code\": \"090179200\",\"deptDate\": \"\",\"deptPlace\": \"郑州\",\"sts\": \"0\",\"prodTourId\": \"089151090\",\"prodTourCode\": \"089151090\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"郑州-海口5天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"李君测试\",\"certNo\": \"1成人\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"82f2c00c31ab409e8ddd8dfb1b8ff709\",\"rightsCode\":\"090176200\",\"sts\":\"03\",\"createTime\":\"2014-09-29 17:27:35\",\"cardSts\":\"0\",\"paySts\":\"03\",\"actualPay\":\"0\",\"price\":\"2194\",\"payFlightSts\":\"02\",\"hotelPrice\":\"0\",\"adultPrice\":\"839\",\"childPrice\":\"540\",\"payTime\":\"2014-09-29 17:23:11\",\"payType\":\"\",\"adultNum\":\"1\",\"childNum\":\"1\",\"source\":\"b2c\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"175\",\"validStart\":\"2014-09-29\",\"validEnd\":\"2016-09-29\",\"productList\":[{\"code\": \"090176210\",\"deptDate\": \"2014-11-01\",\"deptPlace\": \"广州\",\"sts\": \"3\",\"prodTourId\": \"089150950\",\"prodTourCode\": \"089150950\",\"adultNum\":\"1\",\"childNum\":\"1\",\"needPay\": \"00\",\"paySts\": \"03\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"广州-杭州4天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"李君\",\"certNo\": \"230822198310017328\",\"certType\": \"0\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"},{\"name\": \"杨爱赢测试\",\"certNo\": \"110104201111242027\",\"certType\": \"0\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"02\"}]}]},{\"rightsId\":\"bcf2a8d98c10457a9520907612f77fd4\",\"rightsCode\":\"090166860\",\"sts\":\"02\",\"createTime\":\"2014-09-28 14:24:41\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"1065\",\"price\":\"1065\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"895\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"1\",\"childNum\":\"0\",\"source\":\"b2c\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"170\",\"validStart\":\"2014-09-28\",\"validEnd\":\"2016-09-28\",\"productList\":[{\"code\": \"090166870\",\"deptDate\": \"\",\"deptPlace\": \"杭州\",\"sts\": \"0\",\"prodTourId\": \"089151270\",\"prodTourCode\": \"089151270\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"杭州-海口5天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"王瑶\",\"certNo\": \"110104198008143026\",\"certType\": \"0\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"17b2c90565ad48d28b6a6ca3207f0b51\",\"rightsCode\":\"090153600\",\"sts\":\"02\",\"createTime\":\"2014-09-26 17:40:11\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"2229\",\"price\":\"2229\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"2314\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"2\",\"childNum\":\"0\",\"source\":\"b2c\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"315\",\"validStart\":\"2014-09-26\",\"validEnd\":\"2016-09-26\",\"productList\":[{\"code\": \"090153610\",\"deptDate\": \"\",\"deptPlace\": \"北京\",\"sts\": \"0\",\"prodTourId\": \"089150970\",\"prodTourCode\": \"089150970\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"北京-海口5天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"张园园\",\"certNo\": \"成人女\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]},{\"code\": \"090153620\",\"deptDate\": \"\",\"deptPlace\": \"北京\",\"sts\": \"0\",\"prodTourId\": \"089151130\",\"prodTourCode\": \"089151130\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"北京-厦门4天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"李佳佳\",\"certNo\": \"成人男\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"dc0f8e9e37b24d049313a3dbed21864f\",\"rightsCode\":\"090153520\",\"sts\":\"02\",\"createTime\":\"2014-09-26 17:36:06\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"2229\",\"price\":\"2229\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"2314\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"2\",\"childNum\":\"0\",\"source\":\"b2c\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"315\",\"validStart\":\"2014-09-26\",\"validEnd\":\"2016-09-26\",\"productList\":[{\"code\": \"090153530\",\"deptDate\": \"\",\"deptPlace\": \"北京\",\"sts\": \"0\",\"prodTourId\": \"089150970\",\"prodTourCode\": \"089150970\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"北京-海口5天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"张园园\",\"certNo\": \"成人女\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]},{\"code\": \"090153540\",\"deptDate\": \"\",\"deptPlace\": \"北京\",\"sts\": \"0\",\"prodTourId\": \"089151130\",\"prodTourCode\": \"089151130\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"北京-厦门4天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"李佳佳\",\"certNo\": \"成人男\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"9a1092af64b042768644a3facd5f6c48\",\"rightsCode\":\"090153350\",\"sts\":\"02\",\"createTime\":\"2014-09-26 17:28:14\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"2590\",\"price\":\"2590\",\"payFlightSts\":\"02\",\"hotelPrice\":\"0\",\"adultPrice\":\"1950\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"2\",\"childNum\":\"0\",\"source\":\"b2c\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"0\",\"validStart\":\"2014-09-26\",\"validEnd\":\"2016-09-26\",\"productList\":[{\"code\": \"090153360\",\"deptDate\": \"\",\"deptPlace\": \"西安\",\"sts\": \"0\",\"prodTourId\": \"089151080\",\"prodTourCode\": \"089151080\",\"adultNum\":\"2\",\"childNum\":\"0\",\"needPay\": \"00\",\"paySts\": \"03\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"西安-海口5天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"张佳佳\",\"certNo\": \"111111\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"},{\"name\": \"李佳佳\",\"certNo\": \"成人男\",\"certType\": \"9\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"c8bd524259ff421bb429a36e1e63a0b7\",\"rightsCode\":\"090118770\",\"sts\":\"03\",\"createTime\":\"2014-09-22 09:09:05\",\"cardSts\":\"0\",\"paySts\":\"03\",\"actualPay\":\"0\",\"price\":\"984\",\"payFlightSts\":\"00\",\"hotelPrice\":\"0\",\"adultPrice\":\"839\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"1\",\"childNum\":\"0\",\"source\":\"b2c\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"145\",\"validStart\":\"2014-09-22\",\"validEnd\":\"2016-09-22\",\"productList\":[{\"code\": \"090118780\",\"deptDate\": \"2014-10-22\",\"deptPlace\": \"武汉\",\"sts\": \"0\",\"prodTourId\": \"089151240\",\"prodTourCode\": \"089151240\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"01\",\"paySts\": \"01\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"武汉-厦门4天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"李君\",\"certNo\": \"230822198310017328\",\"certType\": \"0\",\"birthday\": \"1983-10-01\",\"mobile\": \"\",\"type\": \"01\"}]}]},{\"rightsId\":\"a0877fb44aef411d931a1cb36fb20e4b\",\"rightsCode\":\"090104460\",\"sts\":\"02\",\"createTime\":\"2014-09-19 16:47:22\",\"cardSts\":\"0\",\"paySts\":\"01\",\"actualPay\":\"1759\",\"price\":\"1759\",\"payFlightSts\":\"02\",\"hotelPrice\":\"0\",\"adultPrice\":\"1249\",\"childPrice\":\"0\",\"payTime\":\"\",\"payType\":\"\",\"adultNum\":\"1\",\"childNum\":\"0\",\"source\":\"b2c\",\"memberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardMemberId\":\"5e6a88cc14d04917a8e09b93df9a962c\",\"cardNo\":\"\",\"cardSts\":\"0\",\"singlePrice\":\"190\",\"validStart\":\"2014-09-19\",\"validEnd\":\"2016-09-19\",\"productList\":[{\"code\": \"090104470\",\"deptDate\": \"\",\"deptPlace\": \"郑州\",\"sts\": \"0\",\"prodTourId\": \"089151380\",\"prodTourCode\": \"089151380\",\"adultNum\":\"1\",\"childNum\":\"0\",\"needPay\": \"00\",\"paySts\": \"03\",\"hotelLevCode\": \"02\",\"actualPay\": \"0\",\"title\": \"郑州-三亚5天1晚双飞自由行(四星级/舒适酒店)\",\"passengerList\":[{\"name\": \"李君\",\"certNo\": \"230822198310017328\",\"certType\": \"0\",\"birthday\": \"\",\"mobile\": \"\",\"type\": \"01\"}]}]}]}";
		
		//Map<String,Map<String,Object>> map = new JsonMapper().json2Map(json);
		//new JsonMapper().json2List("");
	}

	@JsonFilter("axfilter")
	interface DefaultJsonFilter {
		// 默认的@JsonFilter。以实现按字段过滤
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public String toJsonStr(Object value) throws JsonGenerationException,
			JsonMappingException, IOException {
		return objectMapper.writeValueAsString(value);
	}

	public String toJsonStr(Object value, String[] properties)
			throws JsonGenerationException, JsonMappingException, IOException {
		SerializationConfig cfg = objectMapper.getSerializationConfig();
		cfg.addMixInAnnotations(value.getClass(), DefaultJsonFilter.class);

		SimpleBeanPropertyFilter someFilter = SimpleBeanPropertyFilter
				.filterOutAllExcept(properties);
		SimpleFilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("axfilter", someFilter);

		return objectMapper.writer(filterProvider).writeValueAsString(value);

	}

	public String toJsonStrWithExcludeProperties(Object value,
			String[] properties2Exclude) throws JsonGenerationException,
			JsonMappingException, IOException {

		SerializationConfig cfg = objectMapper.getSerializationConfig();
		cfg.addMixInAnnotations(value.getClass(), DefaultJsonFilter.class);

		SimpleBeanPropertyFilter someFilter = SimpleBeanPropertyFilter
				.serializeAllExcept(properties2Exclude);
		SimpleFilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("axfilter", someFilter);

		return objectMapper.writer(filterProvider).writeValueAsString(value);
	}

	public void writeJsonStr(OutputStream out, Object value)
			throws JsonGenerationException, JsonMappingException, IOException {
		objectMapper.writeValue(out, value);
	}

	public void writeJsonStr(OutputStream out, Object value, String[] properties)
			throws JsonGenerationException, JsonMappingException, IOException {

		objectMapper.writer(
				new SimpleFilterProvider().addFilter(
						AnnotationUtils.getValue(
								AnnotationUtils.findAnnotation(
										value.getClass(), JsonFilter.class))
								.toString(), SimpleBeanPropertyFilter
								.filterOutAllExcept(properties))).writeValue(
				out, value);

	}

	public void writeJsonStrWithExcludeProperties(OutputStream out,
			Object value, String[] properties2Exclude)
			throws JsonGenerationException, JsonMappingException, IOException {
		objectMapper.writer(
				new SimpleFilterProvider().addFilter(
						AnnotationUtils.getValue(
								AnnotationUtils.findAnnotation(
										value.getClass(), JsonFilter.class))
								.toString(), SimpleBeanPropertyFilter
								.serializeAllExcept(properties2Exclude)))
				.writeValue(out, value);

	}

	
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Object>> json2Map(String json) {
		// json ="{\"result\": {\"resultCode\": \"\",\"message\": \"\"},\"memberId\": \"c3de6275843e45a284389a9cf4b6b27c\",\"email\": \"feifie@123.com\",\" memberCode \": \"000001040\",\"mobile\":\"13800138000\"}";
		try {
			objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			
			Map<String, Map<String, Object>> maps = objectMapper.readValue(json, Map.class);
			 
			return maps;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public List<LinkedHashMap<String, Object>> json2List(String json) {
		/*json = "[{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"},"
				+ "{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}]";*/
		
		try {
			@SuppressWarnings("unchecked")
			List<LinkedHashMap<String, Object>> list = objectMapper.readValue(json, List.class);
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				Set<String> set = map.keySet();
				for (Iterator<String> it = set.iterator(); it.hasNext();) {
					String key = it.next();
					System.out.println(key + ":" + map.get(key));
				}
			}
			return list;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return null;
	}

}
