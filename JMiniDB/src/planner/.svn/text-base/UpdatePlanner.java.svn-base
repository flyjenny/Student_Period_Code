package planner;

import absyn.*;
import tx.Transaction;

/**
 * An UpdatePlanner handles the planning of an update execution.
 * 
 * @author Alex
 */

public interface UpdatePlanner {

	/**
	 * Executes the specified create table statement, and returns the number of
	 * affected records.
	 * 
	 * @param cmd
	 *            the parsed representation of the create table statement
	 * @param sql
	 *            the definition sql
	 * @param tx
	 *            the calling transaction
	 * @return the number of affected records
	 */
	public int executeCreateTable(CreateTbl cmd, String sql, Transaction tx);

	/**
	 * Executes the specified drop table statement, and returns the number of
	 * affected records.
	 * 
	 * @param cmd
	 *            the parsed representation of the drop table statement
	 * @param tx
	 *            the calling transaction
	 * @return the number of affected records
	 */
	public int executeDropTable(DropTbl cmd, Transaction tx);

	/**
	 * Executes the specified create view statement, and returns the number of
	 * affected records.
	 * 
	 * @param cmd
	 *            the parsed representation of the create view statement
	 * @param defStr
	 *            the definition sql string
	 * @param tx
	 *            the calling transaction
	 * @return the number of affected records
	 */
	public int executeCreateView(CreateView cmd, String defStr, Transaction tx);

	/**
	 * Executes the specified drop view statement, and returns the number of
	 * affected records.
	 * 
	 * @param cmd
	 *            the parsed representation of the drop view statement
	 * @param tx
	 *            the calling transaction
	 * @return the number of affected records
	 */
	public int executeDropView(DropView cmd, Transaction tx);

	/**
	 * Executes the specified create index statement, and returns the number of
	 * affected records.
	 * 
	 * @param cmd
	 *            the parsed representation of the create view statement
	 * @param tx
	 *            the calling transaction
	 * @return the number of affected records
	 */
	public int executeCreateIndex(CreateIdx cmd, Transaction tx);

	/**
	 * Executes the specified drop index statement, and returns the number of
	 * affected records.
	 * 
	 * @param cmd
	 *            the parsed representation of the drop view statement
	 * @param tx
	 *            the calling transaction
	 * @return the number of affected records
	 */
	public int executeDropIndex(DropIdx cmd, Transaction tx);

	/**
	 * Executes the specified insert statement, and returns the number of
	 * affected records.
	 * 
	 * @param cmd
	 *            the parsed representation of the insert statement
	 * @param tx
	 *            the calling transaction
	 * @return the number of affected records
	 */
	public int executeInsert(InsertClause cmd, Transaction tx);

	/**
	 * Executes the specified delete statement, and returns the number of
	 * affected records.
	 * 
	 * @param cmd
	 *            the parsed representation of the delete statement
	 * @param tx
	 *            the calling transaction
	 * @return the number of affected records
	 */
	public int executeDelete(DeleteClause cmd, Transaction tx);

	/**
	 * Executes the specified modify statement, and returns the number of
	 * affected records.
	 * 
	 * @param cmd
	 *            the parsed representation of the modify statement
	 * @param tx
	 *            the calling transaction
	 * @return the number of affected records
	 */
	public int executeUpdate(UpdateClause cmd, Transaction tx);

}
