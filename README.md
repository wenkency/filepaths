# filepaths
这个是7.0文件路径适配的库。为方便其它用到的库单独抽离出来。

### 清单配置
```
    <provider
            android:name="cn.carhouse.filepaths.AppProvider"
            android:authorities="${applicationId}.AppProvider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/app_file_paths" />
    </provider>
```
