package com.hj.utils;

/**   
* @Title: MathRandomUtil.java
* @Package com.hj.utils
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年8月10日 下午4:16:29
* @version V1.0   
*/
public class MathRandomUtil {
		 
		 public double rate0;
		 public double rate1;
		 public double rate2;
		 public double rate3;
		 public double rate4;
		 public double rate5;
		 public double rate6;
		 public double rate7;
		 public double rate8;
		 public double rate9;
		 
		 public MathRandomUtil(){
			 
		 }
		 
		 /**
		  * 传入十个变量
		  * @param a
		  * @param b
		  * @param c
		  * @param d
		  * @param e
		  * @param f
		  * @param g
		  * @param h
		  * @param i
		  * @param j
		  */
		 public MathRandomUtil(double[] score){
			 this.rate0 = score[0];
			 this.rate1 = score[1];
			 this.rate2 = score[2];
			 this.rate3 = score[3];
			 this.rate4 = score[4];
			 this.rate5 = score[5];
			 this.rate6 = score[6];
			 this.rate7 = score[7];
			 this.rate8 = score[8];
			 this.rate9 = score[9];
		 }

		 /**
		  * Math.random()产生一个double型的随机数，判断一下
		  * 例如0出现的概率为%50，则介于0到0.50中间的返回0
		     * @return int
		     *
		     */
		 public int PercentageRandom()
		 {
		  double randomNumber;
		  randomNumber = Math.random();
		  if (randomNumber >= 0 && randomNumber <= rate0)
		  {
		   return 0;
		  }
		  else if (randomNumber >= rate0 / 100 && randomNumber <= rate0 + rate1)
		  {
		   return 1;
		  }
		  else if (randomNumber >= rate0 + rate1
		    && randomNumber <= rate0 + rate1 + rate2)
		  {
		   return 2;
		  }
		  else if (randomNumber >= rate0 + rate1 + rate2
		    && randomNumber <= rate0 + rate1 + rate2 + rate3)
		  {
		   return 3;
		  }
		  else if (randomNumber >= rate0 + rate1 + rate2 + rate3
		    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4)
		  {
		   return 4;
		  }
		  else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4
		    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
		      + rate5)
		  {
		   return 5;
		  }
		  else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4+ rate5
				    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
				      + rate5+ rate6)
		  {
		   return 6;
		  }
		  else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4+ rate5+ rate6
				    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
				      + rate5+ rate6+ rate7)
		  {
			 return 7;
		  }
		  else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4+ rate5+ rate6+ rate7
				    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
				      + rate5+ rate6+ rate7+ rate8)
		{
			 return 8;
		}
		  else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4+ rate5+ rate6+ rate7+ rate8
				    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
				      + rate5+ rate6+ rate7+ rate8+ rate9)
		{
			 return 9;
		}
		  return -1;
		 }

}
