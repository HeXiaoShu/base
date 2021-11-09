package com.util;

import java.math.BigDecimal;
import java.util.List;

/**
 * 精确运算工具类
 */
public class FinanceUtils {

	private static final int ROUND_DEFAULT_2 = 2;
	// 除法运算默认精度
	private static final int DEF_DIV_SCALE = 8;

	private FinanceUtils() {

	}

	/**
	 * 精确加法
	 */
	public static double add(double value1, double value2) {
		BigDecimal b1 = BigDecimal.valueOf(value1);
		BigDecimal b2 = BigDecimal.valueOf(value2);
		return b1.add(b2).doubleValue();
	}

	/**
	 * 精确减法
	 */
	public static double sub(double value1, double value2) {
		BigDecimal b1 = BigDecimal.valueOf(value1);
		BigDecimal b2 = BigDecimal.valueOf(value2);
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 精确乘法
	 */
	public static double mul(double value1, double value2) {
		BigDecimal b1 = BigDecimal.valueOf(value1);
		BigDecimal b2 = BigDecimal.valueOf(value2);
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 多数精确乘法
	 */
	public static double mulMany(double... value) {
		BigDecimal bm = BigDecimal.valueOf(1);
		for (int i = 0; i < value.length; i++) {
			BigDecimal b1 = BigDecimal.valueOf(value[i]);
			bm = bm.multiply(b1);
		}

		return bm.doubleValue();
	}

	public static double addMany(List<BigDecimal> allAmount) {

		BigDecimal bm = BigDecimal.valueOf(0);
		for(BigDecimal Num : allAmount) {
			bm =bm.add(Num);
		}
		return bm.doubleValue();
	}


	/**
	 * 精确除法 使用默认精度8
	 */
	public static double div(double value1, double value2) {
		return div(value1, value2, DEF_DIV_SCALE);
	}

	/**
	 * 精确除法
	 *
	 * @param scale 精度
	 */
	public static double div(double value1, double value2, int scale) {

		BigDecimal b1 = BigDecimal.valueOf(value1);
		BigDecimal b2 = BigDecimal.valueOf(value2);
		// return b1.divide(b2, scale).doubleValue();
		// ROUND_HALF_UP //四舍五入
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_EVEN).doubleValue(); // 四舍六入五分法
	}

	public static double divNoExp(double value1, double value2, int scale) {

		BigDecimal b1 = BigDecimal.valueOf(value1);
		BigDecimal b2 = BigDecimal.valueOf(value2);
		// return b1.divide(b2, scale).doubleValue();
		// ROUND_HALF_UP //四舍五入
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_EVEN).doubleValue(); // 四舍六入五分法
	}

	/**
	 * 四舍六入五分法
	 *
	 * @param scale 小数点后保留几位
	 */
	public static double round(double v, int scale) {
		return div(v, 1, scale);
	}

	public static double round_2(double v) {
		return divNoExp(v, 1, ROUND_DEFAULT_2);
	}

	/**
	 * 比较大小
	 */
	public static boolean equalTo(BigDecimal b1, BigDecimal b2) {
		if (b1 == null || b2 == null) {
			return false;
		}
		return 0 == b1.compareTo(b2);
	}

	public static void main(String[] args) throws IllegalAccessException {
		double value1 = 1.2345678912311;
		double value2 = 9.1234567890123;
		BigDecimal value3 = new BigDecimal(Double.toString(value1));
		BigDecimal value4 = new BigDecimal(Double.toString(value2));
		System.out.println("精确加法=================" + FinanceUtils.add(value1, value2));
		System.out.println("精确减法=================" + FinanceUtils.sub(value1, value2));
		System.out.println("精确乘法=================" + FinanceUtils.mul(value1, value2));
		System.out.println("精确除法 使用默认精度 =================" + FinanceUtils.div(value1, value2));
		System.out.println("精确除法  设置精度=================" + FinanceUtils.div(value1, value2, 20));
		System.out.println("四舍五入   小数点后保留几位 =================" + FinanceUtils.round(value1, 10));
		System.out.println("比较大小 =================" + FinanceUtils.equalTo(value3, value4));

	}
}
