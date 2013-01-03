package Semant;

import Symbol.Table;
import ErrorMsg.*;

public class Env {
	Table venv; // value environment
	Table tenv; // type environment

	ErrorMsg errorMsg;

	Env(ErrorMsg err) {
		errorMsg = err;
		venv = new Table();
		tenv = new Table();
		// initialize venv and tenv with predefined identifiers
	}
}
