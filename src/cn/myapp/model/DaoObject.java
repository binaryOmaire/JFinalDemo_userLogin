package cn.myapp.model;

import java.lang.reflect.Field;
import java.util.Date;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import cn.myapp.util.reflection.UtilReflect;

/**
 * DaoObject [dao操作基类] 
 * 查询 需在子类重写 .
 * @author teason
 *
 */
public class DaoObject extends Model<DaoObject> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * dao Insert 
	 * @param tableName
	 * @param primaryKey
	 * @author teason
	 */
	public void daoInsert(String tableName, String primaryKey) {
		Record record = new Record() ;
		UtilReflect utilSplit = new UtilReflect() ;
		String[] fieldList = utilSplit.getFieldName(this) ;
		for (String string : fieldList) {
			if (string.contains("id") || string.contains("ID")) continue ;		
			if (utilSplit.getFieldValueByName(string, this) == null) continue ;
			
			record.set(string, utilSplit.getFieldValueByName(string, this)) ;
		}		
		Db.save(tableName,primaryKey,record) ;
	}	
	
	/**
	 * dao fetch obj From Record . 
	 * @param record
	 * @return object .
	 */
	public DaoObject fetchFromRecord(Record record) {
				
		UtilReflect utilSplit = new UtilReflect() ;
		Field[] fieldList = utilSplit.getFields(this) ;
		for (Field field: fieldList) {
			String string = field.getName() ;			
			
			if (field.getType() == int.class) {			
				utilSplit.setFieldNameValueByName(field, this, new Object[]{record.getInt(string)}) ;
			}
			else if (field.getType() == String.class) {
				utilSplit.setFieldNameValueByName(field, this, new Object[]{record.getStr(string)}) ;
			}
			else if (field.getType() == double.class) {
				utilSplit.setFieldNameValueByName(field, this, new Object[]{record.getDouble(string)}) ;
			}
			else if (field.getType() == boolean.class) {
				utilSplit.setFieldNameValueByName(field, this, new Object[]{record.getBoolean(string)}) ;			
			}			
			else if (field.getType() == Date.class) {
				utilSplit.setFieldNameValueByName(field, this, new Object[]{record.getDate(string)}) ; 
			}			
		}
		
		return this ;
	}
	
	
}
