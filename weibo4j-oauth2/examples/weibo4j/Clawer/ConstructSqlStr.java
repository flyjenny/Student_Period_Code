package weibo4j.Clawer;

import java.io.UnsupportedEncodingException;

//'\xF0\x9F\x98\xB1\xF0\x9F...'
//'\xF0\x9F\x8D\xBB\xF0\x9F...'55356
//'\xF0\x9F\x9A\x8C'55357
//'\xF0\x9F\x92\x81\xE2\x9D...' 55357 10084 10024
//'\xF0\x9F\x92\x89\xE6\xAD...' 55357
public class ConstructSqlStr {
	public static void main(String[] args) {
		String str = "😊好中意个粉色壳！哈哈~😘😘✨✨手机终于唔洗停机了！😭   多谢🍟晚安！❤";
		//str = "喝晕了 🍺🍺🍺🍺🍻🍻🍻🍻";
		//str = "Finished!📝";
		//str = " 🍻🍺😱";
		str = "期..𣎴容...祝自己新年快乐！";
		//str = "💁❤❤🐶🐻🐤👦a👦❤";
		// str="💉止みで     自己整死自己";
		//str="insert into weibo.weibo1 values(3403758745043177,\"2012-01-20 03:53:06\",\"真的这世界太危险😔 。每当在深夜自卑绝望时，有人和我分担心事，有人适时夸我一下，这都是我加油的动力，被一个叫生活的东西干了无数次我们要更有勇气这才对~要去做自己的主角！@权星子Quincy @马静怡_Mmy\",-1,-1,\"\",-1,0,0,\"1842507152\",\"秋元梨美Akimoto_Rimi\",11,5,\"北京 朝阳区\",\"☆一生中最爱☆Akimoto_梨美あきもとりみ。天蝎座奇葩一枚，语言天赋极高。影视媒体设计学者。Disgn is my life time pursuit。\",\"f\",4803,252,4217,208,\"2010-10-05 00:00:00\",\"n\",220,\"\")";
		for (char c : str.toCharArray()) {
			System.out.println((int) c);
		}
		String newStr = new String();
		
		char[] newChar = str.toCharArray();
		for (int i = 0; i < newChar.length; i++) {
			try {
				if ((int) newChar[i] == 55356 || (int) newChar[i] == 55357) {

					i += 2;
				}
				if ((int) newChar[i] == 10024 || (int) newChar[i] == 10084) {
					i++;
				}
				if ((int) newChar[i] != 55356 && (int) newChar[i] != 55357
						&& (int) newChar[i] != 10024
						&& (int) newChar[i] != 10084) {
					newStr += newChar[i];
				} else {
					i--;
				}
			} catch (java.lang.ArrayIndexOutOfBoundsException e) {

			}
		}
		System.out.println(newStr);

	}
}
