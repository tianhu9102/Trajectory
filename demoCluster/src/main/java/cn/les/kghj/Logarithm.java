package cn.les.kghj;

// logx(y) =loge(y) / loge(x)
// double x = Math.log(5) ，表示以e为底的自然对数
public class Logarithm {
	static public double log(double x,double base){
		return Math.log(x)/Math.log(base);
	}
}
