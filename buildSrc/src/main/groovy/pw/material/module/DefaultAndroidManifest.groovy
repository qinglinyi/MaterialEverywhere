package pw.material.module

import org.gradle.api.Project

public class DefaultAndroidManifest {
    private static final String DEFAULT_MANIFEST_FILENAME = '.material.AndroidManifest.xml'
    private static final String TEMPLATE =
            "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\" package=\"%s\">\n" +
            "    <application/>\n" +
            "</manifest>";

    public static File obtainDefaultManifest(Project project, String packageName) {
        File file = new File(project.buildDir, DEFAULT_MANIFEST_FILENAME)
        file.parentFile.mkdirs()
        file.bytes = String.format(TEMPLATE, packageName.replace('-', '.')).getBytes("utf-8")
        return file
    }
}
