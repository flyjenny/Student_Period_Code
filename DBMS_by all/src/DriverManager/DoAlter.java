package DriverManager;

import Absyn.AlterExp;
import UserInterface.UserInterface;

class DoAlter extends DriverManager {
	DoAlter() {

	}

	void run(AlterExp exp) {
		UserInterface.changeshow("Processing: create a table ...");

		alter(exp);

		UserInterface.changeshow("Finished.");
	}

	void alter(AlterExp exp) {

	}

}
