package infoManage;

import java.io.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import FileSystem.*;

public class UserInfoList implements Serializable{
	Map<String, UserInfo> List;
	
	public static void main(String[] args){
		UserInfoList List = new UserInfoList();
		FileSystem file = new FileSystem();
	//	file.setUserInfoList(List);
		UserInfoList test = file.getUserInfoList();
		test.showInfo();
	}
	public UserInfoList(){
		FileSystem file = new FileSystem();
		if (file.getUserInfoList()==null){
			List = new HashMap<String, UserInfo>();
		}
		else {
			List = file.getUserInfoList().getList();
		}
	}
	public UserInfoList(Connection con){
		List = new HashMap<String, UserInfo>();
	}
	public Map<String, UserInfo> getList(){
		return List;
	}
	public String getUserName(String ID){
		if (List.get(ID)==null)
			return null;
		return List.get(ID).getName();
	}
	public String getUserID(String ID){
		if (List.get(ID)==null)
			return null;
		return List.get(ID).getStudentNo();
	}
	public String getUserAddr(String ID){
		if (List.get(ID)==null)
			return null;
		return List.get(ID).getAddr();
	}
	public String getUserTel(String ID){
		if (List.get(ID)==null)
			return null;
		return List.get(ID).getTele();
	}
	public int getUserType(String ID){
		if (List.get(ID)==null)
			return -1;
		return List.get(ID).getType();
	}
	public boolean isPoverty(String ID){
		return List.get(ID).isPoverty();
	}
	public boolean checkPassword(String ID, String pas){
		if (List.get(ID).getPassword().equals(pas))
			return true;
		else return false;
	}
	public boolean addUser(String ID, String name, String password, boolean gender, int type, String tele, String addr, boolean poverty){
		UserInfo user = new UserInfo(ID, name, tele, addr, poverty, type, password, gender);
		if (user != null){
			List.put(ID, user);
			return true;
		}
		else return false;
	}
	public boolean deleteUser(int ID){
		if (List.remove(ID)!=null){
			return true;
		}
		else return false;
	}
	public boolean editUser(String ID, String name, String tele,
			String addr, boolean poverty, int type, String password, boolean gender){
		UserInfo user = new UserInfo(ID, name, tele, addr, poverty, type, password, gender);
		if (List.get(ID)!=null){
			if (password == null){
				user.setPassword(List.get(ID).getPassword());
			}
			List.put(ID, user);
			return true;
			}
		else return false;
	}
/*	public void setUserList(){
		new FileSystem().setUserInfoList(this);
	}*/
	public void showInfo() {
		try {
			// ���ļ�
			WritableWorkbook book = Workbook.createWorkbook(new File(
					"AllUserInfo.xls"));
			// ����ҳ��
			WritableSheet sheet = book.createSheet("��һ�", 0);
			// ��һ��д����Ϣ
			sheet.addCell(new Label(0, 0, "����"));
			sheet.addCell(new Label(1, 0, "ѧ��"));
			sheet.addCell(new Label(2, 0, "�ֻ�����"));
			sheet.addCell(new Label(3, 0, "��ַ"));
			sheet.addCell(new Label(4, 0, "�Ƿ�ƶ��"));
			sheet.addCell(new Label(5, 0, "���"));
			// ��i+1��д����Ϣ
			int i = 1;
			Iterator itr = List.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry entry=(Map.Entry) itr.next();
				sheet.addCell(new Label(0, i, ((UserInfo)entry.getValue()).getName()));
				sheet.addCell(new Label(1, i, ((UserInfo)entry.getValue()).getStudentNo()));
				sheet.addCell(new Label(2, i, ((UserInfo)entry.getValue()).getTele()));
				sheet.addCell(new Label(3, i, ((UserInfo)entry.getValue()).getAddr()));
				sheet.addCell(new Label(4, i, ((UserInfo)entry.getValue()).isPoverty() ? "��" : "��"));
				String sta = "";
				switch(((UserInfo)entry.getValue()).getType()){
				case 1:{
					sta = "����Ա";
					break;
				}
				case 2:{
					sta = "��������Ա";
					break;
				}
				default:{
					sta = "��ͨԱ��";
					break;
				}
				}
				sheet.addCell(new Label(5, i, sta));
				i++;
			}
			book.write();// д����Ϣ
			book.close();// �ر��ļ�

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
//�û���
class UserInfo implements Serializable{

	private String studentNo = "", name = "", tele = "", addr = "";// ѧ�š����������롢��ַ
	private boolean poverty = false;// �Ƿ�ƶ����
	int type;// 0Ϊ��ͨ�û���1Ϊ����Ա��2Ϊ��������Ա
	String password;// ����
	boolean Gender;//�Ա�:0Ϊ��,1ΪŮ

	// ���캯��
	public UserInfo(String studentNo, String name, String tele, String addr,
			boolean poverty, int type, String password, boolean gender) {
		super();
		this.studentNo = studentNo;
		this.name = name;
		this.tele = tele;
		this.addr = addr;
		this.poverty = poverty;
		this.type = type;
		this.password = password;
		this.Gender = gender;
	}

	// ���ƶ���Ĺ��캯��
	public UserInfo(UserInfo user) {
		super();
		this.studentNo = user.studentNo;
		this.name = user.name;
		this.tele = user.tele;
		this.addr = user.addr;
		this.poverty = user.poverty;
		this.type = user.type;
		this.password = user.password;
		this.Gender = user.Gender;
	}

	// ��������
	public void setPassword(String password) {
		this.password = new String(password);
	}

	// ��������
	public String getPassword() {
		return password;
	}

	// �����û�����
	public int getType() {
		return type;
	}

	// �����û�����
	public void setType(int type) {
		this.type = type;
	}

	// ����ѧ��
	public String getStudentNo() {
		return studentNo;
	}

	// ����ѧ��
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	// ��������
	public String getName() {
		return name;
	}

	// ��������
	public void setName(String name) {
		this.name = name;
	}

	// ���غ���
	public String getTele() {
		return tele;
	}

	// ���ú���
	public void setTele(String tele) {
		this.tele = tele;
	}

	// ���ص�ַ
	public String getAddr() {
		return addr;
	}

	// ���õ�ַ
	public void setAddr(String addr) {
		this.addr = addr;
	}

	// �����Ƿ�ƶ����
	public boolean isPoverty() {
		return poverty;
	}

	// �����Ƿ�ƶ����
	public void setPoverty(boolean poverty) {
		this.poverty = poverty;
	}

	public boolean getGender(){
		return Gender;
	}
	
	public void setGender(boolean gender){
		Gender = gender;
	}
	
	// �û���ȡ�Լ�����Ϣ��ʾ��"SelfInfo.xls"�����
	public void showInfo() {
		try {
			// �����ļ�
			WritableWorkbook book = Workbook
					.createWorkbook(new File("Info.xls"));
			// ����ҳ��
			WritableSheet sheet = book.createSheet("��һ�", 0);
			// ��һ��д����Ϣ
			sheet.addCell(new Label(0, 0, "����"));
			sheet.addCell(new Label(1, 0, "ѧ��"));
			sheet.addCell(new Label(2, 0, "�ֻ�����"));
			sheet.addCell(new Label(3, 0, "��ַ"));
			sheet.addCell(new Label(4, 0, "�Ƿ�ƶ��"));
			sheet.addCell(new Label(5, 0, "���"));
			// �ڶ���д����Ϣ
			sheet.addCell(new Label(0, 1, name));
			sheet.addCell(new Label(1, 1, studentNo));
			sheet.addCell(new Label(2, 1, tele));
			sheet.addCell(new Label(3, 1, addr));
			sheet.addCell(new Label(4, 1, poverty ? "��" : "��"));
			String sta = "";
			if (type == 0)
				sta = "��ͨԱ��";
			else if (type == 1)
				sta = "����Ա";
			else
				sta = "��������Ա";
			sheet.addCell(new Label(5, 1, sta));

			book.write();// д������
			book.close();// �ر��ļ�

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// adm��SuperAdm��ȡһ���ַ����������˵���Ϣ��ʾ��"AllUserInfo.xls"��
/*	ublic void showInfo(UserInfo[] user) {
		if (this.type == 0) {
			System.out.println("����Ȩ�޴���");
			return;
		}
		try {
			// ���ļ�
			WritableWorkbook book = Workbook.createWorkbook(new File(
					"AllUserInfo.xls"));
			// ����ҳ��
			WritableSheet sheet = book.createSheet("��һ�", 0);
			// ��һ��д����Ϣ
			sheet.addCell(new Label(0, 0, "����"));
			sheet.addCell(new Label(1, 0, "ѧ��"));
			sheet.addCell(new Label(2, 0, "�ֻ�����"));
			sheet.addCell(new Label(3, 0, "��ַ"));
			sheet.addCell(new Label(4, 0, "�Ƿ�ƶ��"));
			sheet.addCell(new Label(5, 0, "���"));
			// ��i+1��д����Ϣ
			int i = 1;
			for (; i <= user.length; i++) {
				sheet.addCell(new Label(0, i, user[i - 1].name));
				sheet.addCell(new Label(1, i, user[i - 1].studentNo));
				sheet.addCell(new Label(2, i, user[i - 1].tele));
				sheet.addCell(new Label(3, i, user[i - 1].addr));
				sheet.addCell(new Label(4, i, user[i - 1].poverty ? "��" : "��"));
				String sta = "";
				if (user[i - 1].type == 0)
					sta = "��ͨԱ��";
				else if (user[i - 1].type == 1)
					sta = "����Ա";
				else
					sta = "��������Ա";
				sheet.addCell(new Label(5, i, sta));

			}
			book.write();// д����Ϣ
			book.close();// �ر��ļ�

		} catch (Exception e) {
			System.out.println(e);
		}
	}*/

	// �����µ��û�������Ϣ�������ݿ�,������������ݿ�����ΪDataBase��ͬʱ�������ݿ�����һ��
	// ����set(UserInfo user),���²�����user����Ϣ�洢�����ݿ��У��洢�ɹ�����true
/*	public boolean setUpNewUser(String studentNo, String name, String tele,
			String addr, boolean poverty, int type, String password) {
		if (this.type != 2)
			return false;// ��Ȩ���򷵻�false
		else {
			UserInfo user = new UserInfo(studentNo, name, tele, addr, poverty,
					type, password);
			if (user == null)
				return false;// δ���������û�
			if (!new FileSystem().setUserInfo(user))
				return false;// ���û���Ϣд�����ݿ�ʧ��
			return true;
		}

	}

	// ����������ݿ�������һ������delete( String studentNo),����ѧ�������ݿ���ɾ�������û���
	// ɾ���ɹ�����true
	public boolean deleteUser(String studentNo) {
		if (this.type != 2)
			return false;// ��Ȩ���򷵻�false
		return new FileSystem().deleteUser(studentNo);
	}

	// ����������ݿ�������һ������getUserInfo( String studentNo),���ؾ��й̶�ѧ�ŵ��˵���Ϣ
	// �ú���ʹ�û���ѯ�Լ���Ϣ
	public UserInfo getUserInfo() {
		return new FileSystem().getUserInfo(this.studentNo);
	}

	// ������������������һЩ���ʣ���������������������淵�أ�����������������������������
	// ����ѧ�����в�ѯһ����������Ϣ������������ݿ�������һ������getUserInfo( String studentNo),
	// getUserInfo( String studentNo)����ѧ�������ݿ��в��ҵ����û���Ϣ
	public UserInfo[] getUserInfo(String[] studentNoArr) {
		if (this.type == 0)
			return null;// ��ͨ�û���Ȩ�鿴�����û���Ϣ
		UserInfo[] users = new UserInfo[studentNoArr.length];
		for (int i = 0; i < studentNoArr.length; i++) {
			users[i] = new UserInfo(DataBase.getUser(studentNoArr[i]));// ���ø���ʽ���캯��
		}
		return users;
	}

	// �༭�Լ�����Ϣ
	public void editSelfInfo(String studentNo, String name, String tele,
			String addr, boolean poverty, int type, String password) {
		DataBase.delete(this.studentNo);
		DataBase.setUpNewUser(studentNo, name, tele, addr, poverty, type,
				password);
	}

	// �༭���˵���Ϣ��SuperAdmin�д�Ȩ��,formerIDΪ�޸�ǰ��ѧ��
	public void editAnyInfo(String formerID, String studentNo, String name,
			String tele, String addr, boolean poverty, int type, String password) {
		if (type != 2) {
			return;
		}
		DataBase.delete(formerID);
		DataBase.setUpNewUser(studentNo, name, tele, addr, poverty, type,
				password);
	}

	public static void main(String[] args) {
		UserInfo a = new UserInfo("a", "b", "c", "d", true, 0, "123456");
		a.showInfo();

	}*/
}
