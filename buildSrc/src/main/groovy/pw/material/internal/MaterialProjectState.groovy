package pw.material.internal

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.sdklib.SdkManager
import org.gradle.api.Action
import org.gradle.api.Project
import pw.material.module.ModuleManifest

public class MaterialProjectState {
    MaterialProjectState configure(Action<MaterialProjectState> action) {
        action.execute(this)
        return this
    }

    public final Project project

    public MaterialProjectState(Project project) {
        this.project = project
    }

    def BaseExtension androidExtension
    def ModuleManifest moduleManifest
    def SdkManager sdkManager

    public LibraryExtension getAndroidLibraryExtension() {
        return androidExtension as LibraryExtension
    }

    public AppExtension getAndroidAppExtension() {
        return androidExtension as AppExtension
    }
}
