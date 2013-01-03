package Semant;
import Types.*;
import Symbol.*;


class Env{
	//符号入口表
	Table vEnv ;
	//符号类型表
	Table tEnv ;
	ErrorMsg.ErrorMsg errorMsg = null;

	
	Env(ErrorMsg.ErrorMsg err){
		errorMsg = err;
		initTEnv();
		initVEnv();
	}
	//初始化
	void initTEnv(){
		tEnv = new Table();
		tEnv.put(Symbol.symbol("int"),Type._int);
		tEnv.put(Symbol.symbol("string"),Type._string);
	}
	
	void initVEnv(){
		vEnv = new Table();
	
		//sym: 函数名称
		//formals: 参数表
		//result： 返回值类型
		Symbol sym = null; 
		RECORD formals = null;
		Type result = null;
		/**** 11 @ standard function ****/
		//print(s:string);
		sym = Symbol.symbol("print");
		formals = new RECORD(Symbol.symbol("s"), Type._string, null);
		result = Type._void;
		vEnv.put(sym, new FuncEntry(formals, result));
		
		//printi(i: int)
		//print int on the standard output
		sym = Symbol.symbol("printi");
		formals = new RECORD(Symbol.symbol("i"), Type._int, null);
		result = Type._void;
		vEnv.put(sym, new FuncEntry(formals, result));
			
		//flush()
		//flush the standard output buffer
		sym = Symbol.symbol("flush");
		formals = null; 
		result = Type._void;
		vEnv.put(sym, new FuncEntry(formals, result));

		//getchar():string
		sym = Symbol.symbol("getchar");
		formals = null;
		result = Type._string;
		vEnv.put(sym, new FuncEntry(formals, result));	

		//ord(s : string) : int
		//return ASCII_value_of_s[1];
		//return -1: if size(s)=0;
		sym = Symbol.symbol("ord");
		formals = new RECORD(Symbol.symbol("s"), Type._string, null);
		result = Type._int;
		vEnv.put(sym, new FuncEntry(formals, result));
		
		//chr(i : int) : string
		//return chr[1] = char of ASCII value i;
		//error: terminate program
		sym = Symbol.symbol("chr");
		formals = new RECORD(Symbol.symbol("i"), Type._int, null);
		result = Type._string;
		vEnv.put(sym, new FuncEntry(formals, result));
		
		//size(s : string) : int
		// return length_of_s;
		sym = Symbol.symbol("size");
		formals = new RECORD(Symbol.symbol("s"), Type._string, null);
		result = Type._int;
		vEnv.put(sym, new FuncEntry(formals, result));	
		
		//substring( s :sting, f:int, n:int): string
		//return s[f]~s[f+n-1];
		sym = Symbol.symbol("substring");
		formals = new RECORD(Symbol.symbol("s"), Type._string, new RECORD(Symbol.symbol("f"), Type._int, new RECORD(Symbol.symbol("n"), Type._int, null)));
		result = Type._string;
		vEnv.put(sym, new FuncEntry(formals, result));
		
		//concat(s1:string, s2:string) : string
		//return ( s1+s2 );
		sym = Symbol.symbol("concat");
		formals = new RECORD(Symbol.symbol("s1"), Type._string, new RECORD(Symbol.symbol("s2"), Type._string, null));
		result = Type._string;
		vEnv.put(sym, new FuncEntry(formals, result));
		
		//not(i : int) : int
		// return 1: i=0;
		// return 0: o.w.
		sym = Symbol.symbol("_not");
		formals = new RECORD(Symbol.symbol("i"), Type._int, null);
		result = Type._int;
		vEnv.put(sym, new FuncEntry(formals, result));
		
		//exit(i : int)
		//terminate program with code i;
		sym = Symbol.symbol("exit");
		formals = new RECORD(Symbol.symbol("i"), Type._int, null);
		result = Type._void;
		vEnv.put(sym, new FuncEntry(formals, result));
	}
}