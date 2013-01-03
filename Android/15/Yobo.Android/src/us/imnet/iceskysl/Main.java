package us.imnet.iceskysl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//������(�ж��Ƿ��Ѿ��з���Ȩ�ޣ����û�У�����ת�ĵ�Authҳ�������Ȩ)
//����͵�Yobo���棨չʾ���˵ĵ�̨�����ֺУ�
public class Main extends Activity {
	public final String TAG = "main";
	// Identifiers for option menu items
	private static final int MENU_START = Menu.FIRST + 1;
	private static final int MENU_HELP = MENU_START + 1;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle(R.string.app_title);
	}

	// ��ʼ���˵�
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_START, 0, R.string.menu_start)
				.setIcon(R.drawable.menu_start)
				.setAlphabeticShortcut('S');
		menu.add(0, MENU_HELP, 0, R.string.menu_helps).setIcon(
				R.drawable.helps).setAlphabeticShortcut(
				'H');
		return true;
	}

	// ��һ���˵���ѡ�е�ʱ�����
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case MENU_START:
			intent.setClass(Main.this, Auth.class);
			startActivity(intent);
			return true;
		case MENU_HELP:
			intent.setClass(Main.this, Helps.class);
			startActivity(intent);
			break;
		}
		return true;
	}



}
