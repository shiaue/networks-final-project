package info.androidhive.materialtabs.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.LongSparseArray;
import android.widget.Toast;

import com.owlike.genson.Genson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by e on 2/25/16.
 */
public class ContactSync extends AsyncTask<Void, Void, Void> {
    public final static String TAG = "Syncing-Contacts";

    // not allowed to make a network call over a UI thread
    // Forces you to use a concurrent task (Async)



    @Override
    protected Void doInBackground(Void... params) {
        List<AddressBookContact> list = new LinkedList<AddressBookContact>();
        LongSparseArray<AddressBookContact> array = new LongSparseArray<AddressBookContact>();
        long start = System.currentTimeMillis();

        String[] projection = {
                ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Contactables.DATA,
                ContactsContract.CommonDataKinds.Contactables.TYPE,
        };
        String selection = ContactsContract.Data.MIMETYPE + " in (?, ?)";
        String[] selectionArgs = {
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
        };
        String sortOrder = ContactsContract.Contacts.SORT_KEY_ALTERNATIVE;

        Uri uri = ContactsContract.CommonDataKinds.Contactables.CONTENT_URI;
// we could also use Uri uri = ContactsContract.Data.CONTENT_URI;

// ok, let's work...

//        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
        // TODO fix this cursor
        Cursor cursor = null;
        final int mimeTypeIdx = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE);
        final int idIdx = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID);
        final int nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        final int dataIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA);
        final int typeIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.TYPE);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(idIdx);
            AddressBookContact addressBookContact = array.get(id);
            if (addressBookContact == null) {
                addressBookContact = new AddressBookContact(id, cursor.getString(nameIdx));
                array.put(id, addressBookContact);
                list.add(addressBookContact);
            }
            int type = cursor.getInt(typeIdx);
            String data = cursor.getString(dataIdx);
            String mimeType = cursor.getString(mimeTypeIdx);
            if (mimeType.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                // mimeType == ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                Log.d(TAG, "Inside Email mimeType");
                Log.d(TAG, "email: "+ data);
                addressBookContact.addEmail(data);

            } else {
                // mimeType == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                Log.d(TAG, "Inside phone mimeType");
                Log.d(TAG, "phone: "+ data);

                addressBookContact.addPhone(data);
            }
        }
        long ms = System.currentTimeMillis() - start;
        cursor.close();

// done!!! show the results...
        int i = 1;
        for (AddressBookContact addressBookContact : list) {
            Log.w(TAG, "AddressBookContact #" + i++ + ": " + addressBookContact.toString());
        }

        final String cOn = "<b><font color='#ff9900'>";
        final String cOff = "</font></b>";
        Spanned l1 = Html.fromHtml("got " + cOn + array.size() + cOff + " contacts<br/>");
        Spanned l2 = Html.fromHtml("query took " + cOn + ms / 1000f + cOff + " s (" + cOn + ms + cOff + " ms)");

        Log.w(TAG, "\n\n╔══════ Contact query execution stats ═══════" );
        Log.w(TAG, "║    " + l1);
        Log.w(TAG, "║    " + l2);
        Log.w(TAG, "╚════════════════════════════════════" );
        return null;
    }

//    @Override
//    protected void onPostExecute(Void aVoid) {
//        if(aVoid.equals("Exception Caught"))  {
//                        Toast.makeText(getApplicationContext(),
//                    "Web: Sending SMS failed.",
//                    Toast.LENGTH_LONG).show();
//        }
//        else    {
//                        Toast.makeText(getActivity(), "Web: SMS sent.",
//                    Toast.LENGTH_LONG).show();
//        }
//
//    }
}
