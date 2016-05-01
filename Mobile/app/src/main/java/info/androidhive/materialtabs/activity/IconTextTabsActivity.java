package info.androidhive.materialtabs.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.fragments.OneFragment;
import info.androidhive.materialtabs.fragments.ThreeFragment;
import info.androidhive.materialtabs.fragments.TwoFragment;

public class IconTextTabsActivity extends AppCompatActivity implements OneFragment.OnFragmentInteractionListener1, TwoFragment.OnFragmentInteractionListener2 {

    public final static String TAG = "Contacts";
    public static String [] dateAndTime;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_tab_call,
            R.drawable.ic_tab_contacts,
            R.drawable.ic_tab_favourite
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_text_tabs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        // TODO Contacts Displayer

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
        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

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
//                Log.d(TAG, "Inside Email mimeType");
//                Log.d(TAG, "email: "+ data);
                addressBookContact.addEmail(data);

            } else {
                // mimeType == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
//                Log.d(TAG, "Inside phone mimeType");
//                Log.d(TAG, "phone: "+ data);
                addressBookContact.addPhone(data);
            }
        }
        long ms = System.currentTimeMillis() - start;
        cursor.close();

// done!!! show the results...
        int i = 1;
//        for (AddressBookContact addressBookContact : list) {
//            Log.w(TAG, "AddressBookContact #" + i++ + ": " + addressBookContact.print());
//            Log.w(TAG, addressBookContact.getPhone());
//        }

        final String cOn = "<b><font color='#ff9900'>";
        final String cOff = "</font></b>";
        Spanned l1 = Html.fromHtml("got " + cOn + array.size() + cOff + " contacts<br/>");
        Spanned l2 = Html.fromHtml("query took " + cOn + ms / 1000f + cOff + " s (" + cOn + ms + cOff + " ms)");

        Log.w(TAG, "\n\n╔══════ Contact query execution stats ═══════" );
        Log.w(TAG, "║    " + l1);
        Log.w(TAG, "║    " + l2);
        Log.w(TAG, "╚════════════════════════════════════" );

        // TODO Synchronizing Contacts

        SMSync task = new SMSync();
        task.execute(list);


    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new OneFragment(), "Details");
        adapter.addFrag(new TwoFragment(), "Date / Time");

        Intent intent = getIntent();
        String[] message = intent.getStringArrayExtra("Date_Time");

        Bundle bundle = new Bundle();
        bundle.putStringArray("date_time", message);
        ThreeFragment fragobj = new ThreeFragment();
        fragobj.setArguments(bundle);

        adapter.addFrag(fragobj, "Send");
        viewPager.setAdapter(adapter);
//        hideKeyboard(getApplicationContext());
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
//            hideKeyboard(getApplicationContext());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    public static String schedPhoneNum = "8479222186";
    public static String schedTextMessage = "Default SMS message";
    public void onFragmentInteraction1(String phoneNumber, String textMessage) {
        TwoFragment secondFragment = (TwoFragment)getSupportFragmentManager().findFragmentByTag("info.androidhive.materialtabs.fragments.TwoFragment");
        schedPhoneNum = phoneNumber;
        schedTextMessage = textMessage;
//        TwoFragment.updateTextField(userContent, size, colorValue);
    }

    public void onFragmentInteraction2(String [] date_time_s)   {
        dateAndTime = date_time_s;
        OneFragment firstFragment = (OneFragment)getSupportFragmentManager().findFragmentByTag("info.androidhive.materialtabs.fragments.TwoFragment");
        firstFragment.scheduleTextMessage(schedPhoneNum,schedTextMessage,date_time_s);


//        String [] date_time_s = {hr_s, min_s, mon_s, day_s, yr_s};



    }

}
