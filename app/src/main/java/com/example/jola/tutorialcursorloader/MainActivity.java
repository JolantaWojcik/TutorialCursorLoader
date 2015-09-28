package com.example.jola.tutorialcursorloader;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter simpleCursorAdapter;

    static final String[] SINGLE_CONTACT = new String[]{
            ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] from = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI};
        int[] to = {R.id.name, R.id.photo};

        //Standard constructor
        //SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags)
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.layout_adapter, null, from, to, 0);
        setListAdapter(simpleCursorAdapter);

        //public abstract <D> Loader<D>
        // initLoader(int id, Bundle args, LoaderCallbacks<D> callback)
       // Ensures a loader is initialized and active.
        getLoaderManager().initLoader(0, null, this);
    }

    //CursorLoader
    //CursorLoader is the standard way to query a Cursor as an asynchronous task
    // in order to avoid blocking your app's main thread with the query.

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

       // String sort = ContactsContract.Contacts._ID + "ASC";
        //constructor:
        //	CursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
        //Creates a fully-specified CursorLoader.
        return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI, SINGLE_CONTACT, null, null, null);
    }

    //onLoadFinished() - update Adapter with the new Cursor and the list view then displays the results
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //Swap in a new Cursor, returning the old Cursor.
        //Unlike changeCursor(Cursor), the returned old Cursor is not closed.
        simpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        simpleCursorAdapter.swapCursor(null);
    }
}
