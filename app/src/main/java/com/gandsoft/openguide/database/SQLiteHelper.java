package com.gandsoft.openguide.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.gandsoft.openguide.model.UiModel.IpModel;
import com.gandsoft.openguide.model.UiModel.OuiModel;
import com.gandsoft.openguide.model.UiModel.SubnetData;
import com.gandsoft.openguide.model.respond.Oui.OuiItemModel;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Databases information
    private static final String DATABASE_NAME = "ipserver.db";
    private static final int DATABASE_VERSION = 1;
    public static String TABEL_IP = "tabip";
    public static String KEY_ID_IP = "ipid";
    public static String KEY_IP = "ip";
    public static String KEY_IPDESC = "ipdesc";
    public static String KEY_USERNAME = "ipusername";
    public static String KEY_PASSWORD = "ippassword";

    public static String TABEL_OUI = "taboui";
    public static String KEY_ID_OUI = "id";
    public static String KEY_MAC_OUI = "mac";
    public static String KEY_NAME_OUI = "name";

    public static String TABEL_SUBNET_DEVICE = "tabsubnet";
    public static String KEY_ID_SUBNET_DEVICE = "id";
    public static String KEY_IP_SUBNET_DEVICE = "ip";
    public static String KEY_HOST_SUBNET_DEVICE = "host";
    public static String KEY_MAC_SUBNET_DEVICE = "mac";
    public static String KEY_MAC_ID_SUBNET_DEVICE = "macid";
    public static String KEY_TIME_SUBNET_DEVICE = "time";

    private static final String query_add_table = "CREATE TABLE IF NOT EXISTS " + TABEL_IP + "("
            + KEY_ID_IP + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_IP + " BLOB,"
            + KEY_IPDESC + " TEXT,"
            + KEY_USERNAME + " TEXT,"
            + KEY_PASSWORD + " TEXT)";
    private static final String query_delete_table = "DROP TABLE IF EXISTS " + TABEL_IP;

    private static final String query_add_table_oui = "CREATE TABLE IF NOT EXISTS " + TABEL_OUI + "("
            + KEY_ID_OUI + " BIGINT PRIMARY KEY AUTOINCREMENT, "
            + KEY_MAC_OUI + " BLOB,"
            + KEY_NAME_OUI + " TEXT)";
    private static final String query_delete_table_oui = "DROP TABLE IF EXISTS " + TABEL_OUI;

    private static final String query_add_table_subnet = "CREATE TABLE IF NOT EXISTS " + TABEL_SUBNET_DEVICE + "("
            + KEY_ID_SUBNET_DEVICE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_IP_SUBNET_DEVICE + " BLOB,"
            + KEY_HOST_SUBNET_DEVICE + " BLOB,"
            + KEY_MAC_SUBNET_DEVICE + " BLOB,"
            + KEY_MAC_ID_SUBNET_DEVICE + " TEXT,"
            + KEY_TIME_SUBNET_DEVICE + " TEXT)";
    private static final String query_delete_table_subnet = "DROP TABLE IF EXISTS " + TABEL_SUBNET_DEVICE;

    private static final String TAG = "SQLiteHelper";
    private int id;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query_add_table);
        db.execSQL(query_add_table_oui);
        db.execSQL(query_add_table_subnet);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(query_delete_table);
        db.execSQL(query_delete_table_oui);
        db.execSQL(query_delete_table_subnet);
    }

    /*CRUD IpModel*/

    public void saveIp(IpModel ipModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID_IP, getLastNumaberIdDBIp(TABEL_IP) + 1);
        contentValues.put(KEY_IP, ipModel.getIp());
        contentValues.put(KEY_IPDESC, ipModel.getIpDesc());
        db.insert(TABEL_IP, null, contentValues);
        db.close();
    }

    public int updateIp(IpModel ipModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IP, ipModel.getIp());
        contentValues.put(KEY_IPDESC, ipModel.getIpDesc());
        int q = db.update(TABEL_IP, contentValues, KEY_ID_IP + " = ? ", new String[]{String.valueOf(ipModel.getIpId())});
        db.close();
        return q;
    }

    public void deleteIpById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("deleteIpById from " + TABEL_IP + " where " + KEY_IP + " = '" + ipString + "'");
        db.delete(TABEL_IP, KEY_ID_IP + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAllDataIp() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABEL_IP);
        db.close();
    }

    public void deleteTableIp() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query_delete_table);
        db.close();
    }

    public int getLastNumaberIdDBIp(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + table, null);
        if (cursor.moveToLast()) {
            //name = cursor.getString(column_index);//to get other values
            id = cursor.getInt(0);//to get id, 0 is the column index
        }
        return id;
    }

    public int getCountNumberDataDBIp(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, table);
    }

    public Cursor findAll_CursorIp() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABEL_IP, null);
        return cursor;
    }

    public IpModel findOneIpById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABEL_IP, new String[]{KEY_ID_IP, KEY_IP, KEY_IPDESC}, KEY_ID_IP + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return new IpModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

    }

    public List<IpModel> findAllIp() {
        List<IpModel> ipModelList = new ArrayList<IpModel>();
        String query = "SELECT * FROM " + TABEL_IP;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                IpModel ipModel = new IpModel();
                ipModel.setIpId(cursor.getInt(0));
                ipModel.setIp(cursor.getString(1));
                ipModel.setIpDesc(cursor.getString(2));
                ipModel.setIpUsername(cursor.getString(3));
                ipModel.setIpPassword(cursor.getString(4));
                ipModelList.add(ipModel);
            } while (cursor.moveToNext());
        }

        return ipModelList;
    }

    public List<String> findAll_ListIp() {
        List<String> list = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABEL_IP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }

    public boolean checkUserPassExist(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM tabip WHERE ipid = " + id + " AND ipusername IS NOT NULL AND ippassword IS NOT NULL ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUserPassSame(int id, String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
//        String selectQuery = "SELECT * FROM tabip WHERE ipid = " + id + " AND ipusername = " + username + " AND ippassword = " + password;
//        Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TABEL_IP, null, KEY_ID_IP + " = ? AND " + KEY_USERNAME + " = ? AND " + KEY_PASSWORD + " = ?", new String[]{String.valueOf(id), username, password}, null, null, null);
        if (cursor != null) {
            return true;
        } else {
            return false;
        }
    }

    public void setUserPass(IpModel ipModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, ipModel.getIpUsername());
        contentValues.put(KEY_PASSWORD, ipModel.getIpPassword());
        db.update(TABEL_IP, contentValues, "ipid = ? ", new String[]{Integer.toString(Integer.parseInt(String.valueOf(ipModel.getIpId())))});
        db.close();
    }

    public IpModel getUserPass(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABEL_IP, new String[]{KEY_ID_IP, KEY_IP, KEY_IPDESC, KEY_USERNAME, KEY_PASSWORD}, KEY_ID_IP + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return new IpModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

    }

    /*CRUD MAC*/

    public void saveOui(String id, String mac, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID_OUI, id);
        contentValues.put(KEY_MAC_OUI, mac);
        contentValues.put(KEY_NAME_OUI, name);
        db.insert(TABEL_OUI, null, contentValues);
        db.close();
    }

    public void saveOui(ArrayList<OuiItemModel> ouiItemModels, @Nullable Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < ouiItemModels.size(); i++) {
            OuiItemModel model = ouiItemModels.get(i);
            contentValues.put(KEY_ID_OUI, i);
            contentValues.put(KEY_MAC_OUI, model.getMac());
            contentValues.put(KEY_NAME_OUI, model.getName());
            db.insert(TABEL_OUI, null, contentValues);
            if (i == (ouiItemModels.size() - 1)) {
                Toast.makeText(context, "Save NIC MAC success", Toast.LENGTH_SHORT).show();
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void deleteAllDataOui() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABEL_OUI);
        db.close();
    }

    public void updateOui(OuiModel ouiModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID_OUI, ouiModel.getId());
        contentValues.put(KEY_MAC_OUI, ouiModel.getMac());
        contentValues.put(KEY_NAME_OUI, ouiModel.getName());
        db.update(TABEL_OUI, contentValues, KEY_ID_OUI + " = ? ", new String[]{Integer.toString(Integer.parseInt(String.valueOf(ouiModel.getId())))});
        db.close();
    }

    public List<OuiModel> findAllOui() {
        List<OuiModel> ouiModelList = new ArrayList<OuiModel>();
        String query = "SELECT * FROM " + TABEL_OUI;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                OuiModel OuiModel = new OuiModel();
                OuiModel.setId(Integer.parseInt(cursor.getString(0)));
                OuiModel.setMac(cursor.getString(1));
                OuiModel.setName(cursor.getString(2));
                ouiModelList.add(OuiModel);
            } while (cursor.moveToNext());
        }

        return ouiModelList;
    }

    public String findNameByMac(String mac) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String query = "Select " + KEY_NAME_OUI + " from " + TABEL_OUI + " where " + KEY_MAC_OUI + " = " + mac;
        Cursor cursor = db.query(TABEL_OUI, new String[]{KEY_NAME_OUI}, KEY_MAC_OUI + " = ?", new String[]{mac}, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        } else {
            return "Unknown";
        }
    }

    /*CRUD SUBNET*/

    public void saveSubnet(SubnetData subnetData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(KEY_ID_SUBNET_DEVICE, getLastNumaberIdDBIp() + 1);
        contentValues.put(KEY_IP_SUBNET_DEVICE, subnetData.getIpSubnet());
        contentValues.put(KEY_HOST_SUBNET_DEVICE, subnetData.getHostSubnet());
        contentValues.put(KEY_MAC_SUBNET_DEVICE, subnetData.getMacSubnet());
        contentValues.put(KEY_MAC_ID_SUBNET_DEVICE, subnetData.getMacIdSubnet());
        contentValues.put(KEY_TIME_SUBNET_DEVICE, subnetData.getTimeSubnet());
        db.insert(TABEL_SUBNET_DEVICE, null, contentValues);
        db.close();
    }

    public void deleteAllDataSubnet() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABEL_SUBNET_DEVICE);
        db.close();
    }

    /*public ArrayList<Device> findAllSubnet() {
        ArrayList<Device> subnetDataList = new ArrayList<Device>();
        String query = " SELECT * FROM " + TABEL_SUBNET_DEVICE + " ORDER BY " + KEY_IP_SUBNET_DEVICE + " ASC ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Device device = new Device();
                device.setId(cursor.getString(0));
                device.setIp(cursor.getString(1));
                device.setHostname(cursor.getString(2));
                device.setMac(cursor.getString(3));
                device.setMacId(cursor.getString(4));
                device.setTime(Float.parseFloat(cursor.getString(5)));
                subnetDataList.add(device);
            } while (cursor.moveToNext());
        }

        return subnetDataList;
    }*/

    /*OTHER SYNTAX*/

    public Boolean checkDB(String table) {
        Cursor cursor = findAll_CursorIp();
        if (getCountNumberDataDBIp(table) == 0) {
            return false;
        } else {
            return cursor.moveToNext();
        }
    }

    //USER PASSWORD


}