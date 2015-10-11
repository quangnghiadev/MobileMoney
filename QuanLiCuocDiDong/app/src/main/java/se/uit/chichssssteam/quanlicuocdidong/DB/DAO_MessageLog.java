package se.uit.chichssssteam.quanlicuocdidong.DB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by justinvan on 03-Oct-15.
 */



public class DAO_MessageLog
{

    private SQLiteDatabase _database;
    private DbHelper _dbHelper;
    private String[] _listColumn = {_dbHelper.MESS_ID, _dbHelper.MESSAGE_DATE, _dbHelper.RECEIVER, _dbHelper.MESSAGE_FEE};

    public DAO_MessageLog(Context context) {
        _dbHelper = _dbHelper.getInstance(context);
    }

    public void Open() throws SQLException
    {
        _database = _dbHelper.getWritableDatabase();
        _database = _dbHelper.getReadableDatabase();
    }

    public void Close()
    {
        _dbHelper.close();
    }


    public MessageLog CursortoMessageLog(Cursor c)
    {
        MessageLog row = new MessageLog();
        row.set_messageId(c.getInt(0));
        Date temp = new Date(c.getLong(1));
        row.set_messageDate(temp.toString());
        row.set_recieverNumber(c.getString(2));
        row.set_messageFee(c.getInt(3));
        return row;
    }


    public MessageLog CreateMessageLogRow(String messageDate, String receiver, int messageFee)
    {
        MessageLog msgLog  = new MessageLog();
        ContentValues values = new ContentValues();
        values.put(_dbHelper.MESSAGE_DATE, _dbHelper.convertToMilisec(messageDate));
        values.put(_dbHelper.RECEIVER , receiver);
        values.put(_dbHelper.MESSAGE_FEE, messageFee);
        long insertId = _database.insert(_dbHelper.MESSAGE_TABLE,null,values);
        Cursor cursor = _database.query(_dbHelper.MESSAGE_TABLE,_listColumn, _dbHelper.MESS_ID + " = " + insertId, null,null,null,null);
        cursor.moveToFirst();
        msgLog = CursortoMessageLog(cursor);
        cursor.close();
        return msgLog;

    }

    public void DeleteMessageLog(int _id)
    {
        _database.delete(_dbHelper.MESSAGE_TABLE, _dbHelper.MESS_ID + " = " + _id, null);
    }

    public List<MessageLog> GetAllMessageLog()
    {
        List<MessageLog> _listMessage = new ArrayList<MessageLog>();
        Cursor cursor = _database.query(_dbHelper.MESSAGE_TABLE,_listColumn,null,null,null,null,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            MessageLog temp = CursortoMessageLog(cursor);
            _listMessage.add(temp);
            cursor.moveToNext();

        }
        cursor.close();
        return _listMessage;
    }

    public MessageLog FindMessageLogbyId(int _id)
    {
        MessageLog messageLog;
        Cursor cursor = _database.query(_dbHelper.MESSAGE_TABLE, _listColumn, _dbHelper.MESS_ID + "=" + _id, null, null, null, null);
        if(cursor == null)
            return null;
        else
        {
            cursor.moveToFirst();
            messageLog = CursortoMessageLog(cursor);
        }
        cursor.close();
        return messageLog;
    }
}
