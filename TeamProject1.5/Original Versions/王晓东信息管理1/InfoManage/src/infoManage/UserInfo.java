package infoManage;

import java.io.*;
import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

//用户类
public class UserInfo {

	private String studentNo = "", name = "", tele = "", addr = "";// 学号、姓名、号码、地址
	private boolean poverty = false;// 是否贫困生
	int type = 0;// 0为普通用户、1为管理员、2为超级管理员
	String password;// 密码

	// 构造函数
	public UserInfo(String studentNo, String name, String tele, String addr,
			boolean poverty, int type, String password) {
		super();
		this.studentNo = studentNo;
		this.name = name;
		this.tele = tele;
		this.addr = addr;
		this.poverty = poverty;
		this.type = type;
		this.password = password;
	}

	// 复制对象的构造函数
	public UserInfo(UserInfo user) {
		super();
		this.studentNo = user.studentNo;
		this.name = user.name;
		this.tele = user.tele;
		this.addr = user.addr;
		this.poverty = user.poverty;
		this.type = user.type;
		this.password = user.password;
	}

	// 设置密码
	private void setPassword(String password) {
		this.password = password;
	}

	// 返回密码
	private String getPassword() {
		return password;
	}

	// 返回用户级别
	public int getType() {
		return type;
	}

	// 设置用户级别
	public void setType(int type) {
		this.type = type;
	}

	// 返回学号
	public String getStudentNo() {
		return studentNo;
	}

	// 设置学号
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	// 返回姓名
	public String getName() {
		return name;
	}

	// 设置姓名
	public void setName(String name) {
		this.name = name;
	}

	// 返回号码
	public String getTele() {
		return tele;
	}

	// 设置号码
	public void setTele(String tele) {
		this.tele = tele;
	}

	// 返回地址
	public String getAddr() {
		return addr;
	}

	// 设置地址
	public void setAddr(String addr) {
		this.addr = addr;
	}

	// 返回是否贫困生
	public boolean isPoverty() {
		return poverty;
	}

	// 设置是否贫苦生
	public void setPoverty(boolean poverty) {
		this.poverty = poverty;
	}

	// 用户读取自己的信息显示在"SelfInfo.xls"表格中
	public void showInfo() {
		try {
			// 创建文件
			WritableWorkbook book = Workbook
					.createWorkbook(new File("Info.xls"));
			// 创建页面
			WritableSheet sheet = book.createSheet("第一頁", 0);
			// 第一行写入信息
			sheet.addCell(new Label(0, 0, "姓名"));
			sheet.addCell(new Label(1, 0, "学号"));
			sheet.addCell(new Label(2, 0, "手机号码"));
			sheet.addCell(new Label(3, 0, "地址"));
			sheet.addCell(new Label(4, 0, "是否贫困"));
			sheet.addCell(new Label(5, 0, "身份"));
			// 第二行写入信息
			sheet.addCell(new Label(0, 1, name));
			sheet.addCell(new Label(1, 1, studentNo));
			sheet.addCell(new Label(2, 1, tele));
			sheet.addCell(new Label(3, 1, addr));
			sheet.addCell(new Label(4, 1, poverty ? "是" : "否"));
			String sta = "";
			if (type == 0)
				sta = "普通员工";
			else if (type == 1)
				sta = "管理员";
			else
				sta = "超级管理员";
			sheet.addCell(new Label(5, 1, sta));

			book.write();// 写入数据
			book.close();// 关闭文件

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// adm或SuperAdm读取一部分符合条件的人的信息显示在"AllUserInfo.xls"中
	public void showInfo(UserInfo[] user) {
		if (this.type == 0) {
			System.out.println("调用权限错误");
			return;
		}
		try {
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(
					"AllUserInfo.xls"));
			// 创建页面
			WritableSheet sheet = book.createSheet("第一頁", 0);
			// 第一行写入信息
			sheet.addCell(new Label(0, 0, "姓名"));
			sheet.addCell(new Label(1, 0, "学号"));
			sheet.addCell(new Label(2, 0, "手机号码"));
			sheet.addCell(new Label(3, 0, "地址"));
			sheet.addCell(new Label(4, 0, "是否贫困"));
			sheet.addCell(new Label(5, 0, "身份"));
			// 第i+1行写入信息
			int i = 1;
			for (; i <= user.length; i++) {
				sheet.addCell(new Label(0, i, user[i - 1].name));
				sheet.addCell(new Label(1, i, user[i - 1].studentNo));
				sheet.addCell(new Label(2, i, user[i - 1].tele));
				sheet.addCell(new Label(3, i, user[i - 1].addr));
				sheet.addCell(new Label(4, i, user[i - 1].poverty ? "是" : "否"));
				String sta = "";
				if (user[i - 1].type == 0)
					sta = "普通员工";
				else if (user[i - 1].type == 1)
					sta = "管理员";
				else
					sta = "超级管理员";
				sheet.addCell(new Label(5, i, sta));

			}
			book.write();// 写入信息
			book.close();// 关闭文件

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// 产生新的用户并将信息存入数据库,在这里假设数据库类名为DataBase，同时假设数据库中有一个
	// 函数set(UserInfo user),将新产生的user的信息存储到数据库中，存储成功返回true
	public boolean setUpNewUser(String studentNo, String name, String tele,
			String addr, boolean poverty, int type, String password) {
		if (this.type != 2)
			return false;// 无权限则返回false
		else {
			UserInfo user = new UserInfo(studentNo, name, tele, addr, poverty,
					type, password);
			if (user == null)
				return false;// 未能生成新用户
			if (!DataBase.set(user))
				return false;// 新用户信息写入数据库失败
			return true;
		}

	}

	// 这里假设数据库类中有一个函数delete( String studentNo),根据学号在数据库中删除单个用户，
	// 删除成功返回true
	public boolean deleteUser(String studentNo) {
		if (this.type != 2)
			return false;// 无权限则返回false
		return DataBase.delete(studentNo);
	}

	// 这里假设数据库类中有一个函数getUserInfo( String studentNo),返回具有固定学号的人的信息
	// 该函数使用户查询自己信息
	public UserInfo getUserInfo() {
		return DataBase.getUser(this.studentNo);
	}

	// ？？？？？？这里有一些疑问？在里面产生的数组在外面返回？？？？？？？？？？？？？？？
	// 根据学号序列查询一个以上人信息，这里假设数据库类中有一个函数getUserInfo( String studentNo),
	// getUserInfo( String studentNo)根据学号在数据库中查找单个用户信息
	public UserInfo[] getUserInfo(String[] studentNoArr) {
		if (this.type == 0)
			return null;// 普通用户无权查看其他用户信息
		UserInfo[] users = new UserInfo[studentNoArr.length];
		for (int i = 0; i < studentNoArr.length; i++) {
			users[i] = new UserInfo(DataBase.getUser(studentNoArr[i]));// 调用复制式构造函数
		}
		return users;
	}

	// 编辑自己的信息
	public void editSelfInfo(String studentNo, String name, String tele,
			String addr, boolean poverty, int type, String password) {
		DataBase.delete(this.studentNo);
		DataBase.setUpNewUser(studentNo, name, tele, addr, poverty, type,
				password);
	}

	// 编辑别人的信息，SuperAdmin有此权限,formerID为修改前的学号
	public void editAnyInfo(String formerID, String studentNo, String name,
			String tele, String addr, boolean poverty, int type, String password) {
		if (type != 2) {
			return;
		}
		DataBase.delete(formerID);
		DataBase.setUpNewUser(studentNo, name, tele, addr, poverty, type,
				password);
	}

	//用户修改自己的密码，若修改成功返回null，修改失败返回失败原因
	private String editPassword(String formerPass,String newPass,String newPassConfirm)
	{
		String password=DataBase.getUser(this.studentNo).password;
		if(password!=formerPass) return "输入原密码错误";
		else if(newPass!=newPassConfirm) return"输入的新密码不一致";
		else return null;
	}
	public static void main(String[] args) {
		UserInfo a = new UserInfo("a", "b", "c", "d", true, 0, "123456");
		a.showInfo();

	}
}
