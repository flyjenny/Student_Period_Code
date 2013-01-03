package planner;

import java.io.*;

import parse.*;
import absyn.*;
import query.plan.Plan;
import tx.Transaction;

/**
 * A planner is the component of database system that transforms an SQL
 * statement into a plan, in other words, it turns the abstract syntax tree into
 * a relational algebra query tree.
 * 
 * @author Alex
 */

public class Planner {
	private QueryPlanner qplanner;
	private UpdatePlanner uplanner;

	public Planner(QueryPlanner qp, UpdatePlanner up) {
		qplanner = qp;
		uplanner = up;
	}

	private Absyn parseFile(String filename) {
		ErrorMsg errorMsg = new ErrorMsg(filename);
		InputStream inp;
		try {
			inp = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			throw new Error("File not found: " + filename);
		}
		parser parser = new parser(new Scanner(inp, errorMsg), errorMsg);

		try {
			parser.parse();
		} catch (RuntimeException e) {
			errorMsg.anyErrors = true;
			e.printStackTrace();
			// throw new Error(e.toString());
			throw e;
		} catch (Exception e) {
			errorMsg.anyErrors = true;
			e.printStackTrace();
			// throw new Error(e.toString());
			throw new SQLParseError(e.toString());
		} finally {
			try {
				inp.close();
			} catch (IOException e) {
			}
		}
		return parser.result;
	}

	public static Absyn parseStr(String sql) {
		ErrorMsg errorMsg = new ErrorMsg("");
		InputStream inp;
		inp = new ByteArrayInputStream(sql.getBytes());
		parser parser = new parser(new Scanner(inp, errorMsg), errorMsg);

		try {
			parser.parse();
		} catch (RuntimeException e) {
			errorMsg.anyErrors = true;
			e.printStackTrace();
			// throw new Error(e.toString());
			throw e;
		} catch (Exception e) {
			errorMsg.anyErrors = true;
			e.printStackTrace();
			// throw new Error(e.toString());
			throw new SQLParseError(e.toString());
		} finally {
			try {
				inp.close();
			} catch (java.io.IOException e) {
			}
		}
		return parser.result;
	}

	public Plan createQueryPlanPassFile(String filename, Transaction tx) {
		return qplanner.createPlan((Query) parseFile(filename), tx);
	}

	public Plan createQueryPlanPassStr(String sql, Transaction tx) {
		return qplanner.createPlan((Query) parseStr(sql), tx);
	}

	public int executeUpdatePassFile(String filename, Transaction tx) {
		Update cmd = (Update) parseFile(filename);

		if (cmd instanceof CreateTbl) {
			return uplanner.executeCreateTable((CreateTbl) cmd, "", tx);
		} else if (cmd instanceof DropTbl) {
			return uplanner.executeDropTable((DropTbl) cmd, tx);
		} else if (cmd instanceof CreateView) {
			return uplanner.executeCreateView((CreateView) cmd, "", tx);
		} else if (cmd instanceof DropView) {
			return uplanner.executeDropView((DropView) cmd, tx);
		} else if (cmd instanceof InsertClause) {
			return uplanner.executeInsert((InsertClause) cmd, tx);
		} else if (cmd instanceof DeleteClause) {
			return uplanner.executeDelete((DeleteClause) cmd, tx);
		} else if (cmd instanceof UpdateClause) {
			return uplanner.executeUpdate((UpdateClause) cmd, tx);
		} else {
			return 0;
		}
	}

	public int executeUpdatePassStr(String sql, Transaction tx) {
		Update cmd = (Update) parseStr(sql);
		if (cmd instanceof CreateTbl) {
			return uplanner.executeCreateTable((CreateTbl) cmd, sql, tx);
		} else if (cmd instanceof DropTbl) {
			return uplanner.executeDropTable((DropTbl) cmd, tx);
		} else if (cmd instanceof CreateView) {
			return uplanner.executeCreateView((CreateView) cmd, sql, tx);
		} else if (cmd instanceof DropView) {
			return uplanner.executeDropView((DropView) cmd, tx);
		} else if (cmd instanceof InsertClause) {
			return uplanner.executeInsert((InsertClause) cmd, tx);
		} else if (cmd instanceof DeleteClause) {
			return uplanner.executeDelete((DeleteClause) cmd, tx);
		} else if (cmd instanceof UpdateClause) {
			return uplanner.executeUpdate((UpdateClause) cmd, tx);
		} else if (cmd instanceof CreateIdx) {
			return uplanner.executeCreateIndex((CreateIdx) cmd, tx);
		} else if (cmd instanceof DropIdx) {
			return uplanner.executeDropIndex((DropIdx) cmd, tx);
		} else {
			return 0;
		}
	}

	public static Planner getSimplePlanner() {
		SimpleQueryPlanner qp = new SimpleQueryPlanner();
		SimpleUpdatePlanner up = new SimpleUpdatePlanner();
		Planner p = new Planner(qp, up);
		return p;
	}
}
