package common;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * last update:950314
 *
 *
 */
public class SweetFile {
    private Context theContext;
    public SweetFile(Context theContext)
    {
        this.theContext=theContext;
    }
    public void WriteInStorage(String Directory,String FileName,String Content)
    {
        File dir=new File(Environment.getExternalStorageDirectory().toString()+"/"+Directory);
        Log.d("dir",Environment.getExternalStorageDirectory().toString()+"/"+Directory);
        if(!(dir.exists() && dir.isDirectory()))
            dir.mkdirs();
        File sweetFile=new File(dir, FileName);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(sweetFile);
            BufferedWriter out=new BufferedWriter(fileWriter);
            out.write(Content.toString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void WriteInStorage(String Directory,String FileName,InputStream Stream)
    {
        String RootPath=Environment.getExternalStorageDirectory().getAbsolutePath();
        WriteInStorage(Directory, FileName, Stream, RootPath);

    }
    public void WriteInStorage(String Directory,String FileName,InputStream Stream,String RootPath)
    {
        File dir=new File(RootPath+"/"+Directory);

        Log.d("WriteInStorage3",RootPath+"/"+Directory);
        if(!(dir.exists() && dir.isDirectory()))
        {
            Log.d("WriteInStorage3","Making Dirs");
            dir.mkdirs();
        }
        File sweetFile=new File(dir, FileName);
        FileOutputStream output;
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        try {
            output = new FileOutputStream(sweetFile);
            while ((len = Stream.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
        } catch (IOException e) {
            Log.d("ErrorInWriting2storage", "E");

            e.printStackTrace();
        }

    }
    public InputStream getURLInputStream(String Url)
    {
        InputStream InputS=null;
        try {
            URL theUrl=new URL(Url);
            HttpURLConnection Connection=(HttpURLConnection) theUrl.openConnection();
            Connection.setDoInput(true);
            Connection.connect();
            InputS=Connection.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return InputS;

    }
    public static void copyfile(String srFile, String dtFile){
        try{
            File f1 = new File(srFile);
            File f2 = new File(dtFile);
            InputStream in = new FileInputStream(f1);

//                  If you want to append the file.
//          OutputStream out = new FileOutputStream(f2,true);

            //For Overwrite the file.
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void deletefile(String FileName){
        File f1 = new File(FileName);
        f1.delete();
    }
    public static String ReadFileToString(String FilePath)
    {

        File file = new File(FilePath);

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                //Log.d("Line Is:", line);
                text.append(line);
                text.append('\n');
            }
            return text.toString();
        }
        catch (IOException e) {
            return null;
        }
    }
    public static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {

            Log.d("SSS","isDocumentUri");
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                if (!TextUtils.isEmpty(id)) {
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                }
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            Log.d("SSS","Contetn");
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            Log.d("SSS","file");
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        ContentResolver resolver=context.getContentResolver();
        Cursor cursor = resolver.query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String FilePath = cursor.getString(columnIndex);
        cursor.close();
        return FilePath;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
