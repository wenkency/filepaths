package cn.carhouse.filepaths;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * 兼容7.0文件路径配置
 */

public class AppFileProvider {

    public static final String FILE_PROVIDER = ".FileProvider";

    /**
     * 安装APK
     */
    public static void installApk(Context context, File apkFile) {
        if (context == null || apkFile == null) {
            return;
        }
        // 1.修改文件权限
        setPermission(apkFile.getAbsolutePath());

        // 2. 安装APK
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT >= 24) {
            Uri uriForFile = FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + FILE_PROVIDER,
                    apkFile
            );
            intent.setDataAndType(uriForFile, type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), type);
        }
        context.startActivity(intent);
    }

    /**
     * 修改文件权限
     */
    private static void setPermission(String absolutePath) {
        try {
            String command = "chmod " + "777" + " " + absolutePath;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            return null;
        }
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    private static Uri getUriForFile24(Context context, File file) {
        Uri fileUri = androidx.core.content.FileProvider.getUriForFile(context,
                context.getPackageName() + FILE_PROVIDER, file);
        return fileUri;
    }
}
