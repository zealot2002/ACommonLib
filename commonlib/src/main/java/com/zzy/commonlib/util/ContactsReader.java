package com.zzy.commonlib.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2017/12/12
 */

public class ContactsReader {
    private static final ContactsReader ourInstance = new ContactsReader();

    public static ContactsReader getInstance() {
        return ourInstance;
    }

    private ContactsReader() {
    }
    public List<ContactBean> getContactList(Context context){
        List<ContactBean> contactList = new ArrayList<>();
        Cursor contactCursor = null,phoneCursor = null;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            contactCursor = contentResolver.query(Uri.parse("content://com.android.contacts/contacts"),
                    null, null, null, null);
            if (contactCursor == null) {
                /*针对vivo手机 获取不到数据库*/
                return contactList;
            }
            // 循环遍历
            if (contactCursor.moveToFirst()) {
                int idColumn = contactCursor.getColumnIndex(ContactsContract.Contacts._ID);
                int displayNameColumn = contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                do {
                    ContactBean contactBean = new ContactBean();

                    String contactId = contactCursor.getString(idColumn);
                    String displayName = contactCursor.getString(displayNameColumn);

                    // 查看联系人有多少个号码，如果没有号码，返回0
                    int phoneCount = contactCursor.getInt(contactCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    if (phoneCount > 0) {
                        // 获得联系人的电话号码列表
                        phoneCursor = context.getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + "=" + contactId, null, null);
                        if (phoneCursor.moveToFirst()) {
                            do {
                                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                contactBean.getPhoneList().add(phone);
                            } while (phoneCursor.moveToNext());
                        }
                        phoneCursor.close();
                    }
                    contactBean.setName(displayName);
                    contactList.add(contactBean);
                } while (contactCursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(contactCursor!=null){
                contactCursor.close();
            }
            if(phoneCursor!=null){
                phoneCursor.close();
            }
        }
        return contactList;
    }

    public class ContactBean {
        private String name;
        private ArrayList<String> phoneList;

        public ContactBean() {
            phoneList = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<String> getPhoneList() {
            return phoneList;
        }
    }
}
