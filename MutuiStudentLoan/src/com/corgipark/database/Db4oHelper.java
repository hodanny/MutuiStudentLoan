package com.corgipark.database;

import java.util.List;

import android.content.Context;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Db4oHelper {

	private Context context;
	private static ObjectContainer db;
	
	public Db4oHelper(Context ctx)
	{
		context = ctx;
	}
	
	public boolean startup()
	{
		try
		{
			db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbGetPath(context));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public ObjectContainer db()
	{
		return db;
	}
	
	public void shutdown()
	{
		if(db !=null)
			db.close();
	}
	
	public void clear()
	{
		ObjectSet<?> result = db.queryByExample(new Object());
		while(result.hasNext())
		{
			db.delete(result.next());
		}
	}
	
	//public abstract void store(Object object);
	public String dbGetPath(Context context)
	{
		return context.getDir("data", 0) + "/" + "pumpup.db4o";
	}
	
	public List<?> selectAll()
	{
		return db.query(Object.class);
	}

	
}
